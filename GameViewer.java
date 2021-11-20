/**
 * Controls the drawing of the board and game play. 
 * Allows the human player to make goat moves.
 * Calls AIplayer to make tiger moves.
 *
 * @Student 1 Name: Lloyd (Junghyeon) Na
 * @Student 1 Number: 23433196
 * 
 * @Student 2 Name: Owen Smith
 * @Student 2 Number: 22957291
 */

import java.awt.*;
import java.awt.event.*;

public class GameViewer implements MouseListener
{
    public static void main(String[] args) {
        GameViewer gv = new GameViewer();
    }
    // instance variables
    private int bkSize; // block size - all other measurements to be derived from bkSize
    private int brdSize; // board size
    private SimpleCanvas sc; // an object of SimpleCanvas to draw 
    private GameRules rules; // an object of GameRules
    private Board bd; // an object of Board
    private AIplayer ai; //an object of AIplayer
    private AIGoat aiGoat; //an object of AIGoat
    
    // 2D coordinates of valid locations on the board in steps of block size                                  
    public static final int[][] locs = {{1,1},                  {4,1},                  {7,1},
    
                                                {2,2},          {4,2},          {6,2},
                                                
                                                        {3,3},  {4,3},  {5,3}, 
                                                        
                                        {1,4},  {2,4},  {3,4},          {5,4},  {6,4},  {7,4},
                                        
                                                        {3,5},  {4,5},  {5,5},
                                                        
                                                {2,6},          {4,6},          {6,6},        
                                        
                                        {1,7},                  {4,7},                  {7,7} };
                                 
    // source and destination for the goat moves                             
    private int[] mov = {-1,-1}; //-1 means no selection

    /**
     * Constructor for objects of class GameViewer
     * Initializes instance variables and adds mouse listener.
     * Draws the board.
     */
    public GameViewer(int bkSize)
    {
        this.bkSize = bkSize;
        brdSize = bkSize*8;
        sc = new SimpleCanvas("Tigers and Goats", brdSize, brdSize, Color.BLUE);
        sc.addMouseListener(this);           
        rules = new GameRules();
        bd = new Board();
        ai = new AIplayer();
        aiGoat = new AIGoat(); //AI goat initialisation.          
        drawBoard();               
    }
    
    /**
     * Constructor with default block size
     */
    public GameViewer( )
    {
        this(80);
    }
    
    /**
     * Draws the boad lines and the pieces as per their locations.
     * Drawing of lines is provided, students to implement drawing 
     * of pieces and number of goats.
     */
    private void drawBoard()
    {
         sc.drawRectangle(0,0,brdSize,brdSize, new Color(0, 128, 128)); //wipe the canvas
        
        for(int i=1; i<9; i++)
        {
            //draw the red lines
            if(i<4)
                sc.drawLine(locs[i-1][0]*bkSize, locs[i-1][1]*bkSize,
                        locs[i+5][0]*bkSize, locs[i+5][1]*bkSize, Color.WHITE);
            else if(i==4)
                sc.drawLine(locs[i+5][0]*bkSize, locs[i+5][1]*bkSize,
                        locs[i+7][0]*bkSize, locs[i+7][1]*bkSize, Color.WHITE);
            else if(i==5)
                sc.drawLine(locs[i+7][0]*bkSize, locs[i+7][1]*bkSize,
                        locs[i+9][0]*bkSize, locs[i+9][1]*bkSize, Color.WHITE);              
            else
                sc.drawLine(locs[i+9][0]*bkSize, locs[i+9][1]*bkSize,
                        locs[i+15][0]*bkSize, locs[i+15][1]*bkSize, Color.WHITE);              
           
            if(i==4 || i==8) continue; //no more to draw at i=4,8
            // vertical white lines
            sc.drawLine(i*bkSize, i*bkSize,
                        i*bkSize, brdSize-i*bkSize,Color.white);            
            // horizontal white lines
            sc.drawLine(i*bkSize,         i*bkSize,
                        brdSize-i*bkSize, i*bkSize, Color.white);      
        }
        
        //EXTRA FEATURE: Draws Restart 'button'.
        sc.drawRectangle(5, 5, bkSize, bkSize/3, Color.WHITE);
        sc.drawString("Restart", bkSize / 4, bkSize / 4, Color.BLACK);

        //EXTRA FEATURE: Draws Randomise 'button'
        if(rules.isMoveStage() == false){
            sc.drawRectangle(5, 10 + bkSize / 3, bkSize, 10 + 2 * (bkSize / 3), Color.WHITE);
            sc.drawString("Random", bkSize / 4, 10 + bkSize / 3 + bkSize / 4, Color.BLACK);
        }
        
        // TODO 10 
        // Draw the goats and tigers. (Drawing the shadows is not compulsory)
        // Display the number of goats        
        for(int i = 0; i < 24; i++){
            if(bd.isVacant(i) == false){
                if(bd.isGoat(i) == true){
                    sc.drawDisc(locs[i][0] * bkSize + bkSize/14, locs[i][1] * bkSize + bkSize/14, 20, Color.BLACK);
                    sc.drawDisc(locs[i][0] * bkSize, locs[i][1] * bkSize, 20, new Color(98, 176, 245));
                } else if(bd.isGoat(i) == false){
                    sc.drawCircle(locs[i][0] * bkSize + bkSize/14, locs[i][1] * bkSize + bkSize/14, 20, Color.BLACK);
                    sc.drawCircle(locs[i][0] * bkSize, locs[i][1] * bkSize, 20, new Color(250, 71, 71));
                }
            }
        }
        
        sc.drawString("Number of Goats: " + rules.getNumGoats(), brdSize/2 - bkSize/2, brdSize/20, Color.WHITE);
        sc.drawString("Moves Made: " + rules.moveCount(), brdSize/2 - bkSize/2, brdSize/13, Color.WHITE);
    }

