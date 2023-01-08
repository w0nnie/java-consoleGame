package item.potion;

public class Elixir extends Potion {


    public Elixir(){
        this(0);
    }
    public Elixir(int quantity) {
        super("엘릭서", quantity, 1000, 50);
    }
}
