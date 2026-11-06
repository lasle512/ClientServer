package ru.university.clothes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name="products")
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn (name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn (name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column (nullable = false)
    private int price;

    @Column (nullable = false)
    private int stockQuantity;

    @Column (nullable = false)
    private LocalDate lastRestockedAt;
}
