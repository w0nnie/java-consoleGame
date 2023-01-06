package player;

import inventory.Inventory;
import pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;
// 테스트 1
public class Player {
    private String name;
    private int hp;
    private int money;
    private Inventory items;
    private Inventory pokeballs;

    public Player(String name, int hp){
        this.name = name;
        this.hp = hp;
        this.money = 0;
        this.items = new Inventory();
        this.pokeballs = new Inventory();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Inventory getItems() {
        return items;
    }

    public void setItems(Inventory items) {
        this.items = items;
    }

    public Inventory getPokeballs() {
        return pokeballs;
    }

    public void setPokeballs(Inventory pokeballs) {
        this.pokeballs = pokeballs;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", money=" + money +
                '}';
    }
}
