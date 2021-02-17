package stock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void send(SimpleMailMessage msg) {
		
		LOG.info("Simulate email sending");
		LOG.info(msg.toString());
		LOG.info("Email sent");		
	}
	
	
}
