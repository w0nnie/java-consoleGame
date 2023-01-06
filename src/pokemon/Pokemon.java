package pokemon;

import java.util.ArrayList;
import java.util.List;

public class Pokemon implements Skill{
    private String name;
    private int hp;

    public Pokemon(String name, int hp){
        this.name = name;
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public String toString() {
        return "pokemon.Pokemon{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                '}';
    }

    @Override
    public void skill1(Pokemon pokemon) {

    }

    @Override
    public void skill2(Pokemon pokemon) {

    }

    @Override
    public void skill3(Pokemon pokemon) {

    }
}