package stock.service;

import java.util.Arrays;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import stock.domain.Order;
import stock.domain.Stock;
import stock.domain.User;
import stock.domain.enumerator.OrderTypeEnum;
import stock.domain.enumerator.Profile;
import stock.repository.OrderRepository;
import stock.repository.StockRepository;
import stock.repository.UserRepository;

@Service
public class DBService {
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void instantiateDatabase() {
		
		User u1 = new User(null, "anderkovaski@gmail.com", passwordEncoder.encode("123"));
		u1.getProfiles().add(Profile.ROLE_ADMIN);
		
		User u2 = new User(null, "anderkovaski@live.com", passwordEncoder.encode("456"));
		
		userRepository.saveAll(Arrays.asList(u1, u2));
		
		Stock s1 = new Stock(null, "GOLL4", "Gol Linhas Aéreas", u1);
		Stock s2 = new Stock(null, "GOLL4F", "Gol Linhas Aéreas", u1);
		Stock s3 = new Stock(null, "OIBR4", "Oi", u1);
		Stock s4 = new Stock(null, "OIBR4F", "Oi", u1);
		Stock s5 = new Stock(null, "IRB", "IRB Brasil RE", u2);
		Stock s6 = new Stock(null, "IRBF", "IRB Brasil RE", u2);		
		
		stockRepo.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));
		
		Order o1 = new Order(null, OrderTypeEnum.PURCHASE, s2, 10, new GregorianCalendar(2021, 01, 21).getTime(), null, u1);
		Order o2 = new Order(null, OrderTypeEnum.PURCHASE, s4, 50, new GregorianCalendar(2021, 01, 20).getTime(), null, u1);
		
		Order o3 = new Order(null, OrderTypeEnum.SALE, s4, 20, new GregorianCalendar(2021, 01, 22).getTime(), o2, u1);
		Order o4 = new Order(null, OrderTypeEnum.SALE, s4, 10, new GregorianCalendar(2021, 01, 22).getTime(), o2, u1);
		
		Order o5 = new Order(null, OrderTypeEnum.PURCHASE, s5, 100, new GregorianCalendar(2021, 01, 21).getTime(), null, u2);
		
		Order o6 = new Order(null, OrderTypeEnum.SALE, o5.getStock(), 20, new GregorianCalendar(2021, 01, 22).getTime(), o5, u2);
		Order o7 = new Order(null, OrderTypeEnum.SALE, o5.getStock(), 40, new GregorianCalendar(2021, 01, 22).getTime(), o5, u2);		
		
		orderRepo.saveAll(Arrays.asList(o1, o2, o3, o4, o5, o6, o7));
	
	}

}
