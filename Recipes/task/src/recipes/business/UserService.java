package recipes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.UserRepository;
import recipes.business.User;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User save(User toSave) {

        return userRepository.save(toSave);
    }
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

}