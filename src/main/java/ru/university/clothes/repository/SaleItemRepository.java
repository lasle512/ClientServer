package ru.university.clothes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.university.clothes.model.SaleItem;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    // выручка по каждому товару: [id товара, название, сумма quantity * priceAtSaleTime], по убыванию
    @Query("""
            select si.product.id, si.product.title, sum(si.quantity * si.priceAtSaleTime)
            from SaleItem si
            group by si.product.id, si.product.title
            order by sum(si.quantity * si.priceAtSaleTime) desc
            """)
    List<Object[]> revenueByProduct();
}