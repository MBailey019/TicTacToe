/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 *
 * @author Matt
 */
public class ComputerPlayer extends Player
{

    public class Move
    {
        private final int ROW;
        private final int COLUMN;
        
        public Move(String inputString) throws NumberFormatException{
            int componentLength = inputString.length()/2;
            String firstHalf = inputString.substring(0, componentLength);
            String otherHalf = inputString.substring(componentLength);
            ROW = Integer.parseInt(firstHalf);
            COLUMN = Integer.parseInt(otherHalf);
        }
        
        public Move(int aY, int anX)
        {
            ROW = aY;
            COLUMN = anX;
        }

        public int getRow() {
            return ROW;
        }

        public int getColumn() {
            return COLUMN;
        }
        
        public int[] toArray(){
            return new int[]{ROW,COLUMN};
        }
        
        @Override
        public String toString(){
            return ROW + "" + COLUMN;
        }
        
        public boolean equals(Move otherMove){
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
    
    public ComputerPlayer(String aName){
        super(aName);
        brain = new ArrayList<>();
        gameBoards = new String[9];
        gameMoves = new Move[9];
    }
    
    public ComputerPlayer(String aName, String brainFile) throws FileNotFoundException{
        // @TODO fix brainToFile()
        super(aName);
        brain = fileToBrain(brainFile);
        gameBoards = new String[9];
        gameMoves = new Move[9];
    }

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
                //System.out.println("Searching brain @ "+highestIndex);
                if (brain.get(highestIndex).compareTo(currentBoard) == 0)
                {
                    found = true;
                    //System.out.println("Found match @ "+highestIndex);
                }
                else
                {
                    //System.out.println("No match @ "+highestIndex); 
                }
                highestIndex++;
            }
        }
        //System.out.println("Highest index checked: "+highestIndex);
        int accessPoint = highestIndex - 1;
        int insertionPoint = highestIndex;
        if (!found)
        {
            accessPoint++;
            
            //System.out.println("Inserting new mem @ "+ insertionPoint); 
            GameState newState = new GameState(currentBoard);
            brain.add(insertionPoint, newState);
            //System.out.println(brain.get(insertionPoint).compareTo(currentBoard));
        }
        //System.out.println("Accessing Brain @: "+ accessPoint);
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

    @Override
    public void postGame(Game.PlayerState state)
    {
        
        int score = 0;
        if (state == Game.PlayerState.WINNER)
        {
            score = 1;
        }
        //System.out.println("begin POST_GAME()");
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
                        int count = pastGame.getOccuranceCounter()[row][column];
                        double newProbability = (oldProbability * count) + score;
                        newProbability /= (count + 1);
                        pastGame.getWinProbability()[row][column] = newProbability;
                        pastGame.getOccuranceCounter()[row][column]++;
                    }
                    brainIndex++;
                } 
            }
        }
        Arrays.fill(gameBoards, null);
        Arrays.fill(gameMoves, null);
        movesCount = 0;
    }
    
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
    
    private ArrayList<GameState> fileToBrain(String filename) throws FileNotFoundException
    {
        System.out.println("Loading Brain: " + filename);
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
