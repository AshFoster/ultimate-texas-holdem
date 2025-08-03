package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest
{
    @Test
    void deckShouldContain52Cards()
    {
        Deck deck = new Deck();

        assertEquals(52, deck.size());
    }
}
