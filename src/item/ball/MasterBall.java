package item.ball;

public class MasterBall extends Ball{


    public MasterBall(){
        this(10);
    }
    public MasterBall(int quantity) {
        super("마스터볼", quantity, 4000, 100);
    }
}
