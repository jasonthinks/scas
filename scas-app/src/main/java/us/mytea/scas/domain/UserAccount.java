package us.mytea.scas.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
@Table(name="user_account")
public class UserAccount implements UserDetails {
	
	@Id
	@Column(name="username", length=50)
	String username;
	
	@Column(name="password", length=50)
	String password;
	
	@Column
	boolean accountNonExpired;
	
	@Column
	boolean accountNonLocked;
	
	@Column
	boolean credentialsNonExpired;
	
	@Column
	boolean enabled;
	
	@OneToMany(mappedBy="account", orphanRemoval=true)
	Set<SocialConnection> connections;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="account")
	UserProfile profile;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name ="role")
	public List<String> roles = new ArrayList<String>();

	public UserProfile getProfile() {
		return profile;
	}


	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}


	public List<String> getRoles() {
		return roles;
	}


	protected void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}


	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}


	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}


	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}


	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}


	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Set<SocialConnection> getConnections() {
		return connections;
	}


	public void setConnections(Set<SocialConnection> connections) {
		this.connections = connections;
	}
	
	public void addRole(String role) {
		if(!this.roles.contains(role)) {
			this.roles.add(role);
		}
	}
	
	public void removeRole(String role) {
		if(this.roles.contains(role)) {
			this.roles.remove(role);
		}
	}
	
	public void clearRoles() {
		if(this.roles.size() > 0) {
			this.roles.clear();
		}
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> ret = new ArrayList<GrantedAuthority>(); 
		if(this.roles.size() > 0) {
			for(String s: roles) {
				ret.add(new SimpleGrantedAuthority(s));
			}
		}
		return ret;
	}



}
