package com.fmi.cart.service;

import com.fmi.api.cart.CompletedCartResponseResource;
import com.fmi.cart.dao.entity.CartEntity;
import com.fmi.cart.dao.repository.CartRepository;
import com.fmi.common.exception.NotFoundException;
import com.fmi.security.model.User;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {

    private final HazelcastInstance hazelcast;

    private final CartRepository cartRepository;

    public void add(Long productId, String cartKey) {
        IMap<Long, Integer> cart = hazelcast.getMap(cartKey);
        Integer quantity = cart.get(productId);
        if (quantity != null) {
            cart.set(productId, ++quantity);
        } else {
            cart.put(productId, 1);
        }
    }

    public void remove(Long productId, String cartKey) throws NotFoundException {
        IMap<Long, Integer> cart = hazelcast.getMap(cartKey);
        Integer quantity = Optional.ofNullable(cart.get(productId)).orElseThrow(() -> new NotFoundException("Product doesn't exist in the cart!"));
        if (quantity > 1) {
            cart.set(productId, --quantity);
        } else {
            cart.remove(productId);
        }
    }

    public CompletedCartResponseResource completeCart(String cartKey) {
        final Map<Long, Integer> map = new HashMap<>(hazelcast.getMap(cartKey));
        hazelcast.getMap("cart").clear();
        return CompletedCartResponseResource.builder()
                .products(map)
                .build();
    }

    public CompletedCartResponseResource getCart(String cartKey) {
        final Map<Long, Integer> map = new HashMap<>(hazelcast.getMap(cartKey));
        return CompletedCartResponseResource.builder()
                .products(map)
                .build();
    }
    
//    private void syncCart(String cartKey) {
//        if (SecurityContextHolder.getContext() == null) {
//            return;
//        }
//
//        CartEntity existingCart = cartRepository.findByCartKey(cartKey);
//        if (existingCart == null) {
//
//            List<Long> products = new ArrayList<>();
//            IMap<Long, Integer> cache = hazelcast.getMap(cartKey);
//            cache.keySet().forEach(productId -> {
//                int quantity = cache.get(productId);
//                for (int i = 0; i < quantity; i++) {
//                    products.add(productId);
//                }
//            });
//
//            CartEntity cart = CartEntity.builder().cartKey(cartKey).email(email).products(products).build();
//            cartRepository.save(cart);
//        }
//    }
}
