/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactoe;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Matt
 */
public class Player {
    private String name;
    private int score;
    private Game game;
    
    public Player(String aName){
        name = aName;
        score = 0;
    }

    public String getName() {
        return name;
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
       // while (move[1] == -1){
            //System.out.println( game );
            System.out.println(name+": Please input 2 numbers between 0 and "+max);
            Scanner in = new Scanner(System.in);
            //String input = in.nextLine();
            //Scanner inputScan = new Scanner(input);
            move[0] = in.nextInt();
            move[1] = in.nextInt();
            System.out.println(move[0]+", "+move[1]);
            //inputScan.close();
            //in.close();
       // }
        return move;
    }
    
    public void postGame(Game.PlayerState state){
        score++;
    }
}
