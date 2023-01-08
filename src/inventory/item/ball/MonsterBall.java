package inventory.item.ball;

import inventory.item.Item;

public class MonsterBall extends Item {

    public MonsterBall(String name, int quantity, int price) {
        super(name, quantity, price);
    }

    public void spawn(){
        // 포켓볼에서 포켓몬을 소환하다
    }

    @Override
    public void use() {
        // 포켓볼을 사용해서 포켓몬을 잡다
    }
}
