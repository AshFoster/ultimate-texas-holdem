package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeckTest
{
    @Test
    void deckShouldContain52Cards()
    {
        Deck deck = new Deck();

        assertEquals(52, deck.size());
    }

    @Test
    void drawShouldRemoveCardFromDeck_andReturnTheRemovedCard()
    {
        Deck deck = new Deck();
        int initialSize = deck.size();
        Card card = deck.draw();

        assertEquals(deck.size(), initialSize - 1);
        assertNotNull(card);
    }
}
