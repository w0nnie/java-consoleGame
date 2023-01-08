package shop;

import item.ball.*;
import item.potion.Elixir;
import item.potion.Potion;
import item.potion.PowerElixir;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Ball> balls;
    private List<Potion> potions;

    public Shop(){
        this.balls = new ArrayList<>();
        this.potions = new ArrayList<>();
        initializeShop();
    }

    public void displayShopItemList(){
        int index = 1;
        System.out.println();
        System.out.println();
        System.out.println("------------------- <<아이템샵>> -------------------");
        for(Ball ball : balls) {
            System.out.printf("%d. %s, 가격 : %d\n",index, ball.getName(), ball.getPrice());
            index++;
        }
        for(Potion potion : potions){
            System.out.printf("%d. %s, 가격 : %d\n",index, potion.getName(), potion.getPrice());
            index++;
        }
        System.out.println("--------------------------------------------------");
    }

    private void initializeShop(){
        balls.add(new MonsterBall());
        balls.add(new SuperBall());
        balls.add(new HyperBall());
        balls.add(new MasterBall());
        potions.add(new Elixir());
        potions.add(new PowerElixir());
    }
}
