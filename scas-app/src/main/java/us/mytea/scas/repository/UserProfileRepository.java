package us.mytea.scas.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import us.mytea.scas.domain.UserAccount;
import us.mytea.scas.domain.UserProfile;


public interface UserProfileRepository extends CrudRepository<UserProfile, String> {

}
