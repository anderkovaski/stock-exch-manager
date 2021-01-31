package stock.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import stock.domain.enumerator.Profile;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;	
	private String email;
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl() {
		
	}
	
	public UserDetailsImpl(Long id, String email, String password, Set<Profile> profiles) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = profiles.stream().map(x -> new SimpleGrantedAuthority(x.toString())).collect(Collectors.toList());
	}
	
	public Long getId() {
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}	

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean hasRole(Profile profile) {
		return getAuthorities().contains(new SimpleGrantedAuthority(profile.toString()));
	}

}
