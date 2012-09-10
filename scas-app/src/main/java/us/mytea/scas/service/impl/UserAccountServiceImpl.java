package us.mytea.scas.service.impl;

import org.jasig.cas.support.oauth.authentication.principal.OAuthCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import us.mytea.scas.domain.SocialConnection;
import us.mytea.scas.domain.UserAccount;
import us.mytea.scas.domain.UserProfile;
import us.mytea.scas.domain.UserProfile.Gender;
import us.mytea.scas.repository.SocialConnectionRepository;
import us.mytea.scas.repository.UserAccountRepository;
import us.mytea.scas.repository.UserProfileRepository;
import us.mytea.scas.service.UserAccountService;

public class UserAccountServiceImpl implements UserAccountService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	UserAccountRepository accountRepository;
	
	@Autowired 
	UserProfileRepository profileRepository;
	
	@Autowired 
	SocialConnectionRepository connectionRepository;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return accountRepository.findByUsername(username);
	}

	public void createSocialConnection(OAuthCredentials credentials) {
		logger.debug("");
		logger.debug("credentials.getUserId() = " + credentials.getUserId());
		String username = credentials.getProviderType() + "#" + credentials.getUserId();
		UserDetails ud = this.loadUserByUsername(username);
		if(ud != null) {
			return;
		}
		UserAccount account = new UserAccount();
		account.setUsername(username);
		account.setAccountNonExpired(true);
		account.setAccountNonLocked(true);
		account.setCredentialsNonExpired(true);
		account.setEnabled(true);
		account.addRole("ROLE_USER");
		accountRepository.save(account);
		
		SocialConnection conn = new SocialConnection();
		conn.setAccount(account);
		String accessToken = credentials.getUserAttributes().get("access_token").toString();
		
		String providerId = credentials.getProviderType();
		String providerUserId = credentials.getUserId();
		conn.setAccessToken(accessToken);
//		conn.setExpireTime(expireTime);
		conn.setProviderId(providerId);
		conn.setProviderUserId(providerUserId);
//		conn.setRefreshToken(refreshToken);
//		conn.setSecret(secret);
		connectionRepository.save(conn);
		
		String name = credentials.getUserAttributes().get("name").toString();
		String profileUrl = credentials.getUserAttributes().get("link").toString();
		String firstName = credentials.getUserAttributes().get("first_name").toString();
		String lastName = credentials.getUserAttributes().get("last_name").toString();
		Gender gender = Gender.fromString(credentials.getUserAttributes().get("gender").toString().toLowerCase());
		String locale = credentials.getUserAttributes().get("locale").toString();
		String providerUsername = credentials.getUserAttributes().get("username").toString();
		String updatedTime = credentials.getUserAttributes().get("updated_time").toString();
		String verified = credentials.getUserAttributes().get("verified").toString();
		UserProfile profile = new UserProfile();
		profile.setAccount(account);
		profile.setFirstName(firstName);
		profile.setGender(gender);
		profile.setLastName(lastName);
		profile.setLocale(locale);
		profile.setName(name);
		profile.setProfileUrl(profileUrl);
		profile.setProviderUsername(providerUsername);
		profile.setUpdatedTime(updatedTime);
		profile.setVerified(Boolean.valueOf(verified));
		profileRepository.save(profile);
	}

}
