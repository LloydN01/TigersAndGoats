/**
 * Implments a simple AI player to 
 * automatically contol the tiger moves
 *
 * @author Professor Ajmal Mian
 * @dated Sep 2021
 * @version 1.0
 * 
 * @Student 1 Name: Lloyd (Junghyeon) Na
 * @Student 1 Number: 23433196
 * 
 * @Student 2 Name: Owen Smith
 * @Student 2 Number: 22957291
 */
//import java.util.Random;
import java.util.*;

public class AIplayer
{
    public static void main(String[] args) {

    }
    private Random rn; // for random tiger or location selection
    private GameRules rul; // an instance of GameRules to check for legal moves
    private int[] tigerLocs; // location of tigers for convenience 
    private int ntigers; // number of tigers placed
    
    /**
     * Constructor for objects of class AIplayer.
     * Initializes instance variables.
     */
    public AIplayer()
    {
        // TODO 14
        rn = new Random();
        rul = new GameRules();
        tigerLocs = new int[3];
        ntigers = 0;
    }
    
    /**
     * EXTRA FEATURE: resets the AIplayer class.
     */
     public void resetAIclass(){
        rn = new Random();
        rul = new GameRules();
        tigerLocs = new int[3];
        ntigers = 0;
    }
    
    /**
     * Place tiger in a random vacant location on the Board
     * Update the board, the tiger count and tigerLocs.
     */
    public void placeTiger(Board bd)
    {
        //TODO 15
        //EXTRA FEATURE: The placement of the tigers are more strategic rather than random.
        int tigerLoc = -1;
        int[] scapeGoat = {-1, -1};
        List<Integer> locs = new ArrayList<>();
        for(int l = 0; l < 24; l++){
            locs.add(l);
        }
        //This randomises the starting point of the search for the strategic location so that player cannot spot a pattern.
        Collections.shuffle(locs);
        
        //Below code will check every single possible combination of tigerLoc, scapeGoat[0] and scapeGoat[1] to find the strategic location to place a tiger.
        for(int loc: locs){
            if(bd.isGoat(loc)){
                scapeGoat[0] = loc;
                for(int j = 0; j < 24; j++){
                    if(bd.isVacant(j)){
                        scapeGoat[1] = j;
                        for(int z = 0; z < 24; z++){
                            if(bd.isVacant(z)){
                                if(rul.canEatGoat(z, bd, scapeGoat) && tigerLoc == -1){
                                    //This places the tiger at a location where it can eat a goat once moveStage == true.
                                    tigerLoc = z;
                                    bd.setTiger(tigerLoc);
                                    tigerLocs[ntigers] = tigerLoc;
                                    ntigers++;
                                }
                            }
                        }
                    }
                }
             }
        }
        
        //Below code runs if the above code fails to find a strategic location for the tiger placement. Reverts back to random placement.
        if(tigerLoc == -1){
            tigerLoc = rn.nextInt(24);
            while(bd.isVacant(tigerLoc) == false){
                tigerLoc = rn.nextInt(24);
            }
            bd.setTiger(tigerLoc);
            tigerLocs[ntigers] = tigerLoc;
            ntigers++;
        }
    }

    /**
     * If possible to eat a goat, must eat and return 1
     * If cannot eat any goat, make a move and return 0
     * If cannot make any move (all Tigers blocked), return -1
     */
    public int makeAmove(Board bd)
    {
        if (eatGoat(bd))  return 1; // did eat a goat
        else if (simpleMove(bd)) return 0; // made a simple move
        else return -1; // could not move
    }
    
    /**
     * Randomly choose a tiger, move it to a legal destination and return true
     * if none of the tigers can move, return false.
     * Update the board and the tiger location (tigerLocs)
     */
    private boolean simpleMove(Board bd)
    {
        //TODO 21
        boolean move = false;
        //Using Collections.shuffle() to create a list of randomly ordered tiger locations.
        List<Integer> tigerList = Arrays.asList(tigerLocs[0], tigerLocs[1], tigerLocs[2]);
        Collections.shuffle(tigerList);
        for(int i = 0; i < 3; i++){
            int chosenTiger = tigerList.get(i);
            int numOfDestinations = rul.legalMoves[chosenTiger].length; //number of possible legal destinations can vary between 3 and 4.
            
            //EXTRA FEATURE: Instead of the tigers moving in a predictable(non-random) pattern, we randomised their movements. 
            List<Integer> destList = new ArrayList<Integer>();
            for(int x = 0; x < numOfDestinations; x++){
                destList.add(rul.legalMoves[chosenTiger][x]);
            }
            
            Collections.shuffle(destList);
            
            for(int j = 0; j < numOfDestinations; j++){
                int destination = destList.get(j);
                if(bd.isVacant(destination)){
                    move = true;
                    bd.swap(chosenTiger, destination);
                    //below code is used to acquire the index of the chosenTiger in the tigerLocs array to update the tiger location.
                    for(int z = 0; z < 3; z++){
                        if(tigerLocs[z] == chosenTiger){
                            tigerLocs[z] = destination;
                        }
                    }
                    return move;
                }
            }
        }
        
        return move;
    }
    
    /**
     * If possible, eat a goat and return true otherwise return false.
     * Update the board and the tiger location (tigerLocs)
     * 
     * Hint: use the canEatGoat method of GameRules
     */
    private boolean eatGoat(Board bd)
    {
        //TODO 22
        boolean eat = false;
        int[] scapeGoat = {-1, -1};
        //EXTRA FEATURE: If there are more than one tigers that can eat a goat at any point in time, choose at random the tiger that will eat the goat. 
        List<Integer> tigerList = Arrays.asList(tigerLocs[0], tigerLocs[1], tigerLocs[2]);
        Collections.shuffle(tigerList);
        
        for(int i = 0; i < 3; i++){
            int tigerLoc = tigerList.get(i);
            for(int j = 0; j < 24; j++){
                if(bd.isGoat(j)){
                    scapeGoat[0] = j;
                    for(int z = 0; z < 24; z++){
                        if(bd.isVacant(z)){
                            scapeGoat[1] = z;
                            if(rul.canEatGoat(tigerLoc, bd, scapeGoat)){
                                eat = true;
                                //below code is used to acquire the index of the chosenTiger in the tigerLocs array to update the tiger location.
                                for(int x = 0; x < 3; x++){
                                    if(tigerLocs[x] == tigerLoc){
                                        tigerLocs[x] = scapeGoat[1];
                                    }
                                }
                                bd.swap(tigerLoc, scapeGoat[1]);
                                bd.setVacant(scapeGoat[0]);
                                return eat;
                            }
                        }
                    }
                }
            }
        }
        return eat;
    }
}