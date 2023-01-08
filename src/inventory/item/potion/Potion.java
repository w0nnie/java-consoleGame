package inventory.item.potion;

import inventory.item.Item;

public class Potion extends Item {
    private int increaseHp;

    public Potion(String name, int quantity, int price, int increaseHp) {
        super(name, quantity, price);
        this.increaseHp = increaseHp;
    }

    public int getIncreaseHp() {
        return increaseHp;
    }

    @Override
    public void use() {

    }
}
