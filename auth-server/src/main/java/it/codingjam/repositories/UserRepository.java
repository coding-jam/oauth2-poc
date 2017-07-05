package it.codingjam.repositories;

import it.codingjam.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.inject.Named;

/**
 * Created by acomo on 26/03/17.
 */
@Named
public interface UserRepository extends JpaRepository<User, String> {

}
