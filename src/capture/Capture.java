package capture;

import item.ball.Ball;
import item.ball.MasterBall;
import item.ball.MonsterBall;
import life.pokemon.Ggobugi;
import life.pokemon.Pokemon;
import service.PlayerServiceLogic;

public class Capture {

    private boolean trueOrFalse = true;

    public boolean getCaptureResult(Ball ball, Pokemon pokemon, int hp){
        System.out.println();
        System.out.println();
        System.out.println("------------------- <<포획결과>> -------------------");
        //선택한 몬스터볼로 분기 처리 실패 성공
        System.out.println(pokemon.getName() +" 넌 내꺼야!");
        System.out.println(pokemon.getName() +"을 잡았씁니다!");
        if(ball.getName() == "몬스터볼"  && hp > 30){
            trueOrFalse =   false;
            System.out.println("놓쳤습니다.");
            return trueOrFalse;
        }
        return trueOrFalse;
    }
}