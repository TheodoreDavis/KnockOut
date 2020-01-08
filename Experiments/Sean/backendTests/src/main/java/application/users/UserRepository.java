package application.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Sean Griffen
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
