import java.util.*;
import java.lang.Thread;
public class AIGoat {
    public static void main(String[] args) {
    }

    private GameViewer gv;
    private Random rn;
    private Board bd;
    private GameRules rules;
    Random rd;

    public AIGoat(){
        gv = new GameViewer();
        rn = new Random();
        bd = new Board();
        rules = new GameRules();
        rd = new Random();
    }

    public void aiPlaceGoat(){
        for(int i = 0; i < 13; i++){
            int x = rn.nextInt(24);
            while(bd.isVacant(x) == false){
                x = rn.nextInt(24);
            }
            gv.goatAI(x);
            bd.setGoat(x);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        aiGamePlay();
    }

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
        while(bd.tigersWin() == false){
            aiMoveGoat();
        }
    }
}