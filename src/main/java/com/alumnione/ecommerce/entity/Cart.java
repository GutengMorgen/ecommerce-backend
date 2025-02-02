package com.alumnione.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="cart_cell",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "cellphone_id")
    )
    private List<Cellphone> cellphones;

    @Column(name = "last_updated")
    //TODO: is this necessary?
    private LocalDateTime lastUpdated;

    @OneToOne(fetch=FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;
}