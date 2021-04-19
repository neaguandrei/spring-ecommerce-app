package com.fmi.cart.service;

import com.fmi.api.catalog.resource.response.CompletedCartResponseResource;
import com.fmi.common.exception.NotFoundException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final HazelcastInstance hazelcast;

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
}
