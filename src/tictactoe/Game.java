package tictactoe;

/**
 * The hub of a game of tic-tac-toe. 
 * 
 * sets up the board for each round, gets and process inputs and informs play-state
 *
 * <h5>CS&143 S1</h5>
 * 
 * <h5>Project 2 - Tic-Tac-Toe</h5>
 * 
 * <h5>4/10/2015</h5>
 *
 * @author Matt Bailey    
 */ 
public class Game {
    
    private Player[] players;
    private int[][] board;
    private int currentPlayer;
    private int winner = 0;
    
    public final int BOARD_SIZE;
    public enum PlayerState { WINNER, LOSER, NEITHER;}
    
    /**
     * Creates a new game and notifies players
     * 
     * @param size the square size of the board
     * @param playerOne a player
     * @param playerTwo a player
     * @return Game
     */
    public static Game setupGame(int size, Player playerOne, Player playerTwo)
    {
        Game newGame = new Game(size, playerOne, playerTwo);
        playerOne.setGame(newGame);
        playerTwo.setGame(newGame);
        return newGame;
    }
    
    /**
     * Constructs a new Game object
     * 
     * @param size the square size of the board (1-9)
     * @param player a player
     * @param otherPlayer a player
     * 
     * @pre size must be between 0 and 10
     */
    private Game(int size, Player player, Player otherPlayer)
    {
        BOARD_SIZE = size;
        players = new Player[]{player, null, otherPlayer};
        board = new int[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = 1;
        clearBoard();
    }
    
    /**
     * Clears all spaces on the play-board
     */
    public void clearBoard()
    {
        for (int i =0; i<board.length; i++)
        {
            for (int j =0; j<board[i].length; j++)
            {
                board[i][j] = 0;
            }
        }
        winner = 0;
    }
    
    /**
     * Retrieves the winner of the current game.
     * @return an integer representing the winner
     * -1 stands for player 1, 1 for player 2, -2 for a draw and 0 for and ongoing game
     */
    public int getWinner() 
    {
        return winner;
    }
    
    /**
     * retrieves the current board as a 2-dimensional array
     * @return the board
     */
    public int[][] getBoard()
    {
        return board;
    }
    
    /**
     * Retrieves a string representation of the current board
     * @return the board as a string
     */
    @Override
    public String toString()
    {
        StringBuilder boardString = new StringBuilder("");
        
        for (int row =0; row<board.length; row++)
        {
            for (int column =0; column<board[row].length; column++)
            {
                int placeValue = board[row][column];
                String placeString = String.format("%2d", placeValue);
                boardString.append(placeString);
            }
        }   
        
        return boardString.toString();
    }

    /**
     * Prompts the current player for input.
     */
    public void currentPlayerMark()
    {
        boolean valid = false;
        int[] move;
        //System.out.println(players[currentPlayer+1].getName() + " going.");
        while (!valid)
        {
            move = players[currentPlayer+1].provideInput(board.length-1);
            if (board[move[0]][move[1]]==0 && move!=null)
            {
                valid = true;
                placeMark(move[0],move[1]);
            }
        }
    }
    
    /**
     * places the current player's mark on the board at the specified position
     * @param row the row to place the mark in
     * @param column the column to place the mark in
     */
    public void placeMark(int row, int column)
    {
        board[row][column] = currentPlayer;
        checkForWin();
    }
    
    /**
     * determines if the current game has a winner
     */
    public void checkForWin()
    {
        int totalDia1 = 0;
        int totalDia2 = 0;
        boolean isDraw = true;
        
        for (int row =0; row<board.length; row++)
        {
            int totalHoriz = 0;
            int totalVerti = 0;
            totalDia1 += board[row][row];
            totalDia2 += board[row][board.length-1-row];
            for (int column = 0; column<board[row].length; column++)
            {
                totalHoriz += board[row][column];
                totalVerti += board[column][row];
                if (board[row][column] == 0)
                {
                    isDraw = false;
                }
            }
            if (totalHoriz == 3*currentPlayer || 
                totalVerti == 3*currentPlayer)
            {
                announceWinner();
            }
        }
        
        if (totalDia1 == 3*currentPlayer ||
            totalDia2 == 3*currentPlayer) 
        {
            announceWinner();
        }
        if (isDraw)
        {
            announceDraw();
        }
        
        printBoard();
        switchPlayer();
    }
    
    /**
     * prints the board to the console
     */
    public void printBoard()
    {
        if (!(players[0] instanceof ComputerPlayer &&
            players[2] instanceof ComputerPlayer))
        {
        for (int row = 0; row < board.length; row++)
        {
            for (int column = 0; column < board[row].length; column++)
            {
                String markToDisplay = " ";
                if (board[row][column] == -1){ markToDisplay = "X";}
                if (board[row][column] == 1){markToDisplay = "O";}
                if (column < board.length-1){markToDisplay += "|";}
                System.out.print(markToDisplay);
            }
            if (row < board.length-1){ System.out.print("\n-----\n");}
        }   
        System.out.print("\n");
        System.out.println(toString());
        }
    }
    
    /**
     * switches current player back and forth
     */
    public void switchPlayer()
    {
        currentPlayer *= -1;
    }
    
    /**
     * sets the game's winner & notifies players of game outcome
     */
    public void announceWinner()
    {
        winner = currentPlayer;
        
        //System.out.println(players[currentPlayer+1].getName()+" wins");
        players[currentPlayer+1].postGame(PlayerState.WINNER);
        players[-currentPlayer+1].postGame(PlayerState.LOSER);
    }
    
    /**
     * notifies players of a draw and resets board
     */
    public void announceDraw()
    {
        winner = -2;
        
        //System.out.println("you're both losers.");
        players[0].postGame(PlayerState.NEITHER);
        players[2].postGame(PlayerState.NEITHER);
    }
    
    /**
     * notifies players that the game has ended
     */
    public void endGame(){
        for( Player player: players ){
            if (player instanceof ComputerPlayer){
                ComputerPlayer computerPlayer = (ComputerPlayer)player;
                computerPlayer.brainToFile();
            }
        }
    }
}    
    
