import java.util.*;
public class AIGoat {
    public static void main(String[] args) {

    }

    Random rd;
    Board bd;
    GameViewer gv;
    GameRules rules;

    public AIGoat(Board board, GameViewer viewer, GameRules rules){
        rd = new Random();
        bd = board;
        gv = viewer;
        this.rules = rules;
    }

    public void aiPlaceGoat(){
        int numGoats = rules.getNumGoats();
        for(int i = 0; i < 12 - numGoats; i++){
            int x = rd.nextInt(24);
            while (bd.isVacant(x) == false) {
                x = rd.nextInt(24);
            }
            gv.placeGoat(x);
            if (rules.isGoatsTurn() == false) {
                gv.placeTiger();
            }
        }
    }
}