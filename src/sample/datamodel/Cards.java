package sample.datamodel;

import static sample.datamodel.Cards.Suit.*;

/**
 * Created by andyr on 04/08/2016.
 */

public abstract class Cards {
    private final String rank;
    private final Cards.Suit suit;
    private int value;
    private int posX;
    private int posY;
    private final int squareX = 79;
    private final int squareY = 123;

    public Cards(String rank, Cards.Suit suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.posX = calculatePosX();
        this.posY = calculatePosY();
    }

    public String getRank() {
        return this.rank;
    }

    public Cards.Suit getSuit() {
        return this.suit;
    }

    public int getValue() {
        return this.value;
    }

    public int getPontoonValue(){
        int value = getValue();
        if(value > 9){
            return 10;
        }
        return value;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String toString() {
        return "\t" + this.getRank() + " of " + this.getSuit();
    }

    public static enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES;

        private Suit() {
        }
    }

    private int calculatePosX(){
        int value = this.value - 1;
        int position = value * squareX;
        return position;
    }

    private int calculatePosY(){
        int position = 0;

        if(this.suit == CLUBS){
            position = 0;
        }else if(this.suit == DIAMONDS){
            position = squareY;
        }else if(this.suit == HEARTS){
            position = 2 * squareY;
        }else if(this.suit == SPADES){
            position = 3 * squareY;
        }
        return position;
    }


}