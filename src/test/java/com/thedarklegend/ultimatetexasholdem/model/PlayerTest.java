package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest
{
    @Test
    void playerCanReceiveTwoCards()
    {
        Player player = new Player();
        player.receiveCard(new Card(Rank.ACE, Suit.SPADES));
        player.receiveCard(new Card(Rank.KING, Suit.SPADES));

        assertEquals(2, player.getHand().size());
    }

    @Test
    void playerCannotReceiveMoreThanTwoCards()
    {
        Player player = new Player();
        player.receiveCard(new Card(Rank.ACE, Suit.SPADES));
        player.receiveCard(new Card(Rank.KING, Suit.SPADES));

        assertThrows(IllegalStateException.class, () -> player.receiveCard(new Card(Rank.QUEEN, Suit.SPADES)));
    }

    @Test
    void resetHandClearsThePlayersHand()
    {
        Player player = new Player();
        player.receiveCard(new Card(Rank.ACE, Suit.SPADES));
        player.receiveCard(new Card(Rank.KING, Suit.SPADES));
        player.resetHand();

        assertEquals(0, player.getHand().size());
    }
}
