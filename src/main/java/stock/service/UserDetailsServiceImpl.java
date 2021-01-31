package stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import stock.domain.User;
import stock.repository.UserRepository;
import stock.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException(email + " not found");
		}
		
		return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), user.getProfiles());
	}

}
