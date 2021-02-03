package stock.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import stock.domain.Stock;
import stock.domain.User;

public interface StockRepository extends JpaRepository<Stock, Long> {
	
	@Transactional(readOnly = true)
	Page<Stock> findByUser(User user, Pageable pageRequest);
	
	Optional<Stock> findByIdAndUser(Long id, User user);
}
