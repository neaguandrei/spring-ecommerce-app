package com.fmi.cart.service;

import com.fmi.api.cart.CompletedCartResponseResource;
import com.fmi.cart.dao.entity.CartEntity;
import com.fmi.cart.dao.repository.CartRepository;
import com.fmi.cart.model.CartRow;
import com.fmi.common.exception.NotFoundException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final HazelcastInstance hazelcast;

    private final CartRepository cartRepository;

    public void add(Long productId, String cartKey) {
        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        CartRow cartRow = cart.get(cartKey);
        if (cartRow == null) {
            cartRow = CartRow.builder()
                    .cartKey(cartKey)
                    .created(LocalDateTime.now())
                    .build();
        }

        Integer currentQuantity = cartRow.getProducts().get(productId);
        if (currentQuantity != null) {
            Integer newQuantity = currentQuantity + 1;
            cartRow.getProducts().put(productId, newQuantity);
        } else {
            cartRow.getProducts().put(productId, 1);
        }
        cartRow.setLastUpdated(LocalDateTime.now());
        cart.put(cartKey, cartRow);
    }

    public void remove(Long productId, String cartKey) throws NotFoundException {
        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        CartRow cartRow = cart.get(cartKey);
        if (Objects.isNull(cartRow)) {
            throw new NotFoundException("Cart doesn't exist");
        }

        Integer currentQuantity = Optional.ofNullable(cartRow.getProducts().get(productId)).orElseThrow(() -> new NotFoundException("Product doesn't exist in the cart!"));
        if (currentQuantity > 1) {
            Integer newQuantity = currentQuantity - 1;
            cartRow.getProducts().put(productId, newQuantity);
            cartRow.setLastUpdated(LocalDateTime.now());
        } else if (cartRow.getProducts().values().size() == 0) {
            cartRow.setDeleted(LocalDateTime.now());
        }

        cart.put(cartKey, cartRow);
    }

    public CompletedCartResponseResource completeCart(String cartKey) {
        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        cart.get(cartKey).setDeleted(LocalDateTime.now());
        return CompletedCartResponseResource.builder()
                .products(cart.get(cartKey).getProducts())
                .build();
    }

    public CompletedCartResponseResource getCart(String cartKey) {
        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        return CompletedCartResponseResource.builder()
                .products(cart.get(cartKey).getProducts())
                .build();
    }

    private void syncCacheToDatabase(String cartKey) {
        CartEntity existingCart = cartRepository.findByCartKey(cartKey);
        if (existingCart == null) {
            existingCart = CartEntity.builder().cartKey(cartKey).build();
        }

        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        CartRow cartRow = cart.get(cartKey);
        if (cartRow == null) {
            return;
        }

        if (cartRow.getDeleted() != null) {
            cartRepository.deleteByCartKey(cartKey);
            return;
        }

        if (existingCart.getLastUpdated() != null && cartRow.getLastUpdated().isAfter(existingCart.getLastUpdated())) {
            List<Long> products = new ArrayList<>();
            cartRow.getProducts().keySet().forEach(productId -> {
                int quantity = cartRow.getProducts().get(productId);
                for (int i = 0; i < quantity; i++) {
                    products.add(productId);
                }
            });
            existingCart.setProducts(products);
        }

        cartRepository.save(existingCart);
    }
}
