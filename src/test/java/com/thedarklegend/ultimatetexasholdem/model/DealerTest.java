package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealerTest
{
    @Test
    void dealerCanReceiveAndResetHandLikePlayer()
    {
        Dealer dealer = new Dealer();
        dealer.receiveCard(new Card(Rank.SEVEN, Suit.SPADES));
        dealer.receiveCard(new Card(Rank.TWO, Suit.CLUBS));

        assertEquals(2, dealer.getHand().size());

        dealer.resetHand();
        assertEquals(0, dealer.getHand().size());
    }
}
