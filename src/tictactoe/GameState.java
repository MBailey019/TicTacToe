

/**
 * A single possible board-state and its potential moves.
 * 
 * A statistical history an individual board configuration,
 * tracking the occurrence of each next move, and that move's
 * probability of leading to win.
 * 
 * <h2>CS&143 S1 | Project 2 - Tic-Tac-Toe</h2>
 *
 * @author Matt Bailey (MBaileyWebDev@gmail.com)
 * @since 04/26/15
 * @version 1.0
 */
public class GameState 
{
    
    private String configuration;
    private int[][] occurrenceCounter;
    private double[][] winProbability;

    /**
     * Instantiates a new GameState from it's board configuration.
     * @param configuration the board configuration
     */
    public GameState(String configuration) 
    {
        final int ROW_LENGTH = 6;
        final int TURN_LENGTH = 2;
        
        this.configuration = configuration;
        this.occurrenceCounter = new int[3][3];
        this.winProbability = new double[3][3];
        
        for (int rowIndex= 0; rowIndex < 3; rowIndex++)
        {
            int rowStart = rowIndex * ROW_LENGTH;
            String rowString = configuration.substring(rowStart);
            for (int columnIndex = 0; columnIndex < 3; columnIndex++)
            {
                occurrenceCounter[rowIndex][columnIndex] = 2;
                
                int turnStart = columnIndex * TURN_LENGTH;
                int turnEnd = (columnIndex + 1) * TURN_LENGTH;
                String turnString = rowString.substring(turnStart);               
                if (!(turnEnd >= rowString.length()))
                {
                    turnString = rowString.substring(turnStart, turnEnd);
                }
                
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

    /**
     * gets the GameState's configuration.
     * @return the configuration
     */
    public String getConfiguration()
    {
        return configuration;
    }

    /**
     * Sets the GameState's configuration.
     * @param configuration the new configuration
     */
    public void setConfiguration(String configuration)
    {
        this.configuration = configuration;
    }
    
    /**
     * gets the GameState's counter array.
     * @return the counter array
     */
    public int[][] getOccurrenceCounter()
    {
        return occurrenceCounter;
    }

    /**
     * Sets the GameState's counter array.
     * @param occurrenceCounter the new array
     */
    public void setOccurrenceCounter(int[][] occurrenceCounter)
    {
        this.occurrenceCounter = occurrenceCounter;
    }
    
    /**
     * Sets a specific value in the GameState's counter array.
     * @param row the row to change
     * @param column the column to change
     * @param newCount the new value
     */
    public void setOccuranceCounterAt(int row, int column, int newCount)
    {
        occurrenceCounter[row][column] = newCount;
    }

    /**
     * gets the GameState's probability array.
     * @return the array
     */
    public double[][] getWinProbability()
    {
        return winProbability;
    }

    /**
     * Sets the GameState's probability array. 
     * @param winProbability the array
     */
    public void setWinProbability(double[][] winProbability)
    {
        this.winProbability = winProbability;
    }
    
    /**
     * Sets a specific value in the probability array.
     * @param row the row to change
     * @param column the column to change
     * @param newChance the new value
     */
    public void setWinProbabilityAt(int row, int column, double newChance)
    {
        winProbability[row][column] = newChance;
    }

    /**
     * Represents the GameState as a String.
     * @return the String
     */
    @Override
    public String toString()
    {
        String gameStateString = configuration + ";";
        for (int rowIndex= 0; rowIndex < 3; rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < 3; columnIndex++)
            {
                gameStateString += "[" + rowIndex +"," + columnIndex +"]";
                double prob = winProbability[rowIndex][columnIndex];
                gameStateString += String.format("%07.5f,", prob);
                int counter = occurrenceCounter[rowIndex][columnIndex];
                gameStateString += String.format("%05d;", counter);
            }
        }
        return gameStateString;
    }
    
    /**
     * Compares this GameState to a board-configuration.
     * @param comparison the string to compare
     * @return 1 if this is greater, -1 if this is less, 0 if both are equal.
     */
    public int compareTo(String comparison)
    {
        return compareTo(configuration, comparison);
    }
    
    /**
     * Compares this GameState to another.
     * @param comparison the GameState to compare
     * @return 1 if this is greater, -1 if this is less, 0 if both are equal.
     */
    public int compareTo(GameState comparison)
    {
        return compareTo(configuration, comparison.configuration); 
    }
    
    /**
     * A utility function that handles the brunt work of all comparisons.
     * @param thisGame String representation of this game
     * @param pastGame String representation of other game
     * @return 1 if this is greater, -1 if this is less, 0 if both are equal.
     */
    private static int compareTo(String thisGame, String pastGame)
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
        
        if (firstMove < comparison)
        {
            return -1;
        }
        if (firstMove > comparison)
        {
            return 1;
        }
        
        String shorterGame = thisGame.substring(2);
        String shorterCompare = pastGame.substring(2);
        return compareTo(shorterGame, shorterCompare);
    }
    
}
