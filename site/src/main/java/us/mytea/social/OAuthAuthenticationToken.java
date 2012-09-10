package us.mytea.social;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.oauth1.OAuthToken;

import java.util.ArrayList;
import java.util.Collection;

public class OAuthAuthenticationToken extends AbstractAuthenticationToken {
 
   final private OAuthToken token;
   final private Object     principal;
   final private String     providerAccountId;
   final private String     serviceProviderId;
 
   OAuthAuthenticationToken(OAuthToken token, String providerAccountId, String serviceProviderId) {
      super(new ArrayList<GrantedAuthority>(0));
        this.token = token;
        this.providerAccountId = providerAccountId;
        this.serviceProviderId = serviceProviderId;
        this.principal = "/" + serviceProviderId + "/" + providerAccountId;
        setAuthenticated(false);
   }
 
   /**
    * Created by the authentication provider after successful authentication
    * @param userDetails
    * @param authorities
    * @param token
    */
   public OAuthAuthenticationToken(UserDetails userDetails, Collection<GrantedAuthority> authorities,
           OAuthToken token, String providerAccountId, String serviceProviderId) {
      super(authorities);
      this.token = token;
      this.providerAccountId = providerAccountId;
      this.serviceProviderId = serviceProviderId;
      this.principal = userDetails;
      setAuthenticated(true);
   }
 
   @Override
   public Object getCredentials() {
      return null;
   }
 
   @Override
   public Object getPrincipal() {
      return principal;
   }
 
   public OAuthToken getAccessToken() {
      return token;
   }
 
   public String getServiceProviderId() {
      return serviceProviderId;
   }
 
   public String getProviderAccountId() {
      return providerAccountId;
   }
}