package aneagu.proj.models.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String addressLineOne;

    @Column(nullable = false)
    private String addressLineTwo;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String postalCode;

    @Column(insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Version
    @Column
    @Setter(AccessLevel.NONE)
    private int version;

    public AddressEntity(Long id, String addressLineOne, String addressLineTwo, String city, String state, String country, String postalCode) {
        this.id = id;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }
}
