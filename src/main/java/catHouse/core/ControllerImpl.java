package catHouse.core;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.BaseToy;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller{
    private ToyRepository toys;
    private Collection<House> houses;

    public ControllerImpl() {
        this.houses = new ArrayList<>();
        this.toys = new ToyRepository();
    }

    @Override
    public String addHouse(String type, String name) {
        House house = null;
        switch(type){
            case "ShortHouse":
                house = new ShortHouse(name);
                houses.add(house);
                break;
            case "LongHouse":
                house = new LongHouse(name);
                houses.add(house);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_HOUSE_TYPE);
        }
        //to see if type is wrong
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_HOUSE_TYPE,type);
    }

    @Override
    public String buyToy(String type) {
        Toy toy = null;
        switch (type){
            case "Ball":
               toy = new Ball();
                toys.buyToy(toy);
                break;
            case "Mouse":
                toy = new Mouse();
                toys.buyToy(toy);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_TOY_TYPE);
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_TYPE,type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        Toy toy = null;
        switch (toyType){
            case "Ball":
                toy = new Ball();
                for(House house:houses){
                    if(house.getName().equals(houseName)){
                        house.buyToy(toy);
                    }
                }
                break;
            case "Mouse":
                toy = new Mouse();
                for(House house:houses){
                    if(house.getName().equals(houseName)){
                        house.buyToy(toy);
                    }
                }

                break;
            default:
                throw new IllegalArgumentException(String.format(ExceptionMessages.NO_TOY_FOUND,toyType));
        }

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_IN_HOUSE,toyType,houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        Cat cat = null;
        boolean canLive = false;
        switch (catType){
            case "ShorthairCat":
                cat = new ShorthairCat(catName,catBreed,price);
                for(House house:houses){
                    if(house.getName().equals(houseName)){
                        house.addCat(cat);
                        if(house.getClass().getSimpleName().equals("ShortHouse")){
                            canLive = true;
                        }
                    }
                }
                break;
            case "LonghairCat":
                cat = new LonghairCat(catName,catBreed,price);
                for(House house:houses){
                    if(house.getName().equals(houseName)){
                        house.addCat(cat);
                        if(house.getClass().getSimpleName().equals("LongHouse")){
                            canLive = true;
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_CAT_TYPE);
        }
        if(canLive){
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CAT_IN_HOUSE,catType,houseName);
        }else{
            return String.format(ConstantMessages.UNSUITABLE_HOUSE);
        }

    }

    @Override
    public String feedingCat(String houseName) {

        int count = 0;
        for (House house : houses) {
            if (house.getName().equals(houseName)) {
                house.feeding();
                count = house.getCats().size();
            }

        }
        return String.format(ConstantMessages.FEEDING_CAT, count);

    }

    @Override
    public String sumOfAll(String houseName) {
        House exampleHouse = houses.stream().filter(house -> house.getName().equals(houseName)).findFirst().orElse(null);
        double sum = exampleHouse.getCats().stream().mapToDouble(Cat::getPrice).sum()
                + exampleHouse.getToys().stream().mapToDouble(Toy::getPrice).sum();
        return String.format(ConstantMessages.VALUE_HOUSE,houseName,sum);
    }

    @Override
    public String getStatistics() {
        StringBuilder stringBuilder = new StringBuilder();
        for(House house:houses){
            stringBuilder.append(house.getStatistics());
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

}
