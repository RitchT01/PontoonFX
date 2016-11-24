package sample.datamodel;

/**
 * Created by andyr on 04/08/2016.
 */
public class Diamonds extends Cards {
    private final int value2;
    private final int pontoonValue;

    public Diamonds(String rank, int value) {
        super(rank, Suit.DIAMONDS, value);
        if(rank == "Ace") {
            this.value2 = 11;
        } else {
            this.value2 = 0;
        }

        if(value > 9){
            pontoonValue = 10;
        } else {
            pontoonValue = value;
        }

    }
}
