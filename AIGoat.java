import java.util.*;
import java.lang.Thread;
public class AIGoat {
    public static void main(String[] args) {
        
    }

    private GameViewer gv;
    private Random rn;
    private Board bd;
    private GameRules rules;

    public AIGoat(){
        gv = new GameViewer();
        rn = new Random();
        bd = new Board();
        rules = new GameRules();
    }

    public void aiPlaceGoat(){
        while(rules.isMoveStage() == false){
            int x = rn.nextInt(24);
            while(bd.isVacant(x) == false){
                x = rn.nextInt(24);
            }
            gv.goatAI(x);
            bd.setGoat(x);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}
