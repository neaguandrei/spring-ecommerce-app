package com.fmi.cart.controller;

import com.fmi.api.catalog.resource.request.AddCartRequestResource;
import com.fmi.api.catalog.resource.response.CompletedCartResponseResource;
import com.fmi.cart.service.CartService;
import com.fmi.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class RestCartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<Object> addProduct(@RequestBody AddCartRequestResource request) {
        cartService.add(request.getProductId(), request.getCartKey());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart/{cart_key}/product/{product_id}")
    public ResponseEntity<Object> removeProduct(@PathVariable("cart_key") String cartKey, @PathVariable("product_id") Long productId) throws NotFoundException {
        cartService.remove(productId, cartKey);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current/{cart_key}")
    public ResponseEntity<CompletedCartResponseResource> getCart(@PathVariable("cart_key") String cartKey) {
        return ResponseEntity.ok(cartService.getCart(cartKey));
    }

    @GetMapping("/{cart_key}")
    public ResponseEntity<CompletedCartResponseResource> completeCart(@PathVariable("cart_key") String cartKey) {
        return ResponseEntity.ok(cartService.completeCart(cartKey));
    }
}
