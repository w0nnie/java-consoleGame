package service;

import inventory.Inventory;
import life.player.*;

import java.util.Scanner;

public class PlayerServiceLogic implements PlayerService {
    Scanner scanner;

    public PlayerServiceLogic(){
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void newGame() {
        initialize();
        showStory();
        while(true){
            int selectNum = selectPokemon();
            if(selectNum != 0){
                switch (selectNum){
                    case 1:
                        System.out.println("피카츄와 함께 떠나는 모험!");
                        break;
                    case 2:
                        System.out.println("파이리와 함께 떠나는 모험!");
                        break;
                    case 3:
                        System.out.println("꼬부기와 함께 떠나는 모험!");
                        break;
                    default:
                        break;
                }
            }
        }

    }

    private int selectPokemon() {
        int selectNum = 0;
        System.out.println("시작 포켓몬을 골라주세요.");
        System.out.println("1. 피카츄 | 2. 파이리 | 3. 꼬부기");
        selectNum = scanner.nextInt();
        if(selectNum >= 1 && selectNum <= 3) {
            return selectNum;
        } else {
            System.out.println("잘못된 선택입니다.");
            return 0;
        }

    }

    private void showStory() {
        System.out.println("시작 스토리 출력...");
    }

    @Override
    public void loadGame() {

    }

    @Override
    public void myPage() {

    }

    @Override
    public void shop() {

    }

    @Override
    public void catchMon() {

    }

    @Override
    public void fight() {

    }

    @Override
    public void relax() {

    }

    @Override
    public void saveGame() {

    }

    private void initialize(){
        Player player = new Player("지우", 100);
        Inventory inventory = new Inventory();
        System.out.println("초기화 진행...");
    }
}
