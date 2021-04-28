package com.fmi.cart.service;

import com.fmi.api.cart.CompletedCartResponseResource;
import com.fmi.cart.dao.entity.CartEntity;
import com.fmi.cart.dao.repository.CartRepository;
import com.fmi.cart.model.CartRow;
import com.fmi.common.exception.NotFoundException;
import com.fmi.security.model.UserPrincipal;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
        cart.put(cartKey, cartRow);
        syncCacheToDatabase(cartKey);
    }

    public void remove(Long productId, String cartKey) throws NotFoundException {
        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        CartRow cartRow = cart.get(cartKey);
        if (Objects.isNull(cartRow)) {
            throw new NotFoundException("Cart with key: " + cartKey + " is not found.");
        }

        Integer currentQuantity = Optional.ofNullable(cartRow.getProducts().get(productId)).orElseThrow(() -> new NotFoundException("Product doesn't exist in the cart!"));
        if (currentQuantity > 1) {
            Integer newQuantity = currentQuantity - 1;
            cartRow.getProducts().put(productId, newQuantity);
            cartRow.setLastUpdated(LocalDateTime.now());
        } else if (cartRow.getProducts().values().isEmpty()) {
            cartRow.setDeleted(LocalDateTime.now());
        }

        cart.put(cartKey, cartRow);
        syncCacheToDatabase(cartKey);
    }

    public CompletedCartResponseResource completeCart(String cartKey) throws NotFoundException {
        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        CartRow cartRow = cart.get(cartKey);
        if (Objects.isNull(cartRow)) {
            throw new NotFoundException("Cart with key: " + cartKey + " is not found.");
        }

        final CompletedCartResponseResource responseResource = CompletedCartResponseResource.builder()
                .products(new HashMap<>(cart.get(cartKey).getProducts()))
                .build();

        cartRow.setDeleted(LocalDateTime.now());
        cartRow.getProducts().clear();
        cart.put(cartKey, cartRow);

        syncCacheToDatabase(cartKey);

        return responseResource;
    }

    public CompletedCartResponseResource getCart(String cartKey) throws NotFoundException {
        syncCacheToDatabase(cartKey);
        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        CartRow cartRow = cart.get(cartKey);
        if (Objects.isNull(cartRow)) {
            throw new NotFoundException("Cart with key: " + cartKey + " is not found.");
        }

        return CompletedCartResponseResource.builder()
                .products(cart.get(cartKey).getProducts())
                .build();
    }

    private void syncCacheToDatabase(String cartKey) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return;
        }

        String email = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        CartEntity existingCart = cartRepository.findByCartKey(cartKey);
        if (existingCart == null) {
            existingCart = CartEntity.builder()
                    .cartKey(cartKey)
                    .email(email)
                    .build();
        }

        IMap<String, CartRow> cart = hazelcast.getMap(cartKey);
        CartRow cartRow = cart.computeIfAbsent(cartKey, s -> createCartRow(cartKey, email));

        if (cartRow.getDeleted() != null || existingCart.getDeleted() != null) {
            cartRepository.deleteByCartKey(cartKey);
            hazelcast.getMap(cartKey).clear();
            return;
        }

        synchroniseProducts(existingCart, cartRow);
    }

    private void synchroniseProducts(CartEntity existingCart, CartRow cartRow) {
        List<Long> products = new ArrayList<>();
        if (existingCart.getLastUpdated() != null && cartRow.getLastUpdated() != null && existingCart.getLastUpdated().isAfter(cartRow.getLastUpdated())) {
            Map<Long, Integer> cartRowProducts = new HashMap<>();
            existingCart.getProducts().forEach(productId -> {
                Integer currentQuantity = cartRowProducts.get(productId);
                if (Objects.isNull(currentQuantity)) {
                    cartRowProducts.put(productId, 1);
                } else {
                    cartRowProducts.put(productId, ++currentQuantity);
                }
            });
            cartRow.setProducts(cartRowProducts);
        } else {
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

    private CartRow createCartRow(String cartKey, String email) {
        return CartRow.builder()
                .cartKey(cartKey)
                .email(email)
                .created(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
