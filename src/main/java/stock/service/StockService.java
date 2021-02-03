package stock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import stock.domain.Stock;
import stock.exception.ObjectNotFoundException;
import stock.repository.StockRepository;

@Service
public class StockService {
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired 
	private UserService userService;
	
	public List<Stock> findAll() {
		return stockRepo.findAll();
	}
	
	public Page<Stock> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return stockRepo.findByUser(userService.getAuthenticatedUser(), pageRequest);
		
	}
	
	public Stock findById(Long id) {
		
		Optional<Stock> stock = stockRepo.findByIdAndUser(id, userService.getAuthenticatedUser());
		
		return stock.orElseThrow(
				() -> new ObjectNotFoundException("Stock not found"));
		
	}
	
	public Stock insert(Stock stock) {
		
		stock.setId(null);
		
		stock.setUser(userService.getAuthenticatedUser());
		
		return stockRepo.save(stock);
		
	}
	
	public Stock update(Stock stock, Long id) {
		
		findById(id);
		
		if (stock.getId() != id) {
			stock.setId(id);
		}		
		
		return stockRepo.save(stock);
		
	}
	
	public void deleteById(Long id) {
		
		findById(id);
		
		stockRepo.deleteById(id);
		
	}
	
}

