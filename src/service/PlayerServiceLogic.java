package service;

import inventory.Inventory;
import item.Item;
import item.ball.Ball;
import life.player.Enemy;
import life.player.Player;
import life.pokemon.*;
import shop.Shop;

import java.util.ArrayList;
import java.util.Collections;
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
                int selectNum = selectStartPokemon();
                System.out.println();
                System.out.println();
                if (selectNum != 0) {
                    switch (selectNum) {
                        case 1:
                            System.out.println("피카츄와 함께 떠나는 모험!\n\n");
                            getInventory().setPokemons(new Pikachu());
                            return;
                        case 2:
                            System.out.println("파이리와 함께 떠나는 모험!\n\n");
                            getInventory().setPokemons(new Pyree());
                            return;
                        case 3:
                            System.out.println("꼬부기와 함께 떠나는 모험!\n\n");
                            getInventory().setPokemons(new Ggobugi());
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
        System.out.println("------------------ << 내정보 >> ------------------");
        System.out.println("| HP : " + getPlayer().getHp());
        System.out.println("| -----------------------------------------------");
        System.out.printf("| 보유 머니 : %d\n", getPlayer().getMoney());
        System.out.println("-------------------------------------------------");
        System.out.printf("| 보유 뱃지 개수 : %d\n", getPlayer().getBadge());
        System.out.println("------------------ << 인벤토리 >> -----------------");
        List<Integer> counts = countItems(getInventory().getItems());
        System.out.printf("[몬스터볼 x%d | 수퍼볼 x%d | 하이퍼볼 x%d | 마스터볼 x%d]\n", counts.get(0), counts.get(1), counts.get(2), counts.get(3));
        System.out.printf("[엘릭서 x%d | 파워엘릭서 x%d]\n", counts.get(4), counts.get(5));
        System.out.println("-------------------------------------------------");
        for(Pokemon pokemon : getInventory().getPokemons()) {
            System.out.println(pokemon);
        }
        System.out.println("-------------------------------------------------");
    }

    @Override
    public void shopping() {
        Shop shop = new Shop();
        shop.shopping();
    }

    @Override
    public void catchMon() {
        System.out.printf("\n\n            !! 주의 !!        \n");
        System.out.printf("포획을 할 때 마다 지우의 체력이 10 감소합니다.\n\n");
        if(getPlayer().getHp() > 0) {
            int totalBallCount = 0;
            for (Item item : getInventory().getItems()) {
                if (item instanceof Ball) {
                    totalBallCount += item.getQuantity();
                }
            }
            if (totalBallCount == 0) {
                System.out.println("보유하신 볼이 하나도 없습니다.");
                System.out.println("상점에서 볼을 구매하세요.");
            } else {
                System.out.println("보유하신 볼이 있습니다.");
                Pokemon creature = searchCreature();
                Pokemon myPokemon = selectPokemon();
                if(myPokemon == null) {
                    return;
                }
                fightWithCreature(creature, myPokemon);
                getPlayer().setHp(getPlayer().getHp() - 10);
            }
        } else {
            System.out.println("지우의 체력이 0이여서 포획을 할 수 없습니다.");
            System.out.println("체력을 회복해주세요.");
        }
    }

    @Override
    public void fight() {
        System.out.printf("\n\n            !! 주의 !!        \n");
        System.out.printf("전투를 할 때 마다 지우의 체력이 10 감소합니다.\n\n");
        if(getPlayer().getHp() > 0) {
            Enemy rocketdan = searchRocketdan();
            Pokemon myPokemon = selectPokemon();
            if (myPokemon == null){ // selectPokemon() 0번 뒤로가기 클릭시
                return;
            }
            fightWithRocketdan(rocketdan, myPokemon);
            getPlayer().setHp(getPlayer().getHp() - 10);
            if (getPlayer().getHp() < 30) {
                System.out.println("지우의 체력이 얼마 남지 않았습니다.");
                System.out.printf("체력을 회복해주세요. HP : %d\n", getPlayer().getHp());
            }
        } else {
            System.out.println("지우의 체력이 0이여서 전투를 할 수 없습니다.");
            System.out.println("체력을 회복해주세요.");
        }
    }

    @Override
    public void relax() {

        while(true) {
            System.out.println("\n\n휴식의 방에 입장하셨습니다.");
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
            System.out.println("\n\n초기화 진행...\n\n");
            return false;
        }
    }

    private int selectStartPokemon() {
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
        System.out.println("시작 스토리 출력...\n\n");
    }

    private void whichRelax(String which, int increaseHp, int price, int time){
        System.out.printf("\n\n지우가 %s에서 휴식을 합니다...\n", which);
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

    private List<Integer> countItems(List<Item> items){
        List<Integer> counts = new ArrayList<>();
        for(Item item : items){
            counts.add(item.getQuantity());
        }

        return counts;
    }


    private Pokemon selectPokemon(){
        int index = 1;
        List<Pokemon> myPokemons = inventory.getPokemons();
        System.out.println("어떤 포켓몬으로 싸우시겠습니까?");
        System.out.println("0. 뒤로가기");
        for(Pokemon pokemon : myPokemons){
            System.out.printf("%d. %s  |  %d  |  %s\n", index, pokemon.getName(), pokemon.getHp(), pokemon.getType());
            index++;
        }
        int selectNum = scanner.nextInt();
        if(selectNum == 0){
            return null;
        }
        if(getInventory().getPokemons().get(selectNum-1).getHp() == 0){
            System.out.printf("%s의 체력이 0이여서 선택할수 없습니다.",myPokemons.get(selectNum-1).getName());
            selectPokemon();
        }
        return myPokemons.get(selectNum-1);
    }

    private Pokemon searchCreature(){
        System.out.print("포켓몬을 찾는 중");
        Pokemon creature = randomCreature();
        try {
            for(int i=0; i<3; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("\n\n야생의 %s(이)가 나타났다!!\n", creature.getName());
        System.out.printf("체력 : %d, 타입 : %s\n\n", creature.getHp(), creature.getType());
        return creature;
    }

    private Enemy searchRocketdan(){
        System.out.print("로켓단을 찾는 중");
        Enemy rocketdan = randomRocketdan();

        try {
            for(int i=0; i<3; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("\n\n로켓단의 %s(이)가 나타났다!!\n", rocketdan.getName());
        return rocketdan;
    }

    private void fightWithCreature(Pokemon creature, Pokemon myPokemon){
        System.out.println("------------------------------");
        System.out.println("1. 싸운다.");
        System.out.println("2. 도망간다.");
        System.out.println("------------------------------");
        int menuNum = scanner.nextInt();
        if(menuNum == 1){
            while(creature.getHp() > 0 && myPokemon.getHp() > 0) {
                System.out.println("\n\n1. 공격하기");
                System.out.println("2. 회복하기");
                System.out.println("3. 포획하기");
                System.out.println("4. 도망가기");
                int selectNum = scanner.nextInt();
                if(selectNum == 1){
                    int creatureDamage = (int) (Math.random() * 30 + 1);
                    // 공격하기
                    System.out.println("\n------------------------------");
                    myPokemon.printSkills();
                    System.out.println("------------------------------");
                    int selectSkill = scanner.nextInt();
                    switch (selectSkill){
                        case 1:
                            myPokemon.skill1(creature);
                            System.out.printf("\n(상대) %s(이)가 %d만큼 데미지를 주었습니다.\n", creature.getName(), creatureDamage);
                            myPokemon.setHp(myPokemon.getHp() - creatureDamage);
                            System.out.printf("(나) %s의 현재 체력 : %d\n", myPokemon.getName(), myPokemon.getHp());
                            break;
                        case 2:
                            myPokemon.skill2(creature);
                            System.out.printf("\n(상대) %s(이)가 %d만큼 데미지를 주었습니다.\n", creature.getName(), creatureDamage);
                            myPokemon.setHp(myPokemon.getHp() - creatureDamage);
                            System.out.printf("(나) %s의 현재 체력 : %d\n", myPokemon.getName(), myPokemon.getHp());
                            break;
                        case 3:
                            myPokemon.skill3(creature);
                            System.out.printf("\n(상대) %s(이)가 %d만큼 데미지를 주었습니다.\n", creature.getName(), creatureDamage);
                            myPokemon.setHp(myPokemon.getHp() - creatureDamage);
                            System.out.printf("(나) %s의 현재 체력 : %d\n", myPokemon.getName(), myPokemon.getHp());
                            break;
                    }
                } else if(selectNum == 2){
                    // 회복하기
                    System.out.println("\n\n------------------------------");
                    System.out.println("사용할 포션을 선택해주세요.");
                    System.out.printf("1. %s x%d\n", inventory.getItems().get(4).getName(), inventory.getItems().get(4).getQuantity());
                    System.out.printf("2. %s x%d\n", inventory.getItems().get(5).getName(), inventory.getItems().get(5).getQuantity());
                    System.out.println("------------------------------");
                    int selectNumber = scanner.nextInt();
                    if(inventory.getItems().get(selectNumber+3).getQuantity() == 0){
                        System.out.printf("%s 개수 0개!! 상점에서 구매해주세요.\n", inventory.getItems().get(3+selectNumber).getName());
                    } else if(inventory.getItems().get(selectNumber+3).getQuantity() != 0){
                        if(selectNumber == 1){
                            myPokemon.setHp(myPokemon.getHp() + 50);
                            System.out.printf("(나) %s의 HP를 50 회복했습니다. HP : %d\n", myPokemon.getName(), myPokemon.getHp());
                            inventory.getItems().get(4).decreaseQuantity();
                        } else if(selectNumber == 2){
                            myPokemon.setHp(myPokemon.getHp() + 100);
                            System.out.printf("(나) %s의 HP를 100 회복했습니다. HP : %d\n", myPokemon.getName(), myPokemon.getHp());
                            inventory.getItems().get(5).decreaseQuantity();
                        } else {
                            System.out.println("잘못 입력하셨습니다.");
                        }
                    }
                } else if(selectNum == 3){
                    // 포획하기
                    System.out.println("\n\n------------------------------");
                    System.out.println("사용할 볼을 선택해주세요.");
                    System.out.printf("1. %s x%d\n", inventory.getItems().get(0).getName(), inventory.getItems().get(0).getQuantity());
                    System.out.printf("2. %s x%d\n", inventory.getItems().get(1).getName(), inventory.getItems().get(1).getQuantity());
                    System.out.printf("3. %s x%d\n", inventory.getItems().get(2).getName(), inventory.getItems().get(2).getQuantity());
                    System.out.printf("4. %s x%d\n", inventory.getItems().get(3).getName(), inventory.getItems().get(3).getQuantity());
                    System.out.println("------------------------------");
                    int selectNumber = scanner.nextInt();
                    if(inventory.getItems().get(selectNumber-1).getQuantity() == 0){
                        System.out.printf("%s 개수 0개!! 상점에서 구매해주세요.\n", inventory.getItems().get(selectNumber-1).getName());
                    } else if(inventory.getItems().get(selectNumber-1).getQuantity() != 0){
                        if(selectNumber == 1){
                            if(creature.getHp() <= 30){
                                System.out.println("포획 성공!!");
                                inventory.setPokemons(creature);
                                inventory.getItems().get(selectNumber-1).decreaseQuantity();
                                return;
                            } else {
                                System.out.println("포획 실패!!");
                                inventory.getItems().get(selectNumber-1).decreaseQuantity();
                            }
                        } else if(selectNumber == 2){
                            if(creature.getHp() <= 50){
                                System.out.println("포획 성공!!");
                                inventory.setPokemons(creature);
                                inventory.getItems().get(selectNumber-1).decreaseQuantity();
                                return;
                            } else {
                                System.out.println("포획 실패!!");
                                inventory.getItems().get(selectNumber-1).decreaseQuantity();
                            }
                        } else if(selectNumber == 3){
                            if(creature.getHp() <= 70){
                                System.out.println("포획 성공!!");
                                inventory.setPokemons(creature);
                                inventory.getItems().get(selectNumber-1).decreaseQuantity();
                                return;
                            } else {
                                System.out.println("포획 실패!!");
                                inventory.getItems().get(selectNumber-1).decreaseQuantity();
                            }
                        } else if(selectNumber == 4){
                            if(creature.getHp() <= 100){
                                System.out.println("포획 성공!!");
                                inventory.setPokemons(creature);
                                inventory.getItems().get(selectNumber-1).decreaseQuantity();
                                return;
                            } else {
                                System.out.println("포획 실패!!");
                                inventory.getItems().get(selectNumber-1).decreaseQuantity();
                            }
                        } else {
                            System.out.println("잘못 입력하셨습니다.");
                        }
                    }
                } else if(selectNum == 4){
                    // 도망가기
                    System.out.println("호 다 닥 !!");
                    return;
                } else {
                    System.out.println("잘못 입력하셨습니다.");
                }
                if(creature.getHp() <= 0){
                    System.out.printf("\n\n(상대) %s(이)가 죽었습니다.\n", creature.getName());
                } else if(myPokemon.getHp() <= 0){
                    System.out.printf("\n\n(나) %s(이)가 죽었습니다.\n", myPokemon.getName());
                }
            }
        } else if(menuNum == 2){
            System.out.println("호 다 닥 !!");
        } else {
            System.out.println("잘못 입력하셨습니다.");
        }
    }


    private void fightWithRocketdan(Enemy rocketdan, Pokemon myPokemon){
        System.out.println("------------------------------");
        System.out.println("1. 싸운다.");
        System.out.println("2. 도망간다.");
        System.out.println("------------------------------");
        
        int turn = 0; // 로켓단과 나의 턴 변수
        int index = 0; //로켓단의 포켓몬 List index 변수
        int menuNum = scanner.nextInt();
        if(menuNum == 1){
            while(true) {

                Pokemon enemyPokemon = rocketdan.getPokemons().get(index);

                if (myPokemon.getHp() <= 0) {
                    System.out.println("****" + player.getName() + "의 " + myPokemon.getName() + "이(가) 졌습니다.");

                    if (getInventory().getPokemons().size() > 1) { // 내 포켓몬이 더 있는지 체크
                        myPokemon = selectPokemon();
                        continue;
                    } else {
                        System.out.printf("\n\n(나) %s(이)가 죽었습니다.\n", myPokemon.getName());
                        System.out.printf("\n\n(상대) %s와의 대결에서 패배하였습니다..\n", rocketdan.getName());
                        System.out.printf("\n\n                 << 전투 결과 >>                 \n");
                        System.out.printf("          로켓단과의 전투에서 패배하였습니다..\n\n");
                        System.out.printf("\n\n------------ 내 포켓몬 상태 ------------\n");
                        System.out.printf("    %s    |    %d    \n", myPokemon.getName(), myPokemon.getHp());
                        System.out.printf("--------------------------------------\n\n");
                        index = 0;
                        break;
                    }
                } else if (enemyPokemon.getHp() <= 0) {
                    System.out.println("****" + rocketdan.getName() + "의 " + enemyPokemon.getName() + "이(가) 졌습니다.");
                    for (int k = 0; k < rocketdan.getPokemons().size(); k++) {
                        rocketdan.getPokemons().get(k).setHp(100);
                    }

                    if (index < rocketdan.getPokemons().size() - 1) { // 로켓단 포켓몬이 더 있는지 체크
                        index++;
                        continue;
                    } else {
                        int randomReward = (int)(Math.random() * 5000 + 5000);
                        System.out.printf("\n\n(상대) %s와의 대결에서 승리하였습니다..\n", rocketdan.getName());
                        System.out.printf("\n\n                 << 전투 결과 >>                 \n");
                        System.out.printf("          로켓단과의 전투에서 승리하였습니다!!\n\n");
                        System.out.printf("\n\n------------ 내 포켓몬 상태 ------------\n");
                        System.out.printf("    %s    |    %d    \n", myPokemon.getName(), myPokemon.getHp());
                        System.out.printf("--------------------------------------\n\n");
                        System.out.printf("\n\n------------ 획득 보상 목록 ------------\n");
                        System.out.printf("1. 머니 : %d\n", randomReward);
                        System.out.println("2. 뱃지");
                        getPlayer().setMoney(getPlayer().getMoney() + randomReward);
                        getPlayer().setBadge(getPlayer().getBadge() + 1);
                        System.out.printf("--------------------------------------\n\n");
                        index = 0;
                        break;
                    }
                }else if(getInventory().getPokemons().isEmpty() || rocketdan.getPokemons().isEmpty()){
                    break;
                }

                System.out.println();
                System.out.println("<" + rocketdan.getName() + "(와)과 배틀을 시작합니다>");
                System.out.println();
                System.out.println(myPokemon.getName() + "의 현재HP: " + myPokemon.getHp() + "  ");
                System.out.println(enemyPokemon.getName() + "의 현재HP: " + enemyPokemon.getHp());
                System.out.println();
                System.out.println("*" + player.getName() + "의 차례");

                System.out.println("\n\n1. 공격하기");
                System.out.println("2. 회복하기");
                System.out.println("3. 도망가기");
                System.out.print(":");
                int selectNum = scanner.nextInt();
                if(selectNum == 1){
                    int creatureDamage = (int) (Math.random() * 30 + 1);
                    // 공격하기
                    System.out.println("\n------------------------------");
                    myPokemon.printSkills();
                    System.out.println("------------------------------");
                    int selectSkill = scanner.nextInt();
                    switch (selectSkill){
                        case 1:
                            myPokemon.skill1(enemyPokemon);
                            System.out.printf("\n(상대) %s(이)가 %d만큼 데미지를 주었습니다.\n", enemyPokemon.getName(), creatureDamage);
                            myPokemon.setHp(myPokemon.getHp() - creatureDamage);
                            System.out.printf("(나) %s의 현재 체력 : %d\n", myPokemon.getName(), myPokemon.getHp());
                            break;
                        case 2:
                            myPokemon.skill2(enemyPokemon);
                            System.out.printf("\n(상대) %s(이)가 %d만큼 데미지를 주었습니다.\n", enemyPokemon.getName(), creatureDamage);
                            myPokemon.setHp(myPokemon.getHp() - creatureDamage);
                            System.out.printf("(나) %s의 현재 체력 : %d\n", myPokemon.getName(), myPokemon.getHp());
                            break;
                        case 3:
                            myPokemon.skill3(enemyPokemon);
                            System.out.printf("\n(상대) %s(이)가 %d만큼 데미지를 주었습니다.\n", enemyPokemon.getName(), creatureDamage);
                            myPokemon.setHp(myPokemon.getHp() - creatureDamage);
                            System.out.printf("(나) %s의 현재 체력 : %d\n", myPokemon.getName(), myPokemon.getHp());
                            break;
                    }
                } else if(selectNum == 2){
                    // 회복하기
                    System.out.println("\n\n------------------------------");
                    System.out.println("사용할 포션을 선택해주세요.");
                    System.out.printf("1. %s x%d\n", getInventory().getItems().get(4).getName(), getInventory().getItems().get(4).getQuantity());
                    System.out.printf("2. %s x%d\n", getInventory().getItems().get(5).getName(), getInventory().getItems().get(5).getQuantity());
                    System.out.println("------------------------------");
                    int selectNumber = scanner.nextInt();
                    if(getInventory().getItems().get(selectNumber+3).getQuantity() == 0){
                        System.out.printf("%s 개수 0개!! 상점에서 구매해주세요.\n", getInventory().getItems().get(3+selectNumber).getName());
                    } else if(getInventory().getItems().get(selectNumber+3).getQuantity() != 0){
                        if(selectNumber == 1){
                            myPokemon.setHp(myPokemon.getHp() + 50);
                            System.out.printf("(나) %s의 HP를 50 회복했습니다. HP : %d\n", myPokemon.getName(), myPokemon.getHp());
                            getInventory().getItems().get(4).decreaseQuantity();
                        } else if(selectNumber == 2){
                            myPokemon.setHp(myPokemon.getHp() + 100);
                            System.out.printf("(나) %s의 HP를 100 회복했습니다. HP : %d\n", myPokemon.getName(), myPokemon.getHp());
                            getInventory().getItems().get(5).decreaseQuantity();
                        } else {
                            System.out.println("잘못 입력하셨습니다.");
                        }
                    }
                } else if(selectNum == 3){
                    // 도망가기
                    System.out.println("호 다 닥 !!");
                    return;
                } else {
                    System.out.println("잘못 입력하셨습니다.");
                }
            }
        } else if(menuNum == 2){
            System.out.println("호 다 닥 !!");
        } else {
            System.out.println("잘못 입력하셨습니다.");
        }
    }

    private Pokemon randomCreature(){
        List<Pokemon> creatures = new ArrayList<>();
        creatures.add(new Yeesanghaesee());
        creatures.add(new Peezon());
        creatures.add(new Poorin());
        creatures.add(new Ggomadol());
        creatures.add(new Chicorita());
        creatures.add(new Jammanbo());
        creatures.add(new Gorapaduk());

        Collections.shuffle(creatures);
        return creatures.get(0);
    }

    private Enemy randomRocketdan() {

        //player의 포켓몬 마리수 에 따라 차등적으로 다른 로켓단 출현
        //ex player.getPokemons.size() == 2
        //   losa.getPokemons().size() == 2

        //로사
        Enemy losa = new Enemy("로사");
        losa.addPokemon(new Chicorita());
        losa.addPokemon(new Peezon());

        //로이
        Enemy loy = new Enemy("로이");
        loy.addPokemon(new Ggomadol());
        loy.addPokemon(new Poorin());

        Enemy meowth = new Enemy("냐옹이");
        meowth.addPokemon(new Gorapaduk());
        meowth.addPokemon(new Jammanbo());

        List<Enemy> rocketdan = new ArrayList<>();

        rocketdan.add(losa);
        rocketdan.add(loy);
        rocketdan.add(meowth);

        Collections.shuffle(rocketdan);
        return rocketdan.get(0);
    }
}
