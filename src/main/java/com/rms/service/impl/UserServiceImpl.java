package com.rms.service.impl;

import com.rms.model.dto.UserDTO;
import com.rms.model.entity.User;
import com.rms.model.entity.UserRole;
import com.rms.model.entity.UserRoleEnum;
import com.rms.repositiry.UserRepository;
import com.rms.service.interfaces.UserService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final LoggedUser loggedUser;
//    private final HttpSession session;
//    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository
//            ,
//                           LoggedUser loggedUser,
//                           HttpSession session,
//                           PasswordEncoder encoder
    ) {
        this.userRepository = userRepository;
//        this.loggedUser = loggedUser;
//        this.session = session;
//        this.encoder = encoder;
    }

    public UserDTO findUserByUsername(String username) { // for validation for unique username
        User user = this.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        return this.mapUserDTO(user);
    }

    public UserDTO findUserByEmail(String email) { // for validation for unique email
        User user = this.userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }

        return this.mapUserDTO(user);
    }

    public boolean checkCredentials(String username, String password) {
        User user = this.getUserByUsername(username);

        return false;
//        return user != null && encoder.matches(password, user.getPassword());
    }

//    public void login(String username) {
//        User user = this.getUserByUsername(username);
//        this.loggedUser.setId(user.getId());
//        this.loggedUser.setUsername(user.getUsername());
//    }

//    public void register(RegisterDTO registerDTO) {
//        this.userRepository.save(this.mapUser(registerDTO));
//        this.login(registerDTO.getUsername());
//    }

//    public void logout() {
//        this.session.invalidate();
//        this.loggedUser.setId(null);
//        this.loggedUser.setUsername(null);
//    }

    private User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

//    private User mapUser(RegisterDTO registerDTO) {
//        User user = new User();
//        user.setUsername(registerDTO.getUsername());
//        user.setEmail(registerDTO.getEmail());
//        user.setPassword(encoder.encode(registerDTO.getPassword()));
//        return user;
//    }

    private UserDTO mapUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        ;
        return userDTO;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }



//    @Override
//    public Language findByName(LanguageNameEnum languageNameEnum) {
//        return languageRepository.findByName(languageNameEnum).orElse(null);
//    }
}
