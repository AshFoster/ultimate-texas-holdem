package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void drawShouldThrowAnException_whenDrawingFromAnEmptyDeck()
    {
        Deck deck = new Deck();

        for(int i = 0; i < 52; i++)
        {
            deck.draw();
        }

        assertThrows(NoSuchElementException.class, deck::draw);
    }

    @Test
    void deckShouldContainUniqueCardsOnly()
    {
        Deck deck = new Deck();
        Set<Card> uniqueCards = new HashSet<>();

        while (deck.size() > 0)
        {
            uniqueCards.add(deck.draw());
        }

        assertEquals(52, uniqueCards.size());
    }
}
