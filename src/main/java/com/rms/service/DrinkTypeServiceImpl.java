//package com.rms.service;
//
//import com.rms.model.entity.DrinkType;
//import com.rms.model.entity.DrinkTypeEnum;
//import com.rms.repositiry.DrinkTypeRepository;
////import com.rms.service.interfaces.DrinkTypeService;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//
//@Service
//public class DrinkTypeServiceImpl
////        implements DrinkTypeService
//{
//
//    private final DrinkTypeRepository drinkTypeRepository;
//
//    public DrinkTypeServiceImpl(DrinkTypeRepository drinkTypeRepository) {
//        this.drinkTypeRepository = drinkTypeRepository;
//    }
//
////    @Override
//    public void initDrinkTypes() {
//        if (drinkTypeRepository.count() != 0) {
//            return;
//        }
//
//        Arrays.stream(DrinkTypeEnum.values())
//                .forEach(drinkTypeEnum -> {
//                    DrinkType drinkType = new DrinkType();
//                    drinkType.setName(drinkTypeEnum);
//
//                    switch (drinkTypeEnum) {
//                        case COFFEE_BASED -> drinkType.setDescription("coffee, ristretto, latte, mocha, cappuccino, etc");
//                        case WINE -> drinkType.setDescription("red wine, white wine, rose, etc");
//                        case HOT_BEVERAGE -> drinkType.setDescription("green tea, black tea, herbal tea, fruit tea, hot-chocolate and etc");
//                        case JUICE -> drinkType.setDescription("orange juice, peach juice, grape juice, tropical mix, etc");
//                        case BEER -> drinkType.setDescription("scout, lager, wheat beer, ale, pilsner, pale ale, bock, etc");
//                        case SODA -> drinkType.setDescription("cola, pepsi, sprite, 7up, water");
//                        case COCKTAIL -> drinkType.setDescription("martini, mojito, margarita, bloody mary. negroni, daiquiri, etc");
//                        case ALCOHOLIC -> drinkType.setDescription("vodka, gin, brandy, rum, tequila, whiskey, ouzo, etc");
//                    }
//
//                    drinkTypeRepository.save(drinkType);
//                });
//    }
//}
