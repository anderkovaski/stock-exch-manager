package stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
