package service;

import inventory.Inventory;
import item.Item;
import life.player.Player;
import life.pokemon.Ggobugi;
import life.pokemon.Pikachu;
import life.pokemon.Pokemon;
import life.pokemon.Pyree;
import shop.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerServiceLogic implements PlayerService {
    private boolean isAlreadyStarted;
    private Player player;
    private Inventory inventory;
    private Scanner scanner;

    public PlayerServiceLogic(){
        this.scanner = new Scanner(System.in);
        this.isAlreadyStarted = false;
    }

    @Override
    public void newGame() {
        setAlreadyStarted(initialize());
        if(!isAlreadyStarted()) {
            showStory();
            while (true) {
                int selectNum = selectPokemon();
                System.out.println();
                System.out.println();
                if (selectNum != 0) {
                    switch (selectNum) {
                        case 1:
                            System.out.println("피카츄와 함께 떠나는 모험!");
                            getInventory().setPokemons(new Pikachu());
                            System.out.println();
                            System.out.println();
                            return;
                        case 2:
                            System.out.println("파이리와 함께 떠나는 모험!");
                            getInventory().setPokemons(new Pyree());
                            System.out.println();
                            System.out.println();
                            return;
                        case 3:
                            System.out.println("꼬부기와 함께 떠나는 모험!");
                            getInventory().setPokemons(new Ggobugi());
                            System.out.println();
                            System.out.println();
                            return;
                        default:
                            break;
                    }
                }
            }
        } else {
            System.out.println("이미 시작 포켓몬을 고르셨습니다.");
        }
    }

    @Override
    public void loadGame() {

    }

    @Override
    public void myPage() {
        System.out.println();
        System.out.println();
        System.out.println("------------------ << 내정보 >> ------------------");
        System.out.println("| HP : " + getPlayer().getHp());
        System.out.println("| -----------------------------------------------");
        System.out.printf("| 보유 머니 : %d\n", getPlayer().getMoney());
        System.out.println("-------------------------------------------------");
        System.out.println();
        System.out.println("------------------ << 인벤토리 >> -----------------");
        List<Integer> counts = countItems(getInventory().getItems());
        System.out.printf("[몬스터볼 x%d | 수퍼볼 x%d | 하이퍼볼 x%d | 마스터볼 x%d]\n", counts.get(0), counts.get(1), counts.get(2), counts.get(3));
        System.out.printf("[엘릭서 x%d | 파워엘릭서 x%d]\n", counts.get(4), counts.get(5));
        System.out.println("-------------------------------------------------");
        for(Pokemon pokemon : getInventory().getPokemons()) {
            System.out.println(pokemon);
        }
        System.out.println("-------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    private List<Integer> countItems(List<Item> items){
        List<Integer> counts = new ArrayList<>();
        for(Item item : items){
            counts.add(item.getQuantity());
        }

        return counts;
    }

    @Override
    public void shopping() {
        Shop shop = new Shop();
        shop.shopping();
    }

    @Override
    public void catchMon() {

    }

    @Override
    public void fight() {

    }

    @Override
    public void relax() {

        while(true) {
            System.out.println();
            System.out.println();
            System.out.println("휴식의 방에 입장하셨습니다.");
            int selectNumber = selectRelax();
            switch (selectNumber) {
                case 1:
                    whichRelax("의자", 20, 500, 3000);
                    break;
                case 2:
                    whichRelax("침대", 40, 1000, 5000);
                    break;
                case 3:
                    whichRelax("사우나", 100, 2000, 10000);
                    break;
                case 0:
                    return;
                default:
                    break;

            }
        }

    }

    private void whichRelax(String which, int increaseHp, int price, int time){
        System.out.println();
        System.out.println();
        System.out.printf("지우가 %s에서 휴식을 합니다...\n", which);
        getPlayer().setHp(getPlayer().getHp() + increaseHp);
        getPlayer().setMoney(getPlayer().getMoney() - price);
        try {
            int sec = time / 1000;
            for(int i=sec; i>0; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println("휴식을 끝마쳤습니다.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("현재 HP : %d\n", getPlayer().getHp());
    }

    @Override
    public void saveGame() {

    }

    public boolean isAlreadyStarted() {
        return isAlreadyStarted;
    }

    public void setAlreadyStarted(boolean alreadyStarted) {
        isAlreadyStarted = alreadyStarted;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    private boolean initialize(){
        if(Inventory.getInstance().getPokemons().size() != 0){
            return true;
        } else {
            this.player = Player.getInstance();
            this.inventory = Inventory.getInstance();
            System.out.println();
            System.out.println();
            System.out.println("초기화 진행...");
            System.out.println();
            System.out.println();
            return false;
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

    private int selectRelax(){
        int selectNum = 0;
        while(true) {
            System.out.println("어떤 휴식을 할지 선택해주세요.");
            System.out.println("1. 앉기, 가격 : 500, 소요시간 : 3초, HP 회복량 : 20");
            System.out.println("2. 눕기, 가격 : 1000, 소요시간 : 5초, HP 회복량 : 40");
            System.out.println("3. 사우나, 가격 : 2000, 소요시간 : 10초, HP 회복량 : 100");
            System.out.println("0. 돌아가기");
            selectNum = scanner.nextInt();
            if(selectNum >= 0 && selectNum <= 3){
                return selectNum;
            } else {
                System.out.println("잘못된 선택입니다.");
                return -1;
            }
        }
    }

    private void showStory() {
        System.out.println("시작 스토리 출력...");
        System.out.println();
        System.out.println();
    }
}
