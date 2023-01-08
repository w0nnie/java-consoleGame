package inventory;

import item.Item;
import life.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private static Inventory inventoryInstance;
    private List<Item> items;
    private List<Pokemon> pokemons;

    public Inventory(){
        this.items = new ArrayList<>();
        this.pokemons = new ArrayList<>();
    }

    public static Inventory getInstance(){
        if(inventoryInstance == null){
            inventoryInstance = new Inventory();
        }
        return inventoryInstance;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(Item item) {
        items.add(item);
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(Pokemon pokemon) {
        pokemons.add(pokemon);
    }
}
