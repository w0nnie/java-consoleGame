package service;

import inventory.Inventory;
import item.Item;
import life.player.Player;
import life.pokemon.Ggobugi;
import life.pokemon.Pikachu;
import life.pokemon.Pokemon;
import life.pokemon.Pyree;
import shop.Shop;

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
        System.out.println();
        System.out.println();
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
        int monsterBallCount = 0;
        int superBallCount = 0;
        int hyperBallCount = 0;
        int materBallCount = 0;
        int elixir = 0;
        int powerElixir = 0;
        for(Item item : getInventory().getItems()){
            switch (item.getName()) {
                case "몬스터볼" -> monsterBallCount = item.getQuantity();
                case "수퍼볼" -> superBallCount = item.getQuantity();
                case "하이퍼볼" -> hyperBallCount = item.getQuantity();
                case "마스터볼" -> materBallCount = item.getQuantity();
                case "엘릭서" -> elixir = item.getQuantity();
                case "파워엘릭서" -> powerElixir = item.getQuantity();
            }
        }
        System.out.printf("[몬스터볼 x%d | 수퍼볼 x%d | 하이퍼볼 x%d | 마스터볼 x%d]\n", monsterBallCount, superBallCount, hyperBallCount, materBallCount);
        System.out.printf("[엘릭서 x%d | 파워엘릭서 x%d]\n", elixir, powerElixir);
        System.out.println("-------------------------------------------------");
        for(Pokemon pokemon : getInventory().getPokemons()) {
            System.out.println(pokemon);
        }
        System.out.println("-------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    @Override
    public void shopping() {
        Shop shop = new Shop();
        shop.displayShopItemList();
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
}
