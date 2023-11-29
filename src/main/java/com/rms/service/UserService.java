package com.rms.service;

import com.rms.model.dto.RegisterDTO;
import com.rms.model.dto.UserDTO;
import com.rms.model.entity.UserEntity;
import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;
import com.rms.repositiry.UserRepository;
//import com.rms.service.interfaces.UserRoleService;
//import com.rms.service.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService
{

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;
    private final UserRoleService userRoleService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder encoder,
                       UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userRoleService = userRoleService;
    }

    public UserDTO findUserByUsername(String username) { // for validation for unique username
        UserEntity user = this.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        return this.mapUserDTO(user);
    }

    public UserEntity findUserEntityByUsername(String username) {
        return this.getUserByUsername(username);
    }

    public UserDTO findUserByEmail(String email) { // for validation for unique email
        UserEntity user = this.userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }

        return this.mapUserDTO(user);
    }

    public boolean checkCredentials(String username, String password) {
        UserEntity user = this.getUserByUsername(username);

        return user != null && encoder.matches(password, user.getPassword());
    }

//    public void login(String username) {
//        UserEntity user = this.getUserByUsername(username);
//    }

    public void register(RegisterDTO registerDTO) {
        this.userRepository.save(this.mapUser(registerDTO));
//        this.login(registerDTO.getUsername());
    }

//    public void logout() {
//        this.session.invalidate();
//        this.loggedUser.setId(null);
//        this.loggedUser.setUsername(null);
//    }

    private UserEntity getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    private UserEntity mapUser(RegisterDTO registerDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encoder.encode(registerDTO.getPassword()));
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPhone(registerDTO.getPhone());
        user.setRegistrationDate(LocalDate.now());
        user.setAddress(registerDTO.getAddress());
        UserRoleEntity userRole = userRoleService.findUserRoleEntityByRole(UserRoleEnum.USER);
        user.getRoles().add(userRole);
//        UserRoleEntity clientRole = userRoleService.findUserRoleEntityByRole(UserRoleEnum.CLIENT);
//        user.getRoles().add(clientRole);
        return user;
    }

    private UserDTO mapUserDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        ;
        return userDTO;
    }

//    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

//    @Override
    public void initAdmin() {

        if (userRepository.count() != 0) {
            return;
        }

        UserEntity admin = new UserEntity();
        admin.setUsername("pesho");
        admin.setEmail("pesho@pripesho.com");
        admin.setPassword(encoder.encode("123"));
//        System.out.println(encoder.encode("123"));
        admin.setFirstName("Pesho");
        admin.setLastName("Peshev");
        admin.setAddress("Tabadupkovo");
        admin.setPhone("123456");
        admin.setRegistrationDate(LocalDate.of(1991, 2, 3));
        UserRoleEntity roleAdmin = userRoleService.findUserRoleEntityByRole(UserRoleEnum.ADMIN);
        admin.getRoles().add(roleAdmin);
        UserRoleEntity roleUser = userRoleService.findUserRoleEntityByRole(UserRoleEnum.USER);
        admin.getRoles().add(roleUser);

        userRepository.save(admin);
    }

    public Long usersCount() {
        return userRepository.count();
    }

//    @Override
    public void initUsers() {

        if (userRepository.count() > 1) {
            return;
        }
//        UserRoleEntity userRoleUser = userRoleService.findUserRoleEntityByRole(UserRoleEnum.USER);
//        UserRoleEntity userRoleWaiter = userRoleService.findUserRoleEntityByRole(UserRoleEnum.WAITER);
//        UserRoleEntity userRoleCook = userRoleService.findUserRoleEntityByRole(UserRoleEnum.COOK);
//        UserRoleEntity userRoleBartender = userRoleService.findUserRoleEntityByRole(UserRoleEnum.BARTENDER);
//        UserRoleEntity userRoleClient = userRoleService.findUserRoleEntityByRole(UserRoleEnum.CLIENT);

//        initUser("waiter1", userRoleWaiter);
//        initUser("waiter2", userRoleWaiter);

//        initUser("cook1", userRoleCook);
//        addUser("cook2", userRoleCook);

//        initUser("bartender1", userRoleBartender);
//        addUser("bartender2", userRoleBartender);

        initUser("client1");
        initUser("client2");
        initUser("client3");
        initUser("client4");
        initUser("client5");
        initUser("client6");
        initUser("client7");
        initUser("client8");
        initUser("client9");
        initUser("client10");

    }

//    @Override
//    public void initClients() {
//        //
//    }

    private void initUser(String username) {
//    private void initUser(String username, UserRoleEntity specificUserRole) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(username + "@pripesho.com");
        user.setPassword(encoder.encode("123"));
//        System.out.println(encoder.encode("123"));
        user.setFirstName(username + "Fname");
        user.setLastName(username + "Lname");
        user.setAddress(username +" address");
        user.setPhone(username + " phone");
        user.setRegistrationDate(LocalDate.of(1991, 2, 3));

        UserRoleEntity userRoleUser = userRoleService.findUserRoleEntityByRole(UserRoleEnum.USER);
        user.getRoles().add(userRoleUser);
//        user.getRoles().add(specificUserRole);

        userRepository.save(user);
    }


//    @Override
//    public Language findByName(LanguageNameEnum languageNameEnum) {
//        return languageRepository.findByName(languageNameEnum).orElse(null);
//    }
}
