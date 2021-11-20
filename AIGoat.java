import java.util.*;
public class AIGoat {
    public static void main(String[] args) {

    }

    Random rd;

    public AIGoat(){
        rd = new Random();
    }

    public int aiPlaceGoat(Board bd){
        int x = rd.nextInt(24);
        while (bd.isVacant(x) == false) {
            x = rd.nextInt(24);
        }
        return x;
    }

}