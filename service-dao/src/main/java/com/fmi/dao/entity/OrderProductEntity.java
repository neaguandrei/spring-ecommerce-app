package com.fmi.dao.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "orders_product")
public class OrderProductEntity {

    @EmbeddedId
    private OrderProductId orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    public OrderProductEntity(OrderEntity order, ProductEntity product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.orderProductId = new OrderProductId(order.getId(), product.getId());
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        OrderProductEntity that = (OrderProductEntity) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(product, that.product) &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product, quantity);
    }
}
