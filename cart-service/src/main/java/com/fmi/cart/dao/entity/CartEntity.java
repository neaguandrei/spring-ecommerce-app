package com.fmi.cart.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private UUID cartKey;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    @Convert(converter = StringListConverter.class)
    private List<Long> products;

    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;

    @Column(name = "last_updated", nullable = false)
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastUpdated;

    @Version
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private int version;

    @Converter
    static class StringListConverter implements AttributeConverter<List<String>, String> {
        private static final String SPLIT_CHAR = ";";

        @Override
        public String convertToDatabaseColumn(List<String> stringList) {
            return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
        }

        @Override
        public List<String> convertToEntityAttribute(String string) {
            return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
        }
    }
}
