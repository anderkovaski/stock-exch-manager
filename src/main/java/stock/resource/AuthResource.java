package stock.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stock.domain.User;
import stock.dto.EmailDTO;
import stock.security.JWTUtil;
import stock.service.AuthService;
import stock.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;

	@PostMapping(path = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		
		User user = userService.getAuthenticatedUser();		
		String token = jwtUtil.generateToken(user.getEmail());
		
		response.addHeader("Authorization", "Bearer " + token);		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(path = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
		
		authService.SendNewPassword(emailDTO.getEmail());
		
		return ResponseEntity.noContent().build();
	}
	
}
