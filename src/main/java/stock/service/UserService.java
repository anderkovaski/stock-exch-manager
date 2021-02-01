package stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import stock.domain.User;
import stock.domain.enumerator.Profile;
import stock.exception.AuthorizationException;
import stock.exception.IllegalArgumentException;
import stock.repository.UserRepository;
import stock.security.UserDetailsImpl;

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
		
		UserDetailsImpl authenticatedUser = authenticated();		
		
		if (authenticatedUser == null || (!id.equals(authenticatedUser.getId()) && !authenticatedUser.hasRole(Profile.ROLE_ADMIN))) {
			throw new AuthorizationException("Access denied");
		}		
		
		if (user.getId() != id) {
			user.setId(id);
		}
		
		user.setPassword(encode(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	public void delete(Long id) {
		
		UserDetailsImpl authenticatedUser = authenticated();		
		
		if (authenticatedUser == null || authenticatedUser.hasRole(Profile.ROLE_ADMIN) || id.equals(authenticatedUser.getId())) {
			throw new AuthorizationException("Access denied");
		}		
		
		userRepository.deleteById(id);
	}
	
	private String encode(String password) {
		
		return passwordEncoder.encode(password);
	}
	
	public static UserDetailsImpl authenticated() {
		
		try {
			return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
