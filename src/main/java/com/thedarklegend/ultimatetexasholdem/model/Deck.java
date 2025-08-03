package com.thedarklegend.ultimatetexasholdem.model;

import java.util.ArrayList;
import java.util.List;

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
}
