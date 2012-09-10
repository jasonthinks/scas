package us.mytea.scas.service;

import org.jasig.cas.support.oauth.authentication.principal.OAuthCredentials;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {

	void createSocialConnection(OAuthCredentials credentials);

}