    /**
     * EXTRA FEATURE: Allows user to reset game and start again.
     */
        public void restart(){ 
        bd.setAllVacant();
        rules.resetRules();
        ai.resetAIclass();
        drawBoard();
    }

    /**
     * Used to get the location of the restart button for a click.
     */
    public boolean restartLocation(int x, int y){ 
        boolean hit = false;
        int rx = 5;
        int ry = 5;
        int rx2 = bkSize;
        int ry2 = bkSize/3;
        if(x >= rx && x <= rx2 && y >= ry && y <= ry2){ //Checks if click is within the x & y boundaries of the button.
            hit = true;
        }
        return hit;
    }

    /**
     * Used to get the location of the random Goat Placement button.
     */
    public boolean randomButton(int x, int y){
        boolean hit = false;
        int rx = 5;
        int ry = 10 + bkSize / 3;
        int rx2 = bkSize;
        int ry2 = 10 + 2 * (bkSize / 3);
        if (x >= rx && x <= rx2 && y >= ry && y <= ry2) { // Checks if click is within the x & y boundaries of the button.
            hit = true;
        }
        return hit;
    }
    
    /**
     * If vacant, place a goat at the user clicked location on board.
     * Update goat count in rules and draw the updated board
     */
    public void placeGoat(int loc) 
    {   
        //TODO 2
        if(rules.isMoveStage() == false){
            if(bd.isVacant(loc) && rules.getNumGoats() <= 11){
                bd.setGoat(loc);
                rules.addGoat(1);
                drawBoard();
                }
        }
    }
    
    /**
     * Calls the placeTiger method of AIplayer to place a tiger on the board.
     * Increments tiger count in rules.
     * Draws the updated board.
     */
    public void placeTiger() 
    {   
        //TODO 13
        ai.placeTiger(bd);
        rules.incrTigers();
        drawBoard();
    }
    
    /**
     * Toggles goat selection - changes the colour of selected goat.
     * Resets selection and changes the colour back when the same goat is clicked again.
     * Selects destination (if vacant) to move and calls moveGoat to make the move.
     */
    public void selectGoatMove(int loc) 
    {   
        //TODO 16
        /*Using the mov[] Array that was created as a field variable to select the goat and its destination on the board.
         * Below code is used to choose the source of the move. 
         * */
        if(mov[0] == -1 && mov[1] == -1){
            if(bd.isGoat(loc)){
                mov[0] = loc;
                sc.drawDisc(locs[loc][0] * bkSize, locs[loc][1] * bkSize, 20, Color.ORANGE);
            }
        } else if(mov[0] == loc && mov[1] == -1){
                //If you choose a goat that has been chosen already, it will revert back to its original form.
                mov[0] = -1;
                sc.drawDisc(locs[loc][0] * bkSize, locs[loc][1] * bkSize, 20, new Color(98, 176, 245));
        }
        
        /*Below if-statement will only run if the source of the move is selected but the destination is yet to be decided. 
         * It is used to choose the destination of the move.
         */
        if(mov[0] != -1 && mov[1] == -1){
            if(bd.isGoat(loc)){
                /*EXTRA FEATURE: if the user selects another goat as the destination, below code 
                 * deselects the initial source and makes the new selection the source location.
                 */
                sc.drawDisc(locs[mov[0]][0] * bkSize, locs[mov[0]][1] * bkSize, 20, new Color(98, 176, 245));
                mov[0] = loc;
                sc.drawDisc(locs[loc][0] * bkSize, locs[loc][1] * bkSize, 20, Color.ORANGE);
            } else if(bd.isVacant(loc)){
                mov[1] = loc;
                moveGoat();
            }
        }
    }
    
