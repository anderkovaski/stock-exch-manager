package stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import stock.domain.User;
import stock.exception.IllegalArgumentException;
import stock.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Page<User> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Direction.valueOf(direction), orderBy);
		
		return userRepository.findAll(pageRequest);
	}
	
	public User findByEMail(String email) {
		
		User user = userRepository.findByEmail(email);
		
		return user;
	}
	
	public User insert(User user) {
		
		user.setId(null);
		
		if (findByEMail(user.getEmail()) != null) {
			throw new IllegalArgumentException("E-mail already registered");
		}
		
		user.setPassword(encode(user.getPassword()));
		
		return userRepository.save(user);		
	}
	
	public User update(User user, Long id) {
		
		if (user.getId() != id) {
			user.setId(id);
		}
		
		user.setPassword(encode(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	public void delete(Long id) {
		
		userRepository.deleteById(id);
	}
	
	private String encode(String password) {
		
		return passwordEncoder.encode(password);
	}
	
}
