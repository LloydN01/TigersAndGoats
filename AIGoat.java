import java.util.*;
import java.lang.Thread;
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
/*
    public void aiMoveGoat(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        int a = -1;
        int b = -1;
        int randomLoc = rd.nextInt(24);
        while(bd.isGoat(randomLoc) == false){
            randomLoc = rd.nextInt(24);
        }
        for (int j = 0; j < 24; j++) {
            if (rules.isLegalMove(randomLoc, j)) {
                a = randomLoc;
                b = j;
            }
        }
        gv.aiGoatMove(a, b);
    }

    public void aiGamePlay(){
        while(rules.moveCount() <= 100){
            aiMoveGoat();
        }
    }
*/
}