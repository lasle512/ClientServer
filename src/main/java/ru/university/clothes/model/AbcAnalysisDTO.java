package ru.university.clothes.model;
import java.math.BigDecimal;

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
public class AbcAnalysisDTO {
    private Long id;
    private Product product;
    private BigDecimal totalRevenue;
    private String abcClass; // A, B, C
}
