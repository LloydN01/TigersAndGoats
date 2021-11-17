/**
 * Maintains and updates the status of the board
 * i.e. the locations of goats and tigers
 *
 * @Student 1 Name: Lloyd (Junghyeon) Na
 * @Student 1 Number: 23433196
 * 
 * @Student 2 Name: Owen Smith
 * @Student 2 Number: 22957291
 */
public class Board
{
    public static void main(String[] args) {
        
    }
    // An enumated type for the three possibilities
    private enum Piece {GOAT, TIGER, VACANT};
    Piece[] board;

    /**
     * Constructor for objects of class Board.
     * Initializes the board VACANT.
     */
    public Board()
    {
        // TODO 3
        board = new Piece[24];
        for(int i = 0; i < board.length; i++){
            board[i] = Piece.VACANT;
        }
    }

            
    /**
     * Checks if the location a is VACANT on the board
     */
    public boolean isVacant(int a)
    {
        //TODO 4
        boolean vacant = false;
        if(board[a] == Piece.VACANT){
            vacant = true;
        }
        return vacant;
    }
    
    /**
     * Sets the location a on the board to VACANT
     */
    public void setVacant(int a)
    {
        //TODO 5
        board[a] = Piece.VACANT;
    }
    
    /**
     * EXTRA FEATURE: Sets all pieces on the board to be vacant => Resets game.
     */
    public void setAllVacant(){ 
        for(int i = 0; i < board.length; i++){
            board[i] = Piece.VACANT;
        }
    }
    
    /**
     * Checks if the location a on the board is a GOAT
     */
    public boolean isGoat(int a)
    {
        //TODO 6
        boolean goat = false;
        if(board[a] == Piece.GOAT){
            goat = true;
        }
        return goat;
    }
    
    /**
     * Sets the location a on the board to GOAT
     */
    public void setGoat(int a)
    {
        //TODO 7
        board[a] = Piece.GOAT;
    }
    
    /**
     * Sets the location a on the board to TIGER
     */
    public void setTiger(int a)
    {
        //TODO 8
        board[a] = Piece.TIGER;
    }
    
    /**
     * Moves a piece by swaping the contents of a and b
     */
    public void swap(int a, int b)
    {
        //TODO 9
        Piece aPiece = board[a];
        Piece bPiece = board[b];
        
        board[a] = bPiece;
        board[b] = aPiece;
    }
}
