package stock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import stock.service.DBService;
import stock.service.EmailService;
import stock.service.MockEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean 
	public boolean instantiateDatabase() {
		
		dbService.instantiateDatabase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		
		return new MockEmailService();
	}
}
