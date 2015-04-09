package tictactoe;

/**
 * Tests the Records helper-class.
 *
 * <h5>Class</h5>
 * CS&143 S1
 *
 * <h5>Program/Assignment Title</h5>
 * Project1 File I/O
 *
 * <h5>Date</h5>
 * 1/13/2015
 *
 * @author Matt Bailey    
 */ 
public class Game {
    
    private Player[] players;
    private int[][] board;
    private int currentPlayer;
    private int winner = 0;
    
    public final int BOARD_SIZE = 3;
    public enum PlayerState { WINNER, LOSER;}
    
    public static Game setupGame(Player playerOne, Player playerTwo){
        Game newGame = new Game(playerOne, playerTwo);
        playerOne.setGame(newGame);
        playerTwo.setGame(newGame);
        return newGame;
    }
    
    public Game(Player player, Player otherPlayer){
        players = new Player[]{player, null, otherPlayer};
        board = new int[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = 1;
        clearBoard();
    }
    
    public void clearBoard(){
        for (int i =0; i<board.length; i++){
            for (int j =0; j<board[i].length; j++){
                board[i][j] = 0;
            }
        }
    }

    public int getWinner() {
        return winner;
    }
    
    public void placeMark(int x, int y){
        board[x][y] = currentPlayer;
        System.out.println(players[currentPlayer+1].getName()+" moved: "+x+", "+y);
        checkForWin();
    }
    
    public void switchPlayer(){
        currentPlayer *= -1;
    }
    
    public void currentPlayerMark(){
        boolean valid = false;
        int[] move;
        while (!valid){
            move = players[currentPlayer+1].provideInput(board.length-1);
            if (board[move[0]][move[1]]==0 && move!=null){
                valid = true;
                placeMark(move[0],move[1]);
            }
        }
        
    }
    
    public void checkForWin(){
        int totalDia1 = 0;
        int totalDia2 = 0;
        for (int i =0; i<board.length; i++){
            int totalHoriz = 0;
            int totalVerti = 0;
            totalDia1 += board[i][i];
            totalDia2 += board[i][board.length-1-i];
            for (int j =0; j<board[i].length; j++){
                totalHoriz += board[i][j];
                totalVerti += board[j][i];
            }
            if (totalHoriz == 3*currentPlayer || 
                totalVerti == 3*currentPlayer){announceWinner();}
        }
        if (totalDia1 == 3*currentPlayer ||
            totalDia2 == 3*currentPlayer) {announceWinner();}
        printBoard();
        switchPlayer();
    }
    
    public void printBoard(){
        for (int i =0; i<board.length; i++){
            for (int j =0; j<board[i].length; j++){
                System.out.print(board[i][j]+"|");
            }
            System.out.print("\n------\n");
        }   
    }
    
    @Override
    public String toString(){
        StringBuilder boardString = new StringBuilder("");
        for (int i =0; i<board.length; i++){
            for (int j =0; j<board[i].length; j++){
                boardString.append(String.valueOf(board[i][j]));
            }
        }   
        boardString.append(";");
        return boardString.toString();
    }
    
    public void announceWinner(){
        winner = currentPlayer;
        System.out.println(players[currentPlayer+1].getName()+" wins");
        players[currentPlayer+1].postGame(PlayerState.WINNER);
        players[-currentPlayer+1].postGame(PlayerState.LOSER);
    }
}
