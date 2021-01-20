package stock.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stock.domain.Stock;
import stock.repository.StockRepository;

@Service
public class DBService {
	
	@Autowired
	private StockRepository stockRepo;
	
	public void instantiateDatabase() {
		
		Stock s1 = new Stock(null, "GOLL4", "Gol Linhas Aéreas");
		Stock s2 = new Stock(null, "GOLL4F", "Gol Linhas Aéreas");
		Stock s3 = new Stock(null, "OIBR4", "Oi");
		Stock s4 = new Stock(null, "OIBR4F", "Oi");
		
		stockRepo.saveAll(Arrays.asList(s1, s2, s3, s4));
	
	}

}
