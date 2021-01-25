package stock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import stock.domain.Order;
import stock.domain.enumerator.OrderTypeEnum;
import stock.exception.IllegalArgumentException;
import stock.exception.ObjectNotFoundException;
import stock.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> findAll() {
		
		return orderRepository.findAll();
		
	}
	
	public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return orderRepository.findAll(pageRequest);
		
	}
	
	public Order findById(Long id) {
		
		Optional<Order> order = orderRepository.findById(id);
		
		return order.orElseThrow(() -> new ObjectNotFoundException("Order not found"));
	}
	
	
	public Order insert(Order order) {
		
		order.setId(null);
		
		if (order.getType() == OrderTypeEnum.SALE) {
			
			Order orderPurchase = orderRepository.findById(order.getPurchaseOrder().getId())
					.orElseThrow(() -> new ObjectNotFoundException("The purchase order entered was not found"));
			
			Integer totalSales = orderPurchase.getSalesOrder().stream().mapToInt(o -> o.getAmount()).sum();
			
			if (totalSales + order.getAmount() > orderPurchase.getAmount() ) {
				throw new IllegalArgumentException("The ammount of sales exceeded the ammount of purchases");
			}
			
		}
			
		return orderRepository.save(order);

	}
	
	public Order update(Order order) {
		
		findById(order.getId());
		
		if (order.getType() == OrderTypeEnum.SALE) {
			
			Order orderPurchase = orderRepository.findById(order.getPurchaseOrder().getId())
					.orElseThrow(() -> new ObjectNotFoundException("The purchase order entered was not found"));
			
			Integer totalSales = orderPurchase.getSalesOrder().stream()
					.filter(o -> o.getId() != order.getId())
					.mapToInt(o -> o.getAmount()).sum();
			
			if (totalSales + order.getAmount() > orderPurchase.getAmount() ) {
				throw new IllegalArgumentException("The ammount of sales exceeded the ammount of purchases");
			}
			
		} else if (order.getType() == OrderTypeEnum.PURCHASE) {
			
			if (! order.getSalesOrder().isEmpty()) {
				throw new IllegalArgumentException("It is not permitted to change a purchase order that has linked sales orders");
			}
			
		}
		
		return orderRepository.save(order);
	}
	
	public void delete(Long id) {
		
		findById(id);
		
		orderRepository.deleteById(id);
		
	}
}
