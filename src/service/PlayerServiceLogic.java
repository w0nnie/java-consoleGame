package service;

import capture.Capture;
import inventory.Inventory;
import item.Item;
import item.ball.*;
import life.player.Player;
import life.pokemon.Ggobugi;
import life.pokemon.Pikachu;
import life.pokemon.Pokemon;
import life.pokemon.Pyree;
import shop.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

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
    public boolean catchMon() {
        //몬스터볼의 개수가 1개 이상일경우 해당 메뉴 진입가능
        Map<String, Item> ballCheck = new HashMap<>() {{ //HashMap 선언
            put("마스터볼", null);
            put("몬스터볼", null);
            put("수퍼볼", null);
            put("하이퍼볼", null);
        }};

        List<Item> items = Inventory.getInstance().getItems();
        for (int i = 0; i < items.size(); i++) {
            Item ball = items.get(i);
            if(ball instanceof Ball){
                ballCheck.put(ball.getName(), ball);
            }
        }
        int ballCount = 0;
        StringBuilder stringBuilder = new StringBuilder();

        Iterator<String> iter = ballCheck.keySet().iterator();
        while(iter.hasNext()) {
            String key = iter.next();
            Item ball = ballCheck.get(key);
            int qa = 0;
            if(ball != null){
                qa = ball.getQuantity();
                if(qa !=0){
                    ballCount ++;
                }

            }
            stringBuilder.append(String.format("%s x %d",key, qa));
        }

        if(ballCount == 0){
            System.out.println("보유하신 몬스터볼이 없습니다.");
            return false;
        }
        System.out.println(stringBuilder);
        Capture capture = new Capture(true);

        //포획하는 방법 설명

        System.out.println("포켓몬을 찾는중 . . . ");
        List<Pokemon> list = Arrays.asList(new Pikachu(), new Ggobugi(), new Pyree()); //포켓몬 랜덤 발생
        Collections.shuffle(list); //collections 라이버르리 shuffle 메서드
        Pokemon pokemon = new Pokemon(list.get(0).getName(), list.get(0).getHp(), list.get(0).getType());
        System.out.println("야생의 " + pokemon.getName() +"이 나타났다.");
        System.out.println(pokemon.toString()); // 포켓몬 정보 출력

        // 포획하기 (인벤토리 사용가능한 볼x개수 리스트 출력)
        System.out.println();
        System.out.println();
        System.out.println("========================= 사용자 ui =========================");
        System.out.println("1. 싸우기");
        System.out.println("2. 인벤토리");
        System.out.println("3. 도망치기");
        System.out.println("=============================================================");

        Scanner sc = new Scanner(System.in);

        while(true){
            try{
                int userChoose = sc.nextInt();
                switch (userChoose){
                    case 1:
                        System.out.println("싸우기");
                        break;
                    case 2:
                        //인벤토리 ui 따로 뺴기 .. 시간되면
                        System.out.println("------------------ << 인벤토리 >> -----------------");
                        List<Integer> counts = countItems(getInventory().getItems());
                        System.out.printf("[몬스터볼 x%d | 수퍼볼 x%d | 하이퍼볼 x%d | 마스터볼 x%d]\n", counts.get(0), counts.get(1), counts.get(2), counts.get(3));
                        System.out.println("-------------------------------------------------");
                        //1개이상 보유중인 몬스터볼 인덱스 순으로 선택가능하게
                        break;

                    case 3:
                        System.out.println("호 다 닥 . . .");
                        //Thread sleep
                        return false;
                    default:
                        System.out.println("메뉴를 잘못 입력하셨습니다.");
                }
            }catch (NullPointerException e){
                System.out.println("다시 입력해주세요.");
            }
        }
        //인벤토리 선택시 표출되는 부분

//        Ball ball = null;
//        pokemon.setHp(50);
//        int hp = pokemon.getHp();

        //턴제 방식 전투 및 몬스터볼(hp 양으로 차등)
        //결과( 포획 or
//        capture.getCaptureResult(ball, pokemon, hp); // 인자안에 몬스터볼 종류
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
