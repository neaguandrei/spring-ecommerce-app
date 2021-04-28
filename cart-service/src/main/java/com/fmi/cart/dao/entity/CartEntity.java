package com.fmi.cart.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cartKey;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    @Convert(converter = LongListConverter.class)
    private List<Long> products;

    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "last_updated", nullable = false)
    @Setter(AccessLevel.NONE)
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Column(name = "deleted")
    @Setter(AccessLevel.NONE)
    private LocalDateTime deleted;

    @Version
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private int version;

    @Converter
    static class LongListConverter implements AttributeConverter<List<Long>, String> {
        private static final String SPLIT_CHAR = ";";

        @Override
        public String convertToDatabaseColumn(List<Long> longList) {
            return longList != null ? longList.stream().map(String::valueOf).collect(Collectors.joining(SPLIT_CHAR)) : "";
        }

        @Override
        public List<Long> convertToEntityAttribute(String string) {
            return string != null ? Arrays.stream(string.split(SPLIT_CHAR)).map(Long::valueOf).collect(Collectors.toList()) : emptyList();
        }
    }
}
