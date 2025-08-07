package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EvaluatedHandTest
{
    private List<Card> generateRandomValidFiveCardHand()
    {
        Deck deck = new Deck();
        deck.shuffle();

        return List.of(deck.draw(), deck.draw(), deck.draw(), deck.draw(), deck.draw());
    }

    private List<Rank> generateValidListOfRanks()
    {
        return List.of(Rank.KING, Rank.QUEEN, Rank.FIVE);
    }

    @Test
    void createShouldThrowException_whenHandRankIsNull()
    {
        List<Card> hand = generateRandomValidFiveCardHand();
        List<Rank> orderedRanks = generateValidListOfRanks();
        Executable executable = () -> EvaluatedHand.create(null, hand, orderedRanks);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void createShouldThrowException_whenHandIsNull()
    {
        HandRank handRank = HandRank.FLUSH;
        List<Rank> orderedRanks = generateValidListOfRanks();
        Executable executable = () -> EvaluatedHand.create(handRank, null, orderedRanks);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void createShouldThrowException_whenHandDoesNotContainFiveCards()
    {
        HandRank handRank = HandRank.FLUSH;
        List<Card> hand = List.of(new Card(Rank.ACE, Suit.SPADES));
        List<Rank> orderedRanks = generateValidListOfRanks();
        Executable executable = () -> EvaluatedHand.create(handRank, hand, orderedRanks);

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void createShouldThrowException_whenOrderedRanksIsNull()
    {
        HandRank handRank = HandRank.FLUSH;
        List<Card> hand = generateRandomValidFiveCardHand();
        Executable executable = () -> EvaluatedHand.create(handRank, hand, null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void createShouldReturnValidEvaluatedHandObject_whenArgumentsAreValid()
    {
        HandRank handRank = HandRank.FLUSH;
        List<Card> hand = generateRandomValidFiveCardHand();
        List<Rank> orderedRanks = generateValidListOfRanks();
        EvaluatedHand evaluatedHand = EvaluatedHand.create(handRank, hand, orderedRanks);

        assertEquals(handRank, evaluatedHand.getHandRank());
        assertEquals(hand, evaluatedHand.getHand());
        assertEquals(orderedRanks, evaluatedHand.getOrderedRanks());
    }
}
