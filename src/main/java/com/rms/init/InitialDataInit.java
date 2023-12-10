package com.rms.init;

import com.rms.model.entity.*;
import com.rms.model.entity.DrinkTypeEnum;
import com.rms.model.entity.FoodTypeEnum;
import com.rms.model.entity.UserRoleEnum;
import com.rms.repository.*;
import com.rms.service.Impl.DrinkServiceImpl;
import com.rms.service.Impl.FoodServiceImpl;
import com.rms.service.Impl.UserRoleServiceImpl;
import com.rms.service.Impl.UserServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class InitialDataInit {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ReviewRepository reviewRepository;
    private final UserServiceImpl userServiceImpl;
    private final UserRoleServiceImpl userRoleServiceImpl;
    private final PasswordEncoder encoder;
    private final FoodRepository foodRepository;
    private final DrinkRepository drinkRepository;
    private final OrderRepository orderRepository;
    private final FoodServiceImpl foodServiceImpl;
    private final DrinkServiceImpl drinkServiceImpl;

    public InitialDataInit(UserRepository userRepository, UserRoleRepository userRoleRepository, ReviewRepository reviewRepository, UserServiceImpl userServiceImpl, UserRoleServiceImpl userRoleServiceImpl, PasswordEncoder encoder, FoodRepository foodRepository, DrinkRepository drinkRepository, OrderRepository orderRepository, FoodServiceImpl foodServiceImpl, DrinkServiceImpl drinkServiceImpl) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.reviewRepository = reviewRepository;
        this.userServiceImpl = userServiceImpl;
        this.userRoleServiceImpl = userRoleServiceImpl;
        this.encoder = encoder;
        this.foodRepository = foodRepository;
        this.drinkRepository = drinkRepository;
        this.orderRepository = orderRepository;
        this.foodServiceImpl = foodServiceImpl;
        this.drinkServiceImpl = drinkServiceImpl;
    }

    public void initUserRoles() {
        if (userRoleRepository.count() != 0) {
            return;
        }

        Arrays.stream(UserRoleEnum.values())
                .forEach(userRoleEnum -> {
                    UserRoleEntity userRole = new UserRoleEntity();
                    userRole.setRole(userRoleEnum);

                    switch (userRoleEnum) {
                        case ADMIN -> userRole.setDescription("The owner of the company");
                        case USER -> userRole.setDescription("general user");
                    }

                    userRoleRepository.save(userRole);
                });
    }

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
        UserRoleEntity roleAdmin = userRoleServiceImpl.findUserRoleEntityByRole(UserRoleEnum.ADMIN);
        admin.getRoles().add(roleAdmin);
        UserRoleEntity roleUser = userRoleServiceImpl.findUserRoleEntityByRole(UserRoleEnum.USER);
        admin.getRoles().add(roleUser);

        userRepository.save(admin);
    }

    public void initUsers() {

        if (userRepository.count() > 1) {
            return;
        }

        initUser("client1");
        initUser("client2");
        initUser("client3");
        initUser("client4");
        initUser("client5");

    }

    private void initUser(String username) {

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(username + "@pripesho.com");
        user.setPassword(encoder.encode("123"));
        user.setFirstName(username + "_F_name");
        user.setLastName(username + "_L_name");
        user.setAddress(username + "_address");
        user.setPhone(username + "_phone");
        user.setRegistrationDate(LocalDate.of(1991, 2, 3));

        UserRoleEntity userRoleUser = userRoleServiceImpl.findUserRoleEntityByRole(UserRoleEnum.USER);
        user.getRoles().add(userRoleUser);

        userRepository.save(user);
    }

    public void initReviews() {
        if (reviewRepository.count() != 0) {
            return;
        }

        Random random = new Random();
        int reviewCount = 10;
        while (reviewCount-- > 0) {
            Long randomUserId = random.nextLong(userServiceImpl.usersCount()) + 1;
            Optional<UserEntity> user = userServiceImpl.findById(randomUserId);

            UserEntity userEntity = user.orElse(null);

            if (userEntity != null) {
                ReviewEntity review = new ReviewEntity();
                review.setPostedOn(randomDate());
                review.setContent("Random review text for " + userEntity.getEmail());
                int randomScore = random.nextInt(5) + 1;
                review.setScore(randomScore);
                review.setWrittenBy(userEntity);
                reviewRepository.save(review);
            }
        }
    }

    private LocalDate randomDate() {
        int year = 2023;
        int month = ThreadLocalRandom.current().nextInt(1, 13);
        int day = ThreadLocalRandom.current().nextInt(1, LocalDate.of(year, month, 1).lengthOfMonth() + 1);
        return LocalDate.of(year, month, day);
    }

    public void initFoods() {

        if (foodRepository.count() != 0) {
            return;
        }

        Random random = new Random();

        initFood(random, "Шопса салата", FoodTypeEnum.SALAD);
        initFood(random, "Овчарска салата", FoodTypeEnum.SALAD);
        initFood(random, "Салата зеле с моркови", FoodTypeEnum.SALAD);
        initFood(random, "Гръцка салата", FoodTypeEnum.SALAD);
        initFood(random, "Мусака", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Боб яхния", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Пиле с картофи", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Свинско със зеле", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Свинска кавърма", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Сандвич с шунка", FoodTypeEnum.SANDWICH);
        initFood(random, "Сандвич с кашкавал", FoodTypeEnum.SANDWICH);
        initFood(random, "Веган сандвич", FoodTypeEnum.SANDWICH);
        initFood(random, "Пица Сицилиана", FoodTypeEnum.PIZZA);
        initFood(random, "Пица Вегетариана", FoodTypeEnum.PIZZA);
        initFood(random, "Пица Неаполитана", FoodTypeEnum.PIZZA);
        initFood(random, "Пица Пеперони", FoodTypeEnum.PIZZA);
        initFood(random, "Пица Маргарита", FoodTypeEnum.PIZZA);
        initFood(random, "Ребърца", FoodTypeEnum.BBQ);
        initFood(random, "Вратна пържола", FoodTypeEnum.BBQ);
        initFood(random, "Свински сач", FoodTypeEnum.BBQ);
        initFood(random, "Кюфте", FoodTypeEnum.BBQ);
        initFood(random, "Кебапче", FoodTypeEnum.BBQ);
        initFood(random, "Наденичка", FoodTypeEnum.BBQ);
        initFood(random, "Пилешко шишче", FoodTypeEnum.BBQ);
        initFood(random, "Пилешка супа", FoodTypeEnum.SOUP);
        initFood(random, "Супа топчета", FoodTypeEnum.SOUP);
        initFood(random, "Картофена супа", FoodTypeEnum.SOUP);
        initFood(random, "Крем супа", FoodTypeEnum.SOUP);
        initFood(random, "Крем карамел", FoodTypeEnum.DESSERT);
        initFood(random, "Суфле", FoodTypeEnum.DESSERT);
        initFood(random, "Сладолед", FoodTypeEnum.DESSERT);
        initFood(random, "Торта", FoodTypeEnum.DESSERT);
        initFood(random, "Питка", FoodTypeEnum.BREAD);
        initFood(random, "Бял хляб", FoodTypeEnum.BREAD);
        initFood(random, "Пърленка", FoodTypeEnum.BREAD);
        initFood(random, "Леща", FoodTypeEnum.VEGETARIAN);
        initFood(random, "Спанак", FoodTypeEnum.VEGETARIAN);
        initFood(random, "Картофи", FoodTypeEnum.VEGETARIAN);
        initFood(random, "Ориз", FoodTypeEnum.VEGETARIAN);

    }

    private void initFood(Random random, String foodName, FoodTypeEnum foodTypeEnum) {
        double randomValuePrice = 5 + (10 - 5) * random.nextDouble(); // random between 5 and 10
        double randomValueCalories = 100 + (500 - 100) * random.nextDouble(); // random between 100 and 500

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setName(foodName);
        foodEntity.setPrice(BigDecimal.valueOf(randomValuePrice));
        foodEntity.setType(foodTypeEnum);

        foodEntity.setSize(random.nextInt(301) + 200); // random int from 300 to 500
        foodEntity.setKcal(BigDecimal.valueOf(randomValueCalories));

        foodRepository.save(foodEntity);

    }

    public void initDrinks() {

        if (drinkRepository.count() != 0) {
            return;
        }

        Random random = new Random();

        initDrink(random, "Кафе", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Капучино", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Лате", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Фрапе", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Червено вино", DrinkTypeEnum.WINE);
        initDrink(random, "Бяло вино", DrinkTypeEnum.WINE);
        initDrink(random, "Розе", DrinkTypeEnum.WINE);
        initDrink(random, "Шампанско", DrinkTypeEnum.WINE);
        initDrink(random, "Плодов чай", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Горещ шоколад", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Зелен чай", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Черен чай", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Ябълков сок", DrinkTypeEnum.JUICE);
        initDrink(random, "Портокалов сок", DrinkTypeEnum.JUICE);
        initDrink(random, "Сок от ананас", DrinkTypeEnum.JUICE);
        initDrink(random, "Сок от моркови", DrinkTypeEnum.JUICE);
        initDrink(random, "Бира Лагер", DrinkTypeEnum.BEER);
        initDrink(random, "Бира Ейл", DrinkTypeEnum.BEER);
        initDrink(random, "Бира Пилснер", DrinkTypeEnum.BEER);
        initDrink(random, "Тъмна бира", DrinkTypeEnum.BEER);
        initDrink(random, "Кока кола", DrinkTypeEnum.SODA);
        initDrink(random, "Фанта", DrinkTypeEnum.SODA);
        initDrink(random, "Спрайт", DrinkTypeEnum.SODA);
        initDrink(random, "Тоник", DrinkTypeEnum.SODA);
        initDrink(random, "Маргарита", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Дайкири", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Манхатън", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Секс на плажа", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Ром", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Водка", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Уиски", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Джин", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Текила", DrinkTypeEnum.ALCOHOLIC);

    }

    private void initDrink(Random random, String drinkName, DrinkTypeEnum drinkType) {
        double randomValuePrice = 5 + (10 - 5) * random.nextDouble(); // random between 5 and 10

        DrinkEntity drinkEntity = new DrinkEntity();
        drinkEntity.setName(drinkName);
        drinkEntity.setPrice(BigDecimal.valueOf(randomValuePrice));
        drinkEntity.setType(drinkType);

        drinkEntity.setVolume(random.nextInt(101) + 100); // random int from 100 to 200

        drinkRepository.save(drinkEntity);
    }

    public void initMenuOrder() {

        if (orderRepository.count() != 0) {
            return;
        }

        OrderEntity menu = new OrderEntity();

        menu.setCompleted(true);
        menu.setPaid(true);
        menu.setDateTime(LocalDateTime.now());
        Set<FoodEntity> allFoodsInMenu = foodServiceImpl.findAllBy();
        Set<DrinkEntity> allDrinksInMenu = drinkServiceImpl.findAllBy();
        menu.getDrinks().addAll(allDrinksInMenu);
        menu.getFoods().addAll(allFoodsInMenu);

        orderRepository.save(menu);
    }
}
