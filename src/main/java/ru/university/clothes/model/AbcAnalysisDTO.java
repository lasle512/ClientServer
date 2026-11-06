package ru.university.clothes.model;
import java.math.BigDecimal;

import com.fasterxml.classmate.GenericType;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table (name="AbcAnalysisDTO")
public class AbcAnalysisDTO {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private Product product;

    @Column (nullable = false)
    private BigDecimal totalRevenue;

    @Column (nullable = false)
    private String abcClass;
}
