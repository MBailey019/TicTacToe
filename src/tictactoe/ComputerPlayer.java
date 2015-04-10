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
public class ComputerPlayer extends Player{
    private ArrayList<String> brain;
    private String lastMove;

    public ComputerPlayer(String aName){
        super(aName);
        brain = new ArrayList<>();
    }

    @Override
    public int[] provideInput(int max){
        System.out.println(lastMove);
        Random rand = new Random();
        int[] move = new int[]{rand.nextInt(max+1), rand.nextInt(max+1)};
        lastMove = String.valueOf(move[0])+String.valueOf(move[1])+";";
        return move;
    }

    @Override
    public void postGame(Game.PlayerState state){
        if (state == Game.PlayerState.LOSER){
            String currentBoard = getGame().toString();
            for (int i=0; i<brain.size(); i++){
                String pastGame = brain.get(i);
                Scanner brainScanner = new Scanner(pastGame).useDelimiter(";");
                String pastBoard = brainScanner.next();
                if (currentBoard.equals(pastBoard)){
                    String updatedGame = pastGame+lastMove;
                    brain.set(i, updatedGame);
                    brainScanner.close();
                    return;
                }
            }
            brain.add(currentBoard+lastMove);
        }
        System.out.println(brain.toString());
    }
}    
