package com.thedarklegend.ultimatetexasholdem.model;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private List<Card> hand;

    public Player()
    {
        this.hand = new ArrayList<>();
    }

    public List<Card> getHand()
    {
        return new ArrayList<>(hand);
    }

    public void receiveCard(Card card)
    {
        if (hand.size() >= 2)
        {
            throw new IllegalStateException("Player already has 2 cards!");
        }
        hand.add(card);
    }

    public void resetHand()
    {
        hand.clear();
    }
}
