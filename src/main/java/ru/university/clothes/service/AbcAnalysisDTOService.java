package ru.university.clothes.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.AbcAnalysisDTOResponse;
import ru.university.clothes.repository.SaleItemRepository;

@Service 
@RequiredArgsConstructor 
public class AbcAnalysisDTOService {

    // доля накопленной выручки, до которой товар попадает в класс A и B
    private static final double CLASS_A_SHARE = 0.80;
    private static final double CLASS_B_SHARE = 0.95;

    private final SaleItemRepository saleItemRepository;

    /**
     * Товары по убыванию выручки. Класс определяется долей выручки всех товаров выше по списку:
     * пока она меньше 80% — A, меньше 95% — B, остальное — C.
     */
    @Transactional (readOnly = true)
    public List<AbcAnalysisDTOResponse> analyze() {
        List<Object[]> rows = saleItemRepository.revenueByProduct();
        long totalRevenue = rows.stream().mapToLong(row -> ((Number) row[2]).longValue()).sum();

        List<AbcAnalysisDTOResponse> result = new ArrayList<>();
        long cumulative = 0;
        for (Object[] row : rows) {
            long revenue = ((Number) row[2]).longValue();
            double shareBefore = (double) cumulative / totalRevenue;
            String abcClass = shareBefore < CLASS_A_SHARE ? "A" : shareBefore < CLASS_B_SHARE ? "B" : "C";
            result.add(new AbcAnalysisDTOResponse(
                (Long) row[0], (String) row[1], BigDecimal.valueOf(revenue), abcClass));
            cumulative += revenue;
        }
        return result;
    }

}