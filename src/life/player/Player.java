package life.player;

import inventory.Inventory;

// 테스트 1
public class Player {
    private static Player playerInstance;
    private String name;
    private int hp;
    private int money;
    private Inventory inventory = Inventory.getInstance();

    public Player(){
        this.name = "지우";
        this.hp = 100;
        this.money = 0;
    }

    public static Player getInstance(){
        if(playerInstance == null){
            playerInstance = new Player();
        }
        return playerInstance;
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", money=" + money +
                '}';
    }
}
