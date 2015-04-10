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
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matt
 */
public class ComputerPlayer extends Player{
    private ArrayList<String> brain;
    private String lastMove;

    public ComputerPlayer(String aName){
        super(aName);
        brain = new ArrayList<>();
    }
    
    public ComputerPlayer(String aName, String brainFile) throws FileNotFoundException{
        super(aName);
        brain = fileToBrain(brainFile);
    }

    @Override
    public int[] provideInput(int max)
    {
        Game currentGame = getGame();
        int[][] currentBoard = currentGame.getBoard();
        String boardString = currentGame.toString();
        //System.out.println("THIS: " + boardString);
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        
        for (int row =0; row < currentBoard.length; row++)
        {
            for (int column =0; column < currentBoard[row].length; column++)
            {
                if (currentBoard[row][column] == 0)
                {
                    int[] possibleMove = {row, column};
                    possibleMoves.add(possibleMove);
                }
            }
        }
        
        int gameStringSize = 2 * (getGame().BOARD_SIZE * getGame().BOARD_SIZE);
        for (String previousGame: brain)
        {
            String previousBoard = previousGame.substring(0, gameStringSize);
            //System.out.println("PREV: " + previousBoard); 
            if (previousBoard.equals(boardString))
            {
                Scanner previousGameScanner = new Scanner(previousGame);
                previousGameScanner.useDelimiter(";");
                previousGameScanner.next();
                while (previousGameScanner.hasNext())
                {
                    String badMove = previousGameScanner.next();
                    int moveComponentLength = badMove.length() / 2;
                    int[] badMoveArray = new int[2];
                    String row = badMove.substring(0, moveComponentLength+1);
                    String column = badMove.substring( moveComponentLength );
                    badMoveArray[0] = Integer.parseInt(row);
                    badMoveArray[1] = Integer.parseInt(column);
                    int possibleMoveIndex = possibleMoves.indexOf(badMove);
                    System.out.println("BAD: "+badMoveArray[0]+", "+badMoveArray[1]); //DELETE
                    System.out.println("POS: "+possibleMoves.toString()); //DELETE
                    if (possibleMoveIndex >= 0)
                    {
                        possibleMoves.remove(possibleMoveIndex);
                    }
                }
            }
        }
        
//        System.out.println(lastMove);
        Random randomGenerator = new Random();
        int movesLength = possibleMoves.size();
        int moveChoice = randomGenerator.nextInt(movesLength);
        int[] move = possibleMoves.get(moveChoice);
        lastMove = String.format("%1d%1d;", move[0], move[1]);
        return move;
    }

    @Override
    public void postGame(Game.PlayerState state)
    {
        if (state == Game.PlayerState.LOSER)
        {
            String currentBoard = getGame().toString();
           // System.out.println("currentBoard: "+currentBoard);
            for (int i=0; i<brain.size(); i++)
            {
                String pastGame = brain.get(i);
                //if (i<1) System.out.println(pastGame);
                Scanner brainScanner = new Scanner(pastGame).useDelimiter(";");
                String pastBoard = brainScanner.next();
                //if (i<1) System.out.println(pastBoard);
                //if (i<1) System.out.println(currentBoard);
                if (currentBoard.equals(pastBoard))
                {
                    //System.out.println("fucking MAtch!!===");
                    //System.out.println("pastBoard");
                    String updatedGame = pastGame+lastMove;
                    brain.set(i, updatedGame);
                    brainScanner.close();
                    return;
                }
            }
            brain.add(currentBoard+";"+lastMove);
        }
        //System.out.println(brain.toString());
    }
    
    public void brainToFile(){
        int gameSize = getGame().BOARD_SIZE;
        String outFilename = getName()+"_brain-"+gameSize+"X"+gameSize;
        try
        {
            PrintWriter brainWriter = new PrintWriter(outFilename+".txt");
            try
            {
                for (String previousGame: brain){
                    //brainWriter.print(brain.indexOf(previousGame));
                    brainWriter.println(previousGame);
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
    
    private ArrayList<String> fileToBrain(String filename) throws FileNotFoundException{
        File newBrainFile = new File(filename);
        ArrayList<String> freshBrain;
        try (Scanner brainScanner = new Scanner(newBrainFile)) {
            freshBrain = new ArrayList<>();
            while(brainScanner.hasNextLine()){
                String gameMemory = brainScanner.nextLine();
                freshBrain.add(gameMemory);
            }
        } 
        return freshBrain;
    }
}    
