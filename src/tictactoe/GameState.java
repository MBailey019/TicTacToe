/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactoe;

/**
 *
 * @author Matt
 */
public class GameState {
    
    private String configuration;
    private int[][] occuranceCounter;
    private double[][] winProbability;

    public static void main(String[] args)
    {
        GameState game  = new GameState(" 0 0 0 0 0 0 0 0 0");
        GameState game2 = new GameState(" 1 1 1 1 1 1 1 1 1");
        System.out.println(game.toString());
        System.out.println(game2.toString());
        System.out.println(game.compareTo(game2));
    }
    
    public GameState(String configuration) {
        final int ROW_LENGTH = 6;
        final int TURN_LENGTH = 2;
        
        this.configuration = configuration;
        this.occuranceCounter = new int[3][3];
        this.winProbability = new double[3][3];
        for (int rowIndex= 0; rowIndex < 3; rowIndex++)
        {
            int rowStart = rowIndex * ROW_LENGTH;
            //int rowEnd = (rowIndex + 1) * ROW_LENGTH; //last one is going to be out of bounds!!
            String rowString = configuration.substring(rowStart);
         //   System.out.println(rowString);
            for (int columnIndex = 0; columnIndex < 3; columnIndex++)
            {
                occuranceCounter[rowIndex][columnIndex] = 2;
                
                int turnStart = columnIndex * TURN_LENGTH;
                int turnEnd = (columnIndex + 1) * TURN_LENGTH;
                String turnString = rowString.substring(turnStart);
                if (!(turnEnd >= rowString.length())){
                    turnString = rowString.substring(turnStart, turnEnd);
                }
             //   System.out.println("COL INDEX: " +columnIndex+" - "+turnString);
                int intValue = Integer.valueOf(turnString.trim());
                if (intValue == 0)
                {
                    winProbability[rowIndex][columnIndex] = 0.5;
                }
                else
                {
                    winProbability[rowIndex][columnIndex] = 0;
                } 
            }
        }   
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public int[][] getOccuranceCounter() {
        return occuranceCounter;
    }

    public void setOccuranceCounter(int[][] occuranceCounter) {
        this.occuranceCounter = occuranceCounter;
    }
    
    public void setOccuranceCounterAt(int row, int col, int count) {
        occuranceCounter[row][col] = count;
    }

    public double[][] getWinProbability() {
        return winProbability;
    }

    public void setWinProbability(double[][] winProbability) {
        this.winProbability = winProbability;
    }
    public void setWinProbabilityAt(int row, int col, double prob) {
        winProbability[row][col] = prob;
    }

    @Override
    public String toString() {
        String gameStateString = configuration + ";";
        for (int rowIndex= 0; rowIndex < 3; rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < 3; columnIndex++)
            {
                gameStateString += "[" + rowIndex +"," + columnIndex +"]";
                double prob = winProbability[rowIndex][columnIndex];
                gameStateString += String.format("%07.5f,", prob);
                int counter = occuranceCounter[rowIndex][columnIndex];
                gameStateString += String.format("%05d;", counter);
            }
        }
        return gameStateString;
    }
    
    public int compareTo(String comparison)
    {
       // System.out.println("THIS: "+configuration);
        //System.out.println("THAT: "+comparison);
        return compareTo(configuration, comparison);
    }
    
    public int compareTo(GameState comparison)
    {
        return compareTo(configuration, comparison.configuration); 
    }
    
    public int compareTo(String thisGame, String pastGame)
    {
        if (thisGame.length() == 0 ||
                pastGame.length() == 0)
        {
            return 0;
        }
        String squareString = thisGame.substring(0, 2).trim();
        String pastSquareString = pastGame.substring(0, 2).trim();
        int firstMove = Integer.valueOf(squareString);
        int comparison = Integer.valueOf(pastSquareString);
        //System.out.println("comparing " + firstMove +" to "+ comparison);
        
        if (firstMove < comparison)
        {
          //  System.out.println("THIS < THAT");
            return -1;
        }
        if (firstMove > comparison)
        {
          //  System.out.println("THIS > THAT");
            return 1;
        }
        
        String shorterGame = thisGame.substring(2);
        String shorterCompare = pastGame.substring(2);
        return compareTo(shorterGame, shorterCompare);
    }
    
}