    /**
     * Make the user selected goat move only if legal otherwise set the destination to -1 (invalid).
     * If did make a goat move, then update board, draw the updated board, reset mov to -1,-1.
     * and call tigersMove() since after every goat move, there is a tiger move.
     */
    public void moveGoat() 
    {   
        //TODO 18
        if(rules.isLegalMove(mov[0], mov[1])){
            bd.swap(mov[0], mov[1]);
            drawBoard();    
            mov[1] = -1;
            mov[0] = -1;
            rules.moveMade();
            tigersMove();
        } else{
            sc.drawString("Illegal Move!", brdSize / 2 - bkSize / 2, (brdSize / 2) + 20, Color.WHITE);
            mov[1] = -1;
        }
    }
 
    /**
     * Call AIplayer to make its move. Update and draw the board after the move.
     * If Tigers cannot move, display "Goats Win!".
     * If goats are less than 6, display "Tigers Win!".
     * No need to terminate the game.
     */
    public void tigersMove()
    {
        //TODO 20
        int makeAmoveResult = ai.makeAmove(bd);
        
        if(makeAmoveResult == 1){
            rules.addGoat(-1); //If makeAmoveResult == 1 => Tiger has eaten a goat. Hence addGoat(-1)
        }
        
        drawBoard();
        
        if(makeAmoveResult == -1){
            sc.drawString("Goats Win!", brdSize/2 - bkSize/2, brdSize/2, Color.WHITE);
            sc.drawString(" In " +rules.moveCount() + " moves!", brdSize/2 - bkSize/2, (brdSize/2) + 20, Color.WHITE);
        }
        
        if(rules.getNumGoats() < 6){
            sc.drawString("Tigers Win!", brdSize/2 - bkSize/2, brdSize/2, Color.WHITE);
        }
        
    }

    public void illegalMove(){
        sc.drawString("Illegal Move", brdSize / 2 - bkSize / 2, (brdSize / 2) + 20, Color.WHITE);
    }
    
    /**
     * Respond to a mouse click on the board. 
     * Get a valid location nearest to the click (from GameRules). 
     * If nearest location is still far, do nothing. 
     * Otherwise, call placeGoat to place a goat at the location.
     * Call this.placeTiger when it is the tigers turn to place.
     * When the game changes to move stage, call selectGoatMove to move 
     * the user selected goat to the user selected destination.
     */
    public void mousePressed(MouseEvent e) 
    {
        //TODO 1
        int x = e.getX();
        int y = e.getY();
        if (rules.isMoveStage() == false) {
            if (rules.isGoatsTurn()) {
                if (rules.nearestLoc(x, y, bkSize) != -1) {
                    placeGoat(rules.nearestLoc(x, y, bkSize));
                    if (rules.isGoatsTurn() == false) {
                        placeTiger();
                    }
                }
            }
        } else {
            if (rules.nearestLoc(x, y, bkSize) != -1) {
                selectGoatMove(rules.nearestLoc(x, y, bkSize));
            }
        }
        
        if(restartLocation(x, y)){
            restart();
        }

        if(rules.isMoveStage() == false){
            if(randomButton(x, y)){
                if(rules.isGoatsTurn()){
                    int numGoats = rules.getNumGoats();
                    for(int i = 0; i < 12 - numGoats; i++){
                        placeGoat(aiGoat.aiPlaceGoat(bd));
                        if (rules.isGoatsTurn() == false) {
                            placeTiger();
                        }
                    }
                }
            }
        }
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
