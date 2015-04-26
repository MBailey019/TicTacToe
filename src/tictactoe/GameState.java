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
        GameState game = new GameState(" 0 1 2 3 4 5 6 7 8");
        System.out.println(game.toString());
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
                occuranceCounter[rowIndex][columnIndex] = 0;
                
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

    public double[][] getWinProbability() {
        return winProbability;
    }

    public void setWinProbability(double[][] winProbability) {
        this.winProbability = winProbability;
    }

    @Override
    public String toString() {
        String gameStateString = configuration + ";";
        for (int rowIndex= 0; rowIndex < 3; rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < 3; columnIndex++)
            {
                gameStateString += "[" + rowIndex +"," + columnIndex +"]";
                gameStateString += " " + winProbability[rowIndex][columnIndex] + ",";
                gameStateString += " " + occuranceCounter[rowIndex][columnIndex] + ";";
            }
        }
        return gameStateString;
    }
    
    
}
