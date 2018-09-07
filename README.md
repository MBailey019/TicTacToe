# TicTacToe
Project 2 CS142

## Description

This was a project for Algorithms & Data Structures in Java class.

The goal was to write a program to play tic tac toe against a human player and learn from it's mistakes.

This solution uses a "Brain" -- a historical record of win-percentages associated with each possible move -- to choose the move with the highest probability of winning. The two brains included are the result of simulating thousands of matches by pairing two computer players against each other. Theyre probably identical, but I have yet to check.

The extensions of the `ComputerPlayer` class (`DumbComputerPlayer`, `HalfDumbComputerPlayer`) are simply there to fulfill project requirements.

## Future Work
  - Brain structure could be made more efficient by storing only possible game states and labelling winning-positions or losing-positions
  - Brain could use base-3 to store game-states, making updating process faster. 
  
