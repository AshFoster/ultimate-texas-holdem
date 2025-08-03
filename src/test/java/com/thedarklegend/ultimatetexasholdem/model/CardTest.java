package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest
{
    @Test
    void twoCardsWithSameRankAndSuitShouldBeEqual()
    {
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.ACE, Suit.SPADES);

        assertEquals(card1, card2);
    }

    @Test
    void toStringMethodShouldReturnCorrectFormat()
    {
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Card card2 = new Card(Rank.TEN, Suit.HEARTS);

        assertEquals("Ace of Spades", card1.toString());
        assertEquals("Ten of Hearts", card2.toString());
    }
}
