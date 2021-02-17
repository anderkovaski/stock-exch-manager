package stock.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;


public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendEmail(String email, String subject, List<String> text) {
		
		SimpleMailMessage smm = new SimpleMailMessage();
		
		smm.setTo(email);
		smm.setFrom(sender);
		smm.setSubject(subject);
		smm.setText(text.stream().collect(Collectors.joining()));
		
		send(smm);
	}

}
