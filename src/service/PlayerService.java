package service;

import life.pokemon.Pokemon;

public interface PlayerService {
    void newGame();
    void loadGame();
    void myPage();
    void shopping();
    boolean catchMon();
    void captureFight(Pokemon pokemon);
    void fight();
    void relax();
    void saveGame();
}
