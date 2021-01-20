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

import stock.domain.Stock;
import stock.service.StockService;

@RestController
@RequestMapping(path = "/stocks")
public class StockResource {
	
	@Autowired
	private StockService stockService;
	
	@GetMapping(path = "/page")
	public ResponseEntity<Page<Stock>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "description") String orderBy) {
		
		Page<Stock> stockPage = stockService.findPage(page, linesPerPage, orderBy, direction);
		
		return ResponseEntity.ok().body(stockPage);
	}
	
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Stock> findById(@PathVariable Long id) {
		
		Stock stock = stockService.findById(id);
		
		return ResponseEntity.ok().body(stock);
	}
	
	@PostMapping(path = "/")
	public ResponseEntity<Void> insert(@Valid @RequestBody Stock stock) {
		
		stockService.insert(stock);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(stock.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Stock stock, @PathVariable Long id) {
		
		stockService.findById(id);
		
		if (stock.getId() != id) {
			stock.setId(id);
		}
		
		stockService.update(stock);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		
		stockService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
