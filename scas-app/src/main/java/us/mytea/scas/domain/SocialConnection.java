package us.mytea.scas.domain;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name="social_connection")
public class SocialConnection implements Serializable {
	
	@Id
	@GeneratedValue(generator = "customForeignGenerator")
    @org.hibernate.annotations.GenericGenerator(
        name = "customForeignGenerator",
        strategy = "foreign",
        parameters = @org.hibernate.annotations.Parameter(name = "property", value = "account")
    )
	String id;
	
	@ManyToOne
	@PrimaryKeyJoinColumn
	UserAccount account;
	
	@Column(name="provider_id", length=20)
	private String providerId;
	
	@Column(name="provider_user_id", length=50)
	private String providerUserId;
	
	@Column(name="access_token", length=200)
	private String accessToken;
	
	@Column(name="secret", length=10)
	private String secret;
	
	@Column(name="refresh_token", length=200)
	private String refreshToken;
	
	@Column(name="expire_time")
	private Long expireTime;
	
	public SocialConnection() {
		
	}
	public SocialConnection(String providerId, String providerUserId, String displayName, String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken, Long expireTime) {
		this.providerId = providerId;
		this.providerUserId = providerUserId;
		this.accessToken = accessToken;
		this.secret = secret;
		this.refreshToken = refreshToken;
		this.expireTime = expireTime;
	}

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	/**
	 * The id of the provider the connection is associated with.
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * The id of the provider user this connection is connected to.
	 */
	public String getProviderUserId() {
		return providerUserId;
	}
	
	/**
	 * The access token required to make authorized API calls.
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * The secret token needed to make authorized API calls.
	 * Required for OAuth1-based connections.
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * A token use to renew this connection. Optional.
	 * Always null for OAuth1-based connections.
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * The time the connection expires. Optional.
	 * Always null for OAuth1-based connections.
	 */
	public Long getExpireTime() {
		return expireTime;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	@Override
	public String toString() {
		return "SocialConnection [id=" + id + ", account=" + account
				+ ", providerId=" + providerId + ", providerUserId="
				+ providerUserId
				+ ", accessToken=" + accessToken + ", secret=" + secret
				+ ", refreshToken=" + refreshToken + ", expireTime="
				+ expireTime + "]";
	}
		
}