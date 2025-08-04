package com.thedarklegend.ultimatetexasholdem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
}
