package pl.coderslab.charity.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.User;

import java.util.List;

@Service
@Primary
public interface UserService {

    User findByUserName(String name);

    void createUser(User user);


    boolean checkUserName(String name);

    boolean checkPassword(User user, String password);

    List<User> findAdmins();

    User findById(Long id);

    void deleteById(Long id);

    void createAdmin(String userName, String name, String surname, String password);

    void updateUser(User user);

    void updatePassword(User user, String password);

    List<User> findUsers();

    void disableUser(User user);

    void enableUser(User user);
}
