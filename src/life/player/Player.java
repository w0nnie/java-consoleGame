package life.player;

import inventory.Inventory;

// 테스트 1
public class Player {
    private static Player playerInstance;
    private String name;
    private int hp;
    private int money;
    private int badge;

    private Player(){
        this.name = "지우";
        this.hp = 100;
        this.money = 0;
        this.badge = 0;
    }

    public static Player getInstance(){
        if(playerInstance == null){
            playerInstance = new Player();
        }
        return playerInstance;
    }

    public static void loadGame(){

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
        if(hp > 100){
            System.out.println("HP는 최대 100 을 넘을 수 없습니다.");
            setHp(100);
        } else if(hp <= 0){
            System.out.println("HP가 0 이여서 포획 및 전투를 할 수 없습니다.");
            System.out.println("체력을 회복해주세요.\n");
            setHp(0);
        } else {
            this.hp = hp;
        }
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBadge() { return  badge; }

    public void setBadge(int badge) { this.badge = badge; }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", money=" + money +
                ", badge=" + badge +
                '}';
    }
}
