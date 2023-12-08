package com.rms.service;

import com.rms.model.dto.RegisterDTO;
import com.rms.model.dto.UserDTO;
import com.rms.model.entity.*;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.model.views.OrderView;
import com.rms.model.views.UserView;
import com.rms.repository.UserRepository;
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
    private final DrinkService drinkService;
    private final FoodService foodService;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, UserRoleService userRoleService, ModelMapper modelMapper, OrderService orderService, DrinkService drinkService, FoodService foodService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
        this.drinkService = drinkService;
        this.foodService = foodService;
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
            orderService.saveOrder(newLastOrder);
            currentUser.setLastOrder(newLastOrder);
            saveUser(currentUser);
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

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public Long usersCount() {
        return userRepository.count();
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

        userRepository.save(currentUser);
    }

    public boolean checkIfUserHasOrders(String username) {
        UserEntity currentUser = getUserByUsername(username);

        return currentUser.getLastOrder() != null;
    }

    public void deleteDrinkFromLastOrder(String username, Long id) {
        UserEntity currentUser = getUserByUsername(username);

        if (currentUser.getLastOrder() == null) {
            return; // illegal
        }

        OrderEntity lastOrder = currentUser.getLastOrder();

        Optional<DrinkEntity> currentDrink = lastOrder.getDrinks().stream().filter(d -> d.getId().equals(id)).findAny();

        DrinkEntity drinkEntity = null;
        if (currentDrink.isPresent()) {
            drinkEntity = currentDrink.get();
        }

        if (drinkEntity != null) {
            lastOrder.getDrinks().remove(drinkEntity);
        }
        orderService.saveOrder(lastOrder);
        saveUser(currentUser);
    }

    public void addDrinkToLastOrder(String username, Long id) {

        UserEntity currentUser = getUserByUsername(username);

        if (currentUser.getLastOrder() == null) {
            OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
            currentUser.setLastOrder(newLastOrder);
        }

        DrinkEntity currentDrink = drinkService.findById(id);
        OrderEntity lastOrder = currentUser.getLastOrder();
        lastOrder.getDrinks().add(currentDrink);
        orderService.saveOrder(lastOrder);
        saveUser(currentUser);
    }

    public void addFoodToLastOrder(String username, Long id) {

        UserEntity currentUser = getUserByUsername(username);

        if (currentUser.getLastOrder() == null) {
            OrderEntity newLastOrder = orderService.createNewLastOrder(currentUser);
            currentUser.setLastOrder(newLastOrder);
        }

        FoodEntity currentFood = foodService.findById(id);
        OrderEntity lastOrder = currentUser.getLastOrder();
        lastOrder.getFoods().add(currentFood);
        orderService.saveOrder(lastOrder);
        saveUser(currentUser);
    }

    public void deleteFoodFromLastOrder(String username, Long id) {
        UserEntity currentUser = getUserByUsername(username);

        if (currentUser.getLastOrder() == null) {
           return; // illegal
        }

        OrderEntity lastOrder = currentUser.getLastOrder();

        FoodEntity currentFood = lastOrder.getFoods().stream().filter(f -> f.getId().equals(id)).findAny().get();
        lastOrder.getFoods().remove(currentFood);
        orderService.saveOrder(lastOrder);
        saveUser(currentUser);
    }

    public void addNewOrder(String username) {

        UserEntity currentUser = getUserByUsername(username);
        OrderEntity newOrder = orderService.makeNewOrder(currentUser);
        currentUser.getOrders().add(newOrder);

        currentUser.getLastOrder().getDrinks().clear();
        currentUser.getLastOrder().getFoods().clear();
        orderService.saveOrder(currentUser.getLastOrder());
        saveUser(currentUser);
    }

    public List<OrderView> getAllCurrentOrdersViews(String username) {

        UserEntity currentUser = getUserByUsername(username);
        boolean isAdmin = isAdmin(username);
        List<OrderEntity> orderEntities;

        if (isAdmin) {
            orderEntities = orderService.getAllOrders();
        } else {
            orderEntities = orderService.allOrdersByUsername(username);
        }

        if (!isAdmin) {
            Long idForLastOrder = currentUser.getLastOrder().getId();
            OrderEntity lastOrder = orderEntities.stream().filter(o -> o.getId().equals(idForLastOrder)).findFirst().orElse(null);
            if (lastOrder != null) {
                orderEntities.remove(lastOrder);
            }
        } else {
            OrderEntity menu = orderEntities.stream().filter(o -> o.getId() == 1L).findFirst().orElse(null);
            if (menu != null) {
                orderEntities.remove(menu);
            }
        }

        List<OrderView> allCurrentOrdersViews = orderEntities.stream().map(orderEntity -> {
            OrderView currentOrderView = modelMapper.map(orderEntity, OrderView.class);

            currentOrderView.setMadeBy(orderEntity.getMadeBy().getUsername());

            BigDecimal currentPriceForAllDrinks = orderEntity.getDrinks().stream().map(DrinkEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal currentPriceForAllFoods = orderEntity.getFoods().stream().map(FoodEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalOrderPrice = currentPriceForAllDrinks.add(currentPriceForAllFoods);

            currentOrderView.setTotalPrice(totalOrderPrice);

            String currentDateTime = orderEntity.getDateTime().toString(); // 2023-12-04T12:17:56.705155
            currentOrderView.setDate(currentDateTime.split("T")[0]);
            currentOrderView.setTime(currentDateTime.split("T")[1]);

            return currentOrderView;
        }).toList();

        List<OrderView> filteredOrdersWithZeroAmount = allCurrentOrdersViews.stream().filter(order -> order.getTotalPrice().compareTo(BigDecimal.ZERO) != 0).toList();

        return filteredOrdersWithZeroAmount;
    }
}
