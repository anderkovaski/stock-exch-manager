package stock.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
	
	void sendEmail(String email, String subject, List<String> text);
	
	void send(SimpleMailMessage msg);
}
