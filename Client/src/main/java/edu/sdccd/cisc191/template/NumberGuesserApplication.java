package edu.sdccd.cisc191.template;

import edu.sdccd.cisc191.template.models.NumberGuessingGame;


//Sean Standen - Peer Review
//Abstracted main program method to its own class to clean up the code.
public class NumberGuesserApplication {
    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        game.startGame();
    }
}
