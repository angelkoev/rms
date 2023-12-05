package com.rms.service;

import com.rms.model.dto.RegisterDTO;
import com.rms.model.dto.UserDTO;
import com.rms.model.entity.*;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.model.views.UserView;
import com.rms.repositiry.UserRepository;
//import com.rms.service.interfaces.UserRoleService;
//import com.rms.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, UserRoleService userRoleService, ModelMapper modelMapper, OrderService orderService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
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

    public boolean isAdmin(String username) {

        UserEntity byUsername = getUserByUsername(username);

        for (UserRoleEntity role : byUsername.getRoles()) {
            if (role.getRole().name().equals("ADMIN")) {
                return true;
            }
        }

        return false;
    }

    public void checkLastOrder(String username) {
        UserEntity currentUser = getUserByUsername(username);

        if (currentUser.getLastOrder() == null) {
            OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
            currentUser.setLastOrder(newLastOrder);
        }
    }

    public List<DrinkView> getAllCurrentDrinkViews(String username) {
        UserEntity currentUser = getUserByUsername(username);

        List<DrinkEntity> currentDrinks = currentUser.getLastOrder().getDrinks();
        return currentDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();
    }
    public List<FoodView> getAllCurrentFoodViews(String username) {
        UserEntity currentUser = getUserByUsername(username);

        List<FoodEntity> currentFoods = currentUser.getLastOrder().getFoods();
        return currentFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();
    }

    public void register(RegisterDTO registerDTO) {
        this.userRepository.save(this.mapUser(registerDTO));
    }

    public UserEntity getUserByUsername(String username) {
        UserEntity currentUser = this.userRepository.findByUsername(username).orElse(null);
        if (currentUser == null) {
            throw new NoSuchElementException(); // FIXME throw something else or go to error page ??
        }
        return currentUser;
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


    private void initUser(String username) {
//    private void initUser(String username, UserRoleEntity specificUserRole) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(username + "@pripesho.com");
        user.setPassword(encoder.encode("123"));
//        System.out.println(encoder.encode("123"));
        user.setFirstName(username + "Fname");
        user.setLastName(username + "Lname");
        user.setAddress(username + " address");
        user.setPhone(username + " phone");
        user.setRegistrationDate(LocalDate.of(1991, 2, 3));

        UserRoleEntity userRoleUser = userRoleService.findUserRoleEntityByRole(UserRoleEnum.USER);
        user.getRoles().add(userRoleUser);

        userRepository.save(user);
    }

    @Transient
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public List<UserEntity> getAllUsersOrderById() {
        return userRepository.getUserEntitiesByOrderById();
    }

    public List<UserView> getAllUserViews() {
        List<UserEntity> allUsersOrderById = getAllUsersOrderById();

        List<UserView> allUserViews = allUsersOrderById.stream().map(userEntity -> {

            UserView currentUserView = modelMapper.map(userEntity, UserView.class);

            currentUserView.getRoles().clear();
            for (UserRoleEntity role : userEntity.getRoles()) {
                currentUserView.getRoles().add(role.getRole().name());
            }
            currentUserView.getRoles().sort(String::compareTo);

            return currentUserView;
        }).toList();

        return allUserViews;
    }

    public String totalCurrentPrice(String username) {

        UserEntity currentUser = getUserByUsername(username);

        BigDecimal priceForAllDrinks = currentUser.getLastOrder().getDrinks().stream().map(DrinkEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal priceForAllFoods = currentUser.getLastOrder().getFoods().stream().map(FoodEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal priceForAllDrinks = allCurrentDrinkViews.stream()
//                .map(DrinkView::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal priceForAllFoods = allCurrentFoodViews.stream()
//                .map(FoodView::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String totalOrderPrice = priceForAllDrinks.add(priceForAllFoods).toString();

        return totalOrderPrice;
    }

    public void addAdmin(@AuthenticationPrincipal UserDetails userDetails, Long id) {

        Optional<UserEntity> currentUserOpt = userRepository.findById(id);
        if (currentUserOpt.isEmpty()) {
            throw new NoSuchElementException();
        }
        UserEntity currentUser = currentUserOpt.get();

        UserEntity loggedUser = findUserEntityByUsername(userDetails.getUsername());
        if (loggedUser.getId() == id) {
            return; // can not add admin role by himself
        }
        boolean isLoggedUserStillAdmin = false; // check if is still admin or somebody removed the role !!!
        for (UserRoleEntity role : loggedUser.getRoles()) {
            if (role.getRole().name().equals("ADMIN")) {
                isLoggedUserStillAdmin = true;
                break;
            }
        }

        if (isLoggedUserStillAdmin) {
            UserRoleEntity roleAdmin = userRoleService.findUserRoleEntityByRole(UserRoleEnum.ADMIN);
            currentUser.getRoles().add(roleAdmin);

            userRepository.save(currentUser);
        }
    }

    public void removeAdmin(Long id) {

        if (id == 1L) {
            return; // forbidden to remove the role of pesho
        }

        Optional<UserEntity> currentUserOpt = userRepository.findById(id);
        if (currentUserOpt.isEmpty()) {
            throw new NoSuchElementException();
        }
        UserEntity currentUser = currentUserOpt.get();

        currentUser.getRoles().removeIf(role -> role.getRole().name().equals("ADMIN"));
//        UserRoleEntity roleAdmin = userRoleService.findUserRoleEntityByRole(UserRoleEnum.ADMIN);
//        currentUser.getRoles().remove(roleAdmin);

        userRepository.save(currentUser);
    }

}
