package life.player;

import life.pokemon.Pokemon;

import java.util.ArrayList;

public class Enemy {

    private String name;
    private ArrayList<Pokemon> pokemons;

    public Enemy(String name) {
        this.name = name;
        this.pokemons = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public ArrayList<Pokemon> addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
        return pokemons;
    }
}
