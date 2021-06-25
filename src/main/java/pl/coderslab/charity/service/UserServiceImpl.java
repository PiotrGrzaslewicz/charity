package pl.coderslab.charity.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Role;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.RoleRepository;
import pl.coderslab.charity.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUserName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void createUser(User user) {
        user.setEnabled(1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public boolean checkUserName(String name) {
        return userRepository.findByUsername(name) == null;

    }

    @Override
    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public List<User> findAdmins() {
        Role role = roleRepository.findByName("ROLE_ADMIN");
        return userRepository.findAllByRoles(new HashSet<Role>(Arrays.asList(role)));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void createAdmin(String userName, String name, String surname, String password) {
        User user = new User();
        user.setUsername(userName);
        user.setName(name);
        user.setSurname(surname);
        user.setEnabled(1);
        user.setPassword(passwordEncoder.encode(password));
        Role userRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
        Role userRoleUser = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRoleAdmin, userRoleUser)));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public List<User> findUsers() {
        Role role = roleRepository.findByName("ROLE_USER");
        return userRepository.findAllByRoles(new HashSet<Role>(Arrays.asList(role)));
    }

    @Override
    public void disableUser(User user) {
        user.setEnabled(0);
        userRepository.save(user);
    }

    @Override
    public void enableUser(User user) {
        user.setEnabled(1);
        userRepository.save(user);
    }
}
