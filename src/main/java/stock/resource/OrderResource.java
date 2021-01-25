package stock.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import stock.domain.Order;
import stock.service.OrderService;

@RestController
@RequestMapping(path = "/orders")
public class OrderResource {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping(path = "/")
	public ResponseEntity<Page<Order>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "runOn") String orderBy) {
		
		Page<Order> orderPage = orderService.findPage(page, linesPerPage, orderBy, direction);
		
		return ResponseEntity.ok().body(orderPage);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		
		Order order = orderService.findById(id);
		
		return ResponseEntity.ok().body(order);
	}
	
	@PostMapping(path = "/")
	public ResponseEntity<Order> insert(@Valid @RequestBody Order order) {
		
		Order newOrder = orderService.insert(order);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrder.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Order order, @PathVariable Long id) {
		
		orderService.update(order);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		orderService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
