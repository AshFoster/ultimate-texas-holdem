package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest
{
    private Deck deck;
    @BeforeEach
    void beforeEachTest()
    {
        deck = new Deck();
    }

    @Test
    void deckShouldContain52Cards()
    {
        assertEquals(52, deck.size());
    }

    @Test
    void drawShouldRemoveCardFromDeck_andReturnTheRemovedCard()
    {
        int initialSize = deck.size();
        Card card = deck.draw();

        assertEquals(deck.size(), initialSize - 1);
        assertNotNull(card);
    }

    @Test
    void drawShouldThrowAnException_whenDrawingFromAnEmptyDeck()
    {
        for(int i = 0; i < 52; i++)
        {
            deck.draw();
        }

        assertThrows(NoSuchElementException.class, deck::draw);
    }

    @Test
    void deckShouldContainUniqueCardsOnly()
    {
        Set<Card> uniqueCards = new HashSet<>();

        while (deck.size() > 0)
        {
            uniqueCards.add(deck.draw());
        }

        assertEquals(52, uniqueCards.size());
    }

    @Test
    void shuffleShouldChangeTheOrderOfTheDeck()
    {
        List<Card> before = new ArrayList<>(deck.getCards());
        deck.shuffle(new Random(13));
        List<Card> after = new ArrayList<>(deck.getCards());

        assertNotEquals(before, after);
    }

    @Test
    void resetShouldResetDeckToHave52UniqueCardsAgain()
    {
        for (int i = 0; i < 10; i++)
        {
           deck.draw();
        }

        assertNotEquals(52, deck.size());

        deck.reset();

        deckShouldContainUniqueCardsOnly();
    }
}
