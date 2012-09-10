package us.mytea.scas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="user_profile")
public class UserProfile implements Serializable {
	
	@Id
	@GeneratedValue(generator = "customForeignGenerator")
    @org.hibernate.annotations.GenericGenerator(
        name = "customForeignGenerator",
        strategy = "foreign",
        parameters = @org.hibernate.annotations.Parameter(name = "property", value = "account")
    )
	String id;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	UserAccount account;
	
	@Column(name="first_name", length=30)
	String firstName;
	
	@Column(name="last_name", length=30)
	String lastName;
	
	@Column(name="full_name", length=30)
	String name;
	
	@Column(name="provider_username", length=30)
	String providerUsername;
	
	@Column
	boolean verified = false;
	
	@Column(name="profile_url", length=200)
	String profileUrl;
	
	@Column(name="image_url", length=200)
	String imageUrl;
	@Column
	Gender gender;
	
	@Column(name="locale", length=10)
	String locale;
	
	@Column(name="updated_time", length=30)
	String updatedTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProviderUsername() {
		return providerUsername;
	}

	public void setProviderUsername(String providerUsername) {
		this.providerUsername = providerUsername;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public enum Gender {
		MALE("male"), FEMALE("female"); 
		String code;
		
		private Gender(String code) {
			this.code = code;
		}
		public String getCode() {
			return this.code;
		}
		
		public static Gender fromString(String code) {
		    if (code != null) {
		      for (Gender b : Gender.values()) {
		        if (code.equalsIgnoreCase(b.code)) {
		          return b;
		        }
		      }
		    }
		    return null;
		  }
	}
}