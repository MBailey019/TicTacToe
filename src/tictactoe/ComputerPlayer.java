package tictactoe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A pseudo-intelligent player controlled by the computer.
 * 
 * Uses statistical data to determine the move with the
 * best probability of leading to a win.
 * 
 * <h2>CS&143 S1 | Project 2 - Tic-Tac-Toe</h2>
 *
 * @author Matt Bailey (MBaileyWebDev@gmail.com)
 * @since 04/26/15
 * @version 1.5
 */
public class ComputerPlayer extends Player
{

    /**
     * Used by ComputerPlayer for interacting with the game.
     * 
     * A specialized array that can be converted to and from 
     * multiple different forms for easy comparison and 
     * interaction with other objects.
     * 
     * <h2>CS&143 S1 | Project 2 - Tic-Tac-Toe</h2>
     * 
     * @author Matt Bailey (MBaileyWebDev@gmail.com)
     * @since 04/10/15
     * @version 1.0
     */
    public class Move
    {
        private final int ROW;
        private final int COLUMN;
        
        /**
         * Instantiates a Move from a String.
         * @param inputString the String
         * @throws NumberFormatException 
         */
        public Move(String inputString) throws NumberFormatException
        {
            int componentLength = inputString.length()/2;
            String firstHalf = inputString.substring(0, componentLength);
            String otherHalf = inputString.substring(componentLength);
            ROW = Integer.parseInt(firstHalf);
            COLUMN = Integer.parseInt(otherHalf);
        }
        
        /**
         * Instantiates a Move from a pair of integer values.
         * @param aY the y-coordinate
         * @param anX the x-coordinate
         */
        public Move(int aY, int anX)
        {
            ROW = aY;
            COLUMN = anX;
        }

        /**
         * gets the Move's row.
         * @return the row
         */
        public int getRow()
        {
            return ROW;
        }

        /**
         * gets the Move's column.
         * @return the column
         */
        public int getColumn()
        {
            return COLUMN;
        }
        
        /**
         * Converts the Move to an array of integers.
         * @return the array
         */
        public int[] toArray()
        {
            return new int[]{ROW,COLUMN};
        }
        
        /**
         * Converts the Move to a String
         * @return the String
         */
        @Override
        public String toString()
        {
            return ROW + "" + COLUMN;
        }
        
        /**
         * Determines whether one Move is equal to another.
         * @param otherMove the comparison Move
         * @return boolean
         */
        public boolean equals(Move otherMove)
        {
            if (ROW == otherMove.getRow() &&
                COLUMN == otherMove.getColumn())
            {
                return true;
            }
            return false;
        }
    }
    
    private ArrayList<GameState> brain;
    private String[] gameBoards;
    private Move[] gameMoves;
    private int movesCount = 0;
    
    /**
     * Instantiates a ComputerPlayer with default values.
     */
    public ComputerPlayer()
    {
        super();
    }
    
    /**
     * Instantiates a ComputerPlayer with a custom name and an empty brain.
     * @param aName the name
     */
    public ComputerPlayer(String aName)
    {
        super(aName);
        brain = new ArrayList<>();
        gameBoards = new String[9];
        gameMoves = new Move[9];
    }
    
    /**
     * Instantiates a ComputerPlayer with a custom name and populates the brain.  
     * @param aName the name
     * @param brainFile the address of the brain-file
     * @throws FileNotFoundException 
     */
    public ComputerPlayer(String aName, String brainFile) throws FileNotFoundException
    {
        super(aName);
        brain = fileToBrain(brainFile);
        gameBoards = new String[9];
        gameMoves = new Move[9];
    }

    /**
     * Gets the ComputerPlayer's brain.
     * @return the brain
     */
    public ArrayList<GameState> getBrain() {
        return brain;
    }

    /**
     * Sets the ComputerPlayer's brain.
     * @param brain new brain
     */
    public void setBrain(ArrayList<GameState> brain) {
        this.brain = brain;
    }

    /**
     * Gets the ComputerPlayer's gameBoards array.
     * @return the array
     */
    public String[] getGameBoards() {
        return gameBoards;
    }

    /**
     * Sets the ComputerPlayer's gameBoards array.
     * @param gameBoards new gameBoards
     */
    public void setGameBoards(String[] gameBoards) {
        this.gameBoards = gameBoards;
    }

    /**
     * Gets the ComputerPlayer's gameMoves array.
     * @return the array
     */
    public Move[] getGameMoves() {
        return gameMoves;
    }

    /**
     * Sets the ComputerPlayer's gameMoves array.
     * @param gameMoves new gameMoves
     */
    public void setGameMoves(Move[] gameMoves) {
        this.gameMoves = gameMoves;
    }

    /**
     * Gets the ComputerPlayer's moves count
     * @return the count
     */
    public int getMovesCount() {
        return movesCount;
    }

    /**
     * Sets the ComputerPlayer's moves count
     * @param movesCount new count
     */
    public void setMovesCount(int movesCount) {
        this.movesCount = movesCount;
    }
    

