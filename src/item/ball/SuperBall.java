package item.ball;

public class SuperBall extends Ball{


    public SuperBall(){
        this(1);
    }
    public SuperBall(int quantity) {
        super("수퍼볼", quantity, 2000);
    }
}
