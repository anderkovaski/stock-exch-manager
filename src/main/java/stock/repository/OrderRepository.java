package stock.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import stock.domain.Order;
import stock.domain.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	Page<Order> findByUser(User user, Pageable pageRequest);
	
	Optional<Order> findByIdAndUser(Long id, User user);
}
