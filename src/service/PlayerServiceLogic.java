package service;

import capture.Capture;
import inventory.Inventory;
import item.Item;
import item.ball.*;
import life.player.Enemy;
import life.player.Player;
import life.pokemon.Ggobugi;
import life.pokemon.Pikachu;
import life.pokemon.Pokemon;
import life.pokemon.Pyree;
import shop.Shop;
import ui.UserMenu;

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
//        System.out.println(stringBuilder); // 몬스터볼 몇개 있느지 담아 놓으 stringBuilder

        //포획하는 방법 설명

        System.out.println("포켓몬을 찾는중 . . . ");
        List<Pokemon> list = Arrays.asList(new Pikachu(), new Ggobugi(), new Pyree()); //포켓몬 랜덤 발생
        Collections.shuffle(list); //collections shuffle 메서드
        Pokemon pokemon = null;
        System.out.println();
        String name = list.get(0).getName();
        if(name =="꼬부기"){
             pokemon = new Ggobugi();
        }else if(name =="피카츄"){
             pokemon = new Pikachu();
        }else{
            pokemon = new Pyree();
        }
        System.out.println("야생의 " + pokemon.getName() +"(이)가 나타났다.");
        System.out.println(pokemon.toString()); // 포켓몬 정보 출력

        // 포획하기 (인벤토리 사용가능한 볼x개수 리스트 출력)
        System.out.println();
        System.out.println("========================= 사용자 ui =========================");
        System.out.println("1. 싸우기");
        System.out.println("2. 도망치기");
        System.out.println("=============================================================");

        Scanner sc = new Scanner(System.in);

        while(true){
            try{
                int userChoose = sc.nextInt();
                switch (userChoose){
                    case 1:
                        captureFight(pokemon);
                        return true;
                    case 2:
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
    }

    @Override
    public void fight() {
        int i=0;
        int j=0;
        try{
            System.out.println("로켓단을 찾는중 . . .");
            Thread.sleep(3000);
            Enemy enemy = new Enemy("로사");
            enemy.addPokemon(new Pyree());
            enemy.addPokemon(new Ggobugi());
            System.out.println(enemy.getName() + "(이)가 싸움을 걸어온다.");
            int turn = 0;
            while(true) {
                if (turn == 0) {
                    System.out.println("======메뉴를 선택해주세요=====");
                    System.out.println("1. 대결한다.");
                    System.out.println("2. 포기한다.");
                    System.out.print(":");
                    int menu = scanner.nextInt();
                    System.out.println();

                    if (menu == 1) {

                        while (true) {

                            Pokemon myPokemon = inventory.getPokemons().get(i);
                            Pokemon enemyPokemon = enemy.getPokemons().get(j);

                            if (myPokemon.getHp() <= 0) {
                                System.out.println("****" + player.getName() + "의" + myPokemon.getName() + "이(가) 졌습니다.");

                                if (i < inventory.getPokemons().size() - 1) { // 내 포켓몬이 더 있는지 체크
                                    i++;
                                    continue;
                                } else {
                                    System.out.println(enemy.getName() +" 에게 패배하였습니다.");
                                    i = 0;
                                    j = 0;
                                    break;
                                }
                            } else if (enemyPokemon.getHp() <= 0) {
                                System.out.println("****" + enemy.getName() + "의" + enemyPokemon.getName() + "이(가) 졌습니다.");
                                for (int k = 0; k < enemy.getPokemons().size(); k++) {
                                    enemy.getPokemons().get(k).setHp(100);
                                }

                                if (j < enemy.getPokemons().size() - 1) { // 로켓단 포켓몬이 더 있는지 체크
                                    j++;
                                    continue;
                                } else {
                                    System.out.println(enemy.getName() + "에게 승리하였습니다.");
                                    i = 0;
                                    j = 0;
                                    break;
                                }
                            } else if (inventory.getPokemons().isEmpty() || enemy.getPokemons().isEmpty())
                                break;
                            System.out.println();
                            System.out.println("<" + enemy.getName() + "(와)과 배틀을 시작합니다>");
                            System.out.println();
                            System.out.print(myPokemon.getName() + "의 현재HP: " + myPokemon.getHp() + "  ");
                            System.out.println(enemyPokemon.getName() + "의 현재HP: " + enemyPokemon.getHp());
                            System.out.println();
                            System.out.println("*" + player.getName() + "의 차례");
//                            System.out.println("0번:아이템");
                            myPokemon.printSkills();

                            System.out.print("몇번 스킬을 사용할까요??\n:");

                            int sk = scanner.nextInt();
                            if (sk == 1) {
                                myPokemon.skill1(enemyPokemon);
                            } else if (sk == 2) {
                                myPokemon.skill2(enemyPokemon);
                            } else {
                                myPokemon.skill3(enemyPokemon);
                            }
//                            else if(sk==0) {
//                                if(inventory.getItems().size()==0) {
//                                    System.out.println("*현재 보유한 아이템이 없습니다");
//                                }else {
//                                    inventory.getItems().get(0).useItem(yours);
//                                }
//                            }
                            System.out.println("*" + enemy.getName() + "의 차례");
                            int random = (int) (Math.random() * 3 + 1);
                            if (random == 1) {
                                enemyPokemon.skill1(myPokemon);
                            } else if (random == 2) {
                                enemyPokemon.skill2(myPokemon);
                            } else {
                                enemyPokemon.skill3(myPokemon);
                            }
                        }
                    }else if(menu == 2){
                        System.out.println("도망쳐!!!");
                        return;
                    }else{
                        System.out.println("잘못된 메뉴입니다.");
                        break;
                    }
                }
                break;
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void captureFight(Pokemon wildPokemon){
        while(true) {

            System.out.printf("<%s(와)과 배틀을 시작합니다>\n", wildPokemon.getName());
            System.out.println();
            Pokemon myPokemon = Inventory.getInstance().getPokemons().get(0);
            System.out.println(myPokemon.getName() + "의 현재HP : " + myPokemon.getHp());
            System.out.println("야생의 "+wildPokemon.getName() + "의 현재HP : " + wildPokemon.getHp());
            System.out.println();
            System.out.println("*"+player.getName()+"의 차례");
            System.out.println();
            System.out.println("0번:아이템");
            myPokemon.printSkills();
            System.out.println();
            System.out.print("몇번 스킬을 사용할까요??\n:");

            int select = scanner.nextInt();
            if (select == 1) {
                myPokemon.skill1(wildPokemon);
            } else if (select == 2) {
                myPokemon.skill2(wildPokemon);
            } else if (select == 0) {
                //인벤토리
                whichItem(wildPokemon ,wildPokemon.getHp());
                break;

            }
            System.out.println();
            System.out.println();
            System.out.println("야생의 " + wildPokemon.getName() + "의 차례");
            System.out.println();
            int random = (int) (Math.random() * 3 + 1);
           System.out.println(random);
            if (random == 1) {
                wildPokemon.skill1(myPokemon);
            } else if (random == 2) {
                wildPokemon.skill2(myPokemon);
            } else {
                wildPokemon.skill3(myPokemon);
            }
        }
    }
    private void whichItem(Pokemon pokemon, int hp) {
        List<Integer> counts = countItems(getInventory().getItems());
        System.out.println("------------------ << 인벤토리 >> -----------------");
        System.out.printf("[몬스터볼 x%d | 수퍼볼 x%d | 하이퍼볼 x%d | 마스터볼 x%d]\n", counts.get(0), counts.get(1), counts.get(2), counts.get(3));
        System.out.printf("[엘릭서 x%d | 파워엘릭서 x%d]\n", counts.get(4), counts.get(5));
        System.out.println("-------------------------------------------------");
        System.out.println("사용할 아이템을 선택해주세요.");
        for (int i = 1; i <= inventory.getItems().size() ; i++) {
            System.out.println(i + "번 : "  + inventory.getItems().get(i-1).getName());
        }
        loop1: while (true){
            int select2 = scanner.nextInt();
            switch (select2){
                case 1:
                    if(counts.get(0) == 0){
                        System.out.println();
                        System.out.println("보유하신 몬스터볼이 없습니다.");
                        System.out.println();
                        whichItem(pokemon, hp);
                        return;
                    }else{
                        System.out.println("몬스터볼을 사용합니다.");
                        inventory.getItems().get(0).decreaseQuantity();
                        Capture capture = new Capture();
                        boolean tr =  capture.getCaptureResult(new MonsterBall(), pokemon, hp); // 인자안에 몬스터볼 종류
                        if(tr){
                            //잡힘
                            inventory.getPokemons().add(pokemon);
                            System.out.println("==========================================");
                            System.out.println(pokemon.toString());
                            break loop1;
                        }else{
                            System.out.println("놓침");
                            return;
                        }
                    }
                case 2:
                    if(counts.get(1) == 0){
                        System.out.println();
                        System.out.println("보유하신 수퍼볼이 없습니다.");
                        System.out.println();
                        whichItem(pokemon, hp);
                        return;
                    }else{
                        System.out.println("수퍼볼을 사용합니다.");
                        break;
                    }
                case 3:
                    if(counts.get(2) == 0){
                        System.out.println();
                        System.out.println("보유하신 하이퍼볼이 없습니다.");
                        System.out.println();
                        whichItem(pokemon, hp);
                        return;
                    }else{
                        System.out.println("하이퍼볼을 사용합니다.");
                        break;
                    }
                case 4:
                    if(counts.get(3) == 0){
                        System.out.println();
                        System.out.println("보유하신 마스터볼이 없습니다.");
                        System.out.println();
                        whichItem(pokemon, hp);
                        return;
                    }else{
                        System.out.println("마스터볼을 사용합니다.");
                        break;

                    }
                case 5:
                    if(counts.get(4) == 0){
                        System.out.println();
                        System.out.println("보유하신 엘릭서가 없습니다.");
                        System.out.println();
                        whichItem(pokemon, hp);
                        return;
                    }else{
                        System.out.println("엘릭서를 사용합니다.");
                        System.out.println(pokemon.getName());
                        break;
                    }
                case 6:
                    if(counts.get(5) == 0){
                        System.out.println();
                        System.out.println("보유하신 파워엘릭서가 없습니다.");
                        System.out.println();
                        whichItem(pokemon, hp);
                        return;
                    }else{
                        System.out.println("파워엘릭서를 사용합니다.");
                        break;
                    }
            }

        }
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
