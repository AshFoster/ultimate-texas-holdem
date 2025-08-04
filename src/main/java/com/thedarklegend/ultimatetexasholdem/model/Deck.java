package com.thedarklegend.ultimatetexasholdem.model;

import java.util.*;

public class Deck
{
    private final List<Card> cards;

    public Deck()
    {
        cards = new ArrayList<>();
        for(Rank rank : Rank.values())
        {
            for(Suit suit : Suit.values())
            {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public List<Card> getCards()
    {
        return new ArrayList<>(cards);
    }

    public int size()
    {
        return cards.size();
    }

    public Card draw()
    {
        if (cards.isEmpty())
        {
            throw new NoSuchElementException("The deck has no more cards!");
        }

        return cards.remove(0);
    }

    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    public void shuffle(Random random)
    {
        Collections.shuffle(cards, random);
    }
}
