package stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import stock.domain.User;
import stock.exception.ObjectNotFoundException;
import stock.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	Random rand = new Random();
		
	public void SendNewPassword(String email) {
		
		User user = userRepo.findByEmail(email);
		
		if (user == null) {
			throw new ObjectNotFoundException("User not found");
		}
		
		String newPasswd = newPassword();
		
		user.setPassword(passwordEncoder.encode(newPasswd));
		userRepo.save(user);
		
		List<String> text = new ArrayList<>();
		text.add("Your new temporary password requested is:");
		text.add("");
		text.add(newPasswd);
		
		emailService.sendEmail(user.getEmail(), "New password", text);
	}

	private String newPassword() {
		
		char[] vet = new char[10];
		
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		
		int opt = rand.nextInt(3);
		
		if (opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		} 
		else {
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
