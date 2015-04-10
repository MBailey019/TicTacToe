/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactoe;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author Matt
 */
public class Player {
    private final String NAME;
    private int score;
    private Game game;
    
    public Player(String aName){
        NAME = aName;
        score = 0;
    }

    public String getName() {
        return NAME;
    }

    public int getScore() {
        return score;
    }
    
    public void setGame(Game aGame) {
        game = aGame;
    }
    
    public Game getGame(){
        return game;
    }
    
    public int[] provideInput(int max){
        int[] move = {-1,-1};
            String inputPrompt = NAME;
            inputPrompt += ": Please input 2 numbers between 0 and " + max;
            inputPrompt += ", separated by a space.";
            while (move[0] == -1 || move[1] == -1){
                System.out.println(inputPrompt);
                Scanner in = new Scanner(System.in);
                try{
                    for (int index = 0; index <= 1; index++){
                        int potentialMove = in.nextInt();
                        if( potentialMove <= max &&
                        potentialMove >= 0){
                            System.out.println(index+": "+potentialMove);
                            move[index] = potentialMove;
                        }
                        else{
                            System.out.println("i dunno man: "+ potentialMove);
                        }
                    }
                }catch (InputMismatchException exception){
                    System.out.println("That's not an integer and you know it!");
                    move[0] = -1;
                    move[1] = -1;
                }
            }    
           // System.out.println(move);
        return move;
    }
    
    public void postGame(Game.PlayerState state){
        if (state == Game.PlayerState.WINNER)score++;
    }
}