    /**
     * Provides a move likely to win in a game of tic-tac-toe. 
     * @param max the maximum accepted value
     * @return a move
     */
    @Override
    public int[] provideInput(int max)
    {
        boolean found = false;
        int highestIndex = 0;
        String currentBoard = getGame().toString();
        if (brain.size() > 0)
        {
            while (brain.size() > highestIndex &&
                    brain.get(highestIndex).compareTo(currentBoard) >= 0)
            {
                if (brain.get(highestIndex).compareTo(currentBoard) == 0)
                {
                    found = true;
                }
                else
                {
                }
                highestIndex++;
            }
        }
        int accessPoint = highestIndex - 1;
        int insertionPoint = highestIndex;
        if (!found)
        {
            accessPoint++;
            GameState newState = new GameState(currentBoard);
            brain.add(insertionPoint, newState);
        }
        GameState thisGame = brain.get(accessPoint);
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int totalWeight = 0;
        Move chosenMove = null;
        for (int rowIndex= 0; rowIndex < 3; rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < 3; columnIndex++)
            {
                double probability = thisGame.getWinProbability()[rowIndex][columnIndex];
                int weight = (int) Math.floor(probability * 10);
                Move move = new Move(rowIndex, columnIndex);
                for ( int count = 0; count < weight; count++)
                {
                    totalWeight++;
                    possibleMoves.add(move);
                }
                if (totalWeight == 0 && probability > 0.0)
                {
                    chosenMove = move;
                }
            }
        }
        int movesLength = possibleMoves.size();
        if (movesLength > 0){
            Random randomGenerator = new Random();
            int moveChoice = randomGenerator.nextInt(movesLength);
            chosenMove = possibleMoves.get(moveChoice);
        }
        
        gameBoards[movesCount] = currentBoard;
        gameMoves[movesCount] = chosenMove;
        movesCount++;
        
        return chosenMove.toArray();
    }

    /**
     * updates the players brain after a game is finished.
     * @param state the player's state at the end of a game
     */
    @Override
    public void postGame(Game.PlayerState state)
    {
        
        int score = 0;
        if (state == Game.PlayerState.WINNER)
        {
            score = 1;
        }
        for (int turnCount = 0; turnCount < gameBoards.length; turnCount++)
        {
            if(gameBoards[turnCount] != null)
            {
                String gameBoard = gameBoards[turnCount];
                int brainIndex = 0;
                while (brain.size() > brainIndex &&
                        brain.get(brainIndex).compareTo(gameBoard) >= 0)
                {
                    if (brain.get(brainIndex).compareTo(gameBoard) == 0)
                    {
                        Move gameMove = gameMoves[turnCount];
                        int row = gameMove.getRow();
                        int column = gameMove.getColumn();

                        GameState pastGame = brain.get(brainIndex);
                        double oldProbability = pastGame.getWinProbability()[row][column];
                        int count = pastGame.getOccurrenceCounter()[row][column];
                        double newProbability = (oldProbability * count) + score;
                        newProbability /= (count + 1);
                        pastGame.getWinProbability()[row][column] = newProbability;
                        pastGame.getOccurrenceCounter()[row][column]++;
                    }
                    brainIndex++;
                } 
            }
        }
        Arrays.fill(gameBoards, null);
        Arrays.fill(gameMoves, null);
        movesCount = 0;
    }
    
    /**
     * Writes the brain array to a text file.
     */
    public void brainToFile(){
        int gameSize = getGame().BOARD_SIZE;
        String outFilename = getName()+"_brain-"+gameSize+"X"+gameSize;
        try
        {
            PrintWriter brainWriter = new PrintWriter(outFilename+".txt");
            try
            {
                for (GameState previousGame: brain){
                    //brainWriter.print(brain.indexOf(previousGame));
                    brainWriter.println(previousGame.toString());
                }
            }
            finally
            {
                brainWriter.close();
            }
        }
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(ComputerPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Reads a text file into the brain array.
     * @param filename the file to write to
     * @return brrraaains!!!
     * @throws FileNotFoundException 
     */
    private ArrayList<GameState> fileToBrain(String filename)
            throws FileNotFoundException
    {
        File newBrainFile = new File(filename);
        ArrayList<GameState> freshBrain;
        try (Scanner brainScanner = new Scanner(newBrainFile)) {
            freshBrain = new ArrayList<>();
            while(brainScanner.hasNextLine()){
                String gameMemory = brainScanner.nextLine();
                Scanner memoryScanner = new Scanner(gameMemory);
                memoryScanner.useDelimiter(";");
                String configuration = memoryScanner.next();
                GameState game = new GameState(configuration);
                while (memoryScanner.hasNext())
                {
                    String move = memoryScanner.next();
                    int row = Integer.valueOf(move.substring(1,2));
                    int column = Integer.valueOf(move.substring(3,4));
                    double prob = Double.parseDouble(move.substring(5,12));
                    int count = Integer.parseInt(move.substring(13));
                    
                    game.setWinProbabilityAt(row, column, prob);
                    game.setOccuranceCounterAt(row, row, count);
                }
                freshBrain.add(game);
            }
        } 
        return freshBrain;
    }
}    
