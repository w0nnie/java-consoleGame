package item.ball;

public class HyperBall extends Ball{



    public HyperBall(){
        this(1);
    }
    public HyperBall(int quantity) {
        super("하이퍼볼", quantity, 3000);
    }
}
