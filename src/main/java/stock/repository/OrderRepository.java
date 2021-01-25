package stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import stock.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
}
