package stock.service;

import java.util.Arrays;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stock.domain.Order;
import stock.domain.Stock;
import stock.domain.enumerator.OrderTypeEnum;
import stock.repository.OrderRepository;
import stock.repository.StockRepository;

@Service
public class DBService {
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	public void instantiateDatabase() {
		
		Stock s1 = new Stock(null, "GOLL4", "Gol Linhas Aéreas");
		Stock s2 = new Stock(null, "GOLL4F", "Gol Linhas Aéreas");
		Stock s3 = new Stock(null, "OIBR4", "Oi");
		Stock s4 = new Stock(null, "OIBR4F", "Oi");
		
		stockRepo.saveAll(Arrays.asList(s1, s2, s3, s4));
		
		Order o1 = new Order(null, OrderTypeEnum.PURCHASE, s2, 10, new GregorianCalendar(2021, 01, 21).getTime(), null);
		Order o2 = new Order(null, OrderTypeEnum.PURCHASE, s4, 50, new GregorianCalendar(2021, 01, 20).getTime(), null);
		
		Order o3 = new Order(null, OrderTypeEnum.SALE, s4, 20, new GregorianCalendar(2021, 01, 22).getTime(), o2);
		Order o4 = new Order(null, OrderTypeEnum.SALE, s4, 10, new GregorianCalendar(2021, 01, 22).getTime(), o2);
		
		orderRepo.saveAll(Arrays.asList(o1, o2, o3, o4));
	
	}

}
