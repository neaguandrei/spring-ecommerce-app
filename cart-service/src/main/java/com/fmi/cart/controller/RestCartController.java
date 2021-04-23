package com.fmi.cart.controller;

import com.fmi.api.cart.AddCartRequestResource;
import com.fmi.api.cart.CompletedCartResponseResource;
import com.fmi.cart.service.CartService;
import com.fmi.common.exception.NotFoundException;
import com.fmi.security.annotation.PreAuthorizeAny;
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

    @PreAuthorizeAny
    @GetMapping("/{cart_key}")
    public ResponseEntity<CompletedCartResponseResource> completeCart(@PathVariable("cart_key") String cartKey) {
        return ResponseEntity.ok(cartService.completeCart(cartKey));
    }
}
