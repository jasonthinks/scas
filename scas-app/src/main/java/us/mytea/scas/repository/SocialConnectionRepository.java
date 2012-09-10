package us.mytea.scas.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import us.mytea.scas.domain.SocialConnection;
import us.mytea.scas.domain.UserAccount;


public interface SocialConnectionRepository extends CrudRepository<SocialConnection, String> {

}
