package tictactoe;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * A basic player . 
 * 
 * Uses the console I/O to provide input when prompted
 *
 * <h2>CS&143 S1 | Project 2 - Tic-Tac-Toe</h2>
 *
 * @author Matt Bailey (MBaileyWebDev@gmail.com)
 * @since 04/10/15
 * @version 1.0
 */ 
public class Player 
{
    private final String NAME;
    private int score;
    private Game game;
    
    /**
     * Instantiates a new computer player with a default name.
     */
    public Player()
    {
        NAME = "Juan Carlos De Los Parlotes De Amadeo";
        score = 0;
    }
    
    /**
     * Instantiates a Player with a custom name.
     * @param aName the player's name
     */
    public Player(String aName)
    {
        NAME = aName;
        score = 0;
    }

    /**
     * Gets the Player's name.
     * @return the name
     */
    public String getName()
    {
        return NAME;
    }

    /**
     * Gets the players current score.
     * @return the score
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * Sets the player's score.
     * @param newScore the number to set the score to
     */
    public void setScore(int newScore)
    {
        this.score = newScore;
    }
    
    /**
     * Gets the game in which the player is participating.
     * @return a Game object
     */
    public Game getGame()
    {
        return game;
    }
    
    /**
     * Sets the player's current game.
     * @param aGame a new Game object 
     */
    public void setGame(Game aGame)
    {
        game = aGame;
    }
    
    /**
     * Prompts the user for input through the console.
     * @param max the maximum accepted value
     * @return the player's move
     */
    public int[] provideInput(int max)
    {
        int[] move = {-1,-1};
        String inputPrompt = NAME;
        inputPrompt += ": Please input 2 numbers between 0 and " + max;
        inputPrompt += ", separated by a space.\n";
        inputPrompt += " [ROW] [COLUMN]";

        while (move[0] == -1 || move[1] == -1)
        {
            System.out.println(inputPrompt);
            Scanner in = new Scanner(System.in);
            try
            {
                for (int index = 0; index <= 1; index++)
                {
                    int potentialMove = in.nextInt();
                    if( potentialMove <= max &&
                    potentialMove >= 0)
                    {
                        move[index] = potentialMove;
                    }
                    else
                    {
                        System.out.println("invalid value: "+ potentialMove);
                    }
                }
            }
            catch (InputMismatchException exception)
            {
                System.out.println("That's not an integer and you know it!");
                move[0] = -1;
                move[1] = -1;
            }
        }    
        return move;
    }
    
    /**
     * increments the player's score.
     * @param state the player's status at the end of a game
     */
    public void postGame(Game.PlayerState state)
    {
        if (state == Game.PlayerState.WINNER)
        {
            score++;
        }
    }
}
