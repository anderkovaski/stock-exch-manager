package stock.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import stock.domain.User;
import stock.service.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<Page<User>> findPage(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction,
			@RequestParam(name = "orderBy", defaultValue = "email") String orderBy) {
		
		Page<User> userPage = userService.findPage(page, linesPerPage, direction, orderBy);
		
		return ResponseEntity.ok().body(userPage);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody User user) {
		
		User newUser = userService.insert(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable Long id) {
		
		userService.update(user, id);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		userService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
