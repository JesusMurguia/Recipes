package recipes.persistence;

import org.springframework.stereotype.Repository;
import recipes.business.User;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}