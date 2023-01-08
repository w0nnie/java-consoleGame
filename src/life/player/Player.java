package life.player;

import inventory.Inventory;

// 테스트 1
public class Player {
    private String name;
    private int hp;
    private int money;
    private Inventory potions;
    private Inventory balls;

    public Player(String name, int hp){
        this.name = name;
        this.hp = hp;
        this.money = 0;
        this.potions = new Inventory();
        this.balls = new Inventory();
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

    public Inventory getPotions() {
        return potions;
    }

    public void setPotions(Inventory potions) {
        this.potions = potions;
    }

    public Inventory getBalls() {
        return balls;
    }

    public void setBalls(Inventory balls) {
        this.balls = balls;
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
