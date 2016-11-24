package sample.datamodel;

import java.util.*;

/**
 * Created by andyr on 04/08/2016.
 */
public class Deck {
    private final String name;
    private final Set<Cards> deck;
    public static Map<Integer, Cards>  deckMap = new HashMap<>();

    public Deck(String name) {
        this.name = name;
        this.deck = new HashSet();
        Clubs card = new Clubs("Ace", 1);
        this.deck.add(card);
        card = new Clubs("Two", 2);
        this.deck.add(card);
        card = new Clubs("Three", 3);
        this.deck.add(card);
        card = new Clubs("Four", 4);
        this.deck.add(card);
        card = new Clubs("Five", 5);
        this.deck.add(card);
        card = new Clubs("Six", 6);
        this.deck.add(card);
        card = new Clubs("Seven", 7);
        this.deck.add(card);
        card = new Clubs("Eight", 8);
        this.deck.add(card);
        card = new Clubs("Nine", 9);
        this.deck.add(card);
        card = new Clubs("Ten", 10);
        this.deck.add(card);
        card = new Clubs("Jack", 11);
        this.deck.add(card);
        card = new Clubs("Queen", 12);
        this.deck.add(card);
        card = new Clubs("King", 13);
        this.deck.add(card);
        Diamonds card1 = new Diamonds("Ace", 1);
        this.deck.add(card1);
        card1 = new Diamonds("Two", 2);
        this.deck.add(card1);
        card1 = new Diamonds("Three", 3);
        this.deck.add(card1);
        card1 = new Diamonds("Four", 4);
        this.deck.add(card1);
        card1 = new Diamonds("Five", 5);
        this.deck.add(card1);
        card1 = new Diamonds("Six", 6);
        this.deck.add(card1);
        card1 = new Diamonds("Seven", 7);
        this.deck.add(card1);
        card1 = new Diamonds("Eight", 8);
        this.deck.add(card1);
        card1 = new Diamonds("Nine", 9);
        this.deck.add(card1);
        card1 = new Diamonds("Ten", 10);
        this.deck.add(card1);
        card1 = new Diamonds("Jack", 11);
        this.deck.add(card1);
        card1 = new Diamonds("Queen", 12);
        this.deck.add(card1);
        card1 = new Diamonds("King", 13);
        this.deck.add(card1);
        Hearts card2 = new Hearts("Ace", 1);
        this.deck.add(card2);
        card2 = new Hearts("Two", 2);
        this.deck.add(card2);
        card2 = new Hearts("Three", 3);
        this.deck.add(card2);
        card2 = new Hearts("Four", 4);
        this.deck.add(card2);
        card2 = new Hearts("Five", 5);
        this.deck.add(card2);
        card2 = new Hearts("Six", 6);
        this.deck.add(card2);
        card2 = new Hearts("Seven", 7);
        this.deck.add(card2);
        card2 = new Hearts("Eight", 8);
        this.deck.add(card2);
        card2 = new Hearts("Nine", 9);
        this.deck.add(card2);
        card2 = new Hearts("Ten", 10);
        this.deck.add(card2);
        card2 = new Hearts("Jack", 11);
        this.deck.add(card2);
        card2 = new Hearts("Queen", 12);
        this.deck.add(card2);
        card2 = new Hearts("King", 13);
        this.deck.add(card2);
        Spades card3 = new Spades("Ace", 1);
        this.deck.add(card3);
        card3 = new Spades("Two", 2);
        this.deck.add(card3);
        card3 = new Spades("Three", 3);
        this.deck.add(card3);
        card3 = new Spades("Four", 4);
        this.deck.add(card3);
        card3 = new Spades("Five", 5);
        this.deck.add(card3);
        card3 = new Spades("Six", 6);
        this.deck.add(card3);
        card3 = new Spades("Seven", 7);
        this.deck.add(card3);
        card3 = new Spades("Eight", 8);
        this.deck.add(card3);
        card3 = new Spades("Nine", 9);
        this.deck.add(card3);
        card3 = new Spades("Ten", 10);
        this.deck.add(card3);
        card3 = new Spades("Jack", 11);
        this.deck.add(card3);
        card3 = new Spades("Queen", 12);
        this.deck.add(card3);
        card3 = new Spades("King", 13);
        this.deck.add(card3);

        createDeckMap();
    }

    public String getName() {
        return this.name;
    }

    public Set<Cards> getDeck() {
        return this.deck;
    }

    public String toString() {
        Iterator var1 = this.deck.iterator();

        while(var1.hasNext()) {
            Cards deckCard = (Cards)var1.next();
            System.out.println(deckCard.getRank() + " of " + deckCard.getSuit() + " posX: " + deckCard.getPosX() + " posY: " + deckCard.getPosY());
        }

        return "";
    }

    public static Map<Integer, Cards> getDeckMap() {
        return deckMap;
    }

    private Map createDeckMap(){
        int i = 1;
        for(Cards card1 : deck){
            deckMap.put(i, card1);
            i++;
        }
        return deckMap;
    }
}
