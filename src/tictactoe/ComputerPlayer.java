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
    private ArrayList<String> brain;
    private String lastMove;
    private String lastBoard;

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
        
        public Move(int anX, int aY)
        {
            ROW = anX;
            COLUMN = aY;
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
        if (boardString.equals("-1-1 0 1-1 1-1 1 0"))
        {
            System.out.println("THIS: " + boardString); //DELETE
        }   
        ArrayList<Move> possibleMoves = new ArrayList<>();
        
        for (int row =0; row < currentBoard.length; row++)
        {
            for (int column =0; column < currentBoard[row].length; column++)
            {
                if (currentBoard[row][column] == 0)
                {
                    Move possibleMove = new Move(row, column);
                    possibleMoves.add(possibleMove);
                }
            }
        }
        
        int gameStringSize = 2 * (getGame().BOARD_SIZE * getGame().BOARD_SIZE);
        for (String previousGame: brain)
        {
            String previousBoard = previousGame.substring(0, gameStringSize);
            if (previousBoard.equals(boardString))
            {
//                System.out.println("PREV: " + previousBoard); //DELETE
                Scanner previousGameScanner = new Scanner(previousGame);
                previousGameScanner.useDelimiter(";");
                previousGameScanner.next();
                while (previousGameScanner.hasNext())
                {
                    String badString = previousGameScanner.next();
                    Move badMove = new Move(badString);
                    if (boardString.equals("-1-1 0 1-1 1-1 1 0"))
                    {
                        System.out.println("BAD: "+badMove.getRow()+", "+badMove.getColumn());
                    }
//                    Iterator<Move> moveIterator = possibleMoves.iterator();
//                    while (moveIterator.hasNext()){
//                        Move move = moveIterator.next();
//                        if (badMove.equals(move))
//                        {
//                            possibleMoves.remove(move);
//                        }
//                    }
                    int toRemove = -1;
                    for (Move move: possibleMoves)
                    {
                        if (boardString.equals("-1-1 0 1-1 1-1 1 0"))
                        {
                            System.out.println(" -p: "+move.getRow()+", "+move.getColumn()+", Match? " + move.equals(badMove));
                        }      
                        if (move.equals(badMove))
                        {
                            toRemove = possibleMoves.indexOf(move);
                            if (boardString.equals("-1-1 0 1-1 1-1 1 0")){
                                System.out.println("TR: "+toRemove);
                                System.out.println("size: "+possibleMoves.size());
                            }
                            
                        }
                    }
                    if (toRemove != -1 && possibleMoves.size() > 1)
                    {
                        Move removed = possibleMoves.remove(toRemove);
                        //System.out.println("REMOVED:" +removed.toString());
                    }
                }
                if (boardString.equals("-1-1 0 1-1 1-1 1 0"))
                {
                    System.out.println("P: "+possibleMoves);
                }    
            }
        }
        
//        System.out.println(lastMove);
        Random randomGenerator = new Random();
        int movesLength = possibleMoves.size();
        int moveChoice = randomGenerator.nextInt(movesLength);
        Move chosenMove = possibleMoves.get(moveChoice);
        lastMove = chosenMove.toString();
        if (possibleMoves.size() == 1)
        {
            lastMove = null;
        }
        lastBoard = boardString;
        if (boardString.equals("-1-1 0 1-1 1-1 1 0"))
        {
            System.out.println("FINALMOVE: "+lastMove);
        }
        return chosenMove.toArray();
    }

    @Override
    public void postGame(Game.PlayerState state)
    {
        if (state == Game.PlayerState.LOSER)
        {
           // String currentBoard = getGame().toString();
           // System.out.println("currentBoard: "+currentBoard);
            for (int i=0; i<brain.size(); i++)
            {
                String pastGame = brain.get(i);
                //if (i<1) System.out.println(pastGame);
                Scanner brainScanner = new Scanner(pastGame).useDelimiter(";");
                String pastBoard = brainScanner.next();
                //if (i<1) System.out.println(pastBoard);
                //if (i<1) System.out.println(currentBoard);
                if (lastBoard.equals(pastBoard))
                {
                    //System.out.println("fucking MAtch!!===");
                    //System.out.println("pastBoard");
                    if (lastMove != null)
                    {
                        String updatedGame = pastGame+lastMove+";";
                        brain.set(i, updatedGame);
                    }
                    brainScanner.close();
                    return;
                }
            }
            brain.add(lastBoard+";"+lastMove+";");
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
