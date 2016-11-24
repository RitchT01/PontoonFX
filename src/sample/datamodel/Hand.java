package sample.datamodel;

import java.util.stream.IntStream;

/**
 * Created by andyr on 05/08/2016.
 */
public class Hand {
    private Cards card1;
    private Cards card2;
    private Cards card3;
    private Cards card4;
    private Cards card5;
    private Cards[] cards = new Cards[5];

    public Hand(Cards card1, Cards card2) {
        this.card1 = card1;
        this.card2 = card2;


        this.cards[0] = card1;
        this.cards[1] = card2;
    }

    public int getHandTotals(){
        int[] sums = saveHandTotals();
        int total = 0;
        for(int sum : sums){
            if(sum != 0 && sum > total && sum <= 21){
                total = sum;
            }
            if(total == 0){
                total = -1;
            }
        }
        return total;
    }

    private int[] saveHandTotals(){
        int sum = 0;
        int aces = 0;
        int[] sums = new int[5];
        int noOfCardsInHand = 0;

        for(Cards card : cards){
            if(card != null){
                noOfCardsInHand += 1;
            }
        }

        int[] total = new int[noOfCardsInHand];

        for(int i=0; i<total.length; i++){
            total[i] = cards[i].getPontoonValue();
        }

        for(int value : total){
            if(value ==1){
                aces+=1;
            }
        }

        sum = IntStream.of(total).sum();
        sums[0] = sum;

        for(int i=1; i<=aces; i++){
            if(aces == 0){
                break;
            }
            sums[i] = sum + (i*10);
        }
        return sums;
    }


    public int addCardToHand(Cards card) {
        if (this.card3 == null) {
            this.card3 = card;
            this.cards[2] = card;
            return 3;
        } else if (this.card4 == null) {
            this.card4 = card;
            this.cards[3] = card;
            return 4;
        } else if (this.card5 == null) {
            this.card5 = card;
            this.cards[4] = card;
            return 5;
        }
        return -1;
    }


    @Override
    public String toString() {
        return this.card1 + "\n" +
                this.card2 + "\n" +
                this.card3 + "\n" +
                this.card4 + "\n" +
                this.card5;
    }

    public Cards getCard1() {
        return card1;
    }

    public Cards getCard2() {
        return card2;
    }

    public Cards getCard3() {
        return card3;
    }

    public Cards getCard4() {
        return card4;
    }

    public Cards getCard5() {
        return card5;
    }

    public Cards[] getCards() {
        return cards;
    }
}
