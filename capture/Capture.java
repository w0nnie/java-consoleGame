package capture;

import item.ball.Ball;
import item.ball.MasterBall;
import item.ball.MonsterBall;
import life.pokemon.Ggobugi;
import life.pokemon.Pokemon;
import service.PlayerServiceLogic;

public class Capture {

    private boolean trueOrFalse;

    public Capture(boolean trueOrFalse) {
        this.trueOrFalse = trueOrFalse;
    }

    public void getCaptureResult(Ball ball, Pokemon pokemon, int hp){
        System.out.println();
        System.out.println();
        System.out.println("------------------- <<포획결과>> -------------------");
        //선택한 몬스터볼로 분기 처리 실패 성공

        if(ball.getName() == "몬스터볼"  && hp >= 50){
            trueOrFalse =   false;
            return;
        }

        if(trueOrFalse){
            System.out.println("포획성공");
            PlayerServiceLogic playerServiceLogic = null;
            playerServiceLogic.getInventory().setPokemons(pokemon);
            //인벤토리

        }else{
            System.out.println("놓침");
        }
    }
}