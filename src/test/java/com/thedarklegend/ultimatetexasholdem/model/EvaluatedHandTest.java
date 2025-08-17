package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private EvaluatedHand generateHandFromRank(HandRank handRank)
    {
        return EvaluatedHand.create(handRank,
                                    generateRandomValidFiveCardHand(),
                                    generateValidListOfRanks());
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

    @Test
    void compareToShouldCompareDifferentHandRanks()
    {
        EvaluatedHand highCard = generateHandFromRank(HandRank.HIGH_CARD);
        EvaluatedHand pair = generateHandFromRank(HandRank.PAIR);
        EvaluatedHand twoPair = generateHandFromRank(HandRank.TWO_PAIR);
        EvaluatedHand trips = generateHandFromRank(HandRank.THREE_OF_A_KIND);
        EvaluatedHand straight = generateHandFromRank(HandRank.STRAIGHT);
        EvaluatedHand flush = generateHandFromRank(HandRank.FLUSH);
        EvaluatedHand fullHouse = generateHandFromRank(HandRank.FULL_HOUSE);
        EvaluatedHand quads = generateHandFromRank(HandRank.FOUR_OF_A_KIND);
        EvaluatedHand straightFlush = generateHandFromRank(HandRank.STRAIGHT_FLUSH);
        EvaluatedHand royalFlush = generateHandFromRank(HandRank.ROYAL_FLUSH);

        assertTrue(pair.compareTo(highCard) > 0);
        assertTrue(highCard.compareTo(pair) < 0);

        assertTrue(twoPair.compareTo(pair) > 0);
        assertTrue(pair.compareTo(twoPair) < 0);

        assertTrue(trips.compareTo(twoPair) > 0);
        assertTrue(twoPair.compareTo(trips) < 0);

        assertTrue(straight.compareTo(trips) > 0);
        assertTrue(trips.compareTo(straight) < 0);

        assertTrue(flush.compareTo(straight) > 0);
        assertTrue(straight.compareTo(flush) < 0);

        assertTrue(fullHouse.compareTo(flush) > 0);
        assertTrue(flush.compareTo(fullHouse) < 0);

        assertTrue(quads.compareTo(fullHouse) > 0);
        assertTrue(fullHouse.compareTo(quads) < 0);

        assertTrue(straightFlush.compareTo(quads) > 0);
        assertTrue(quads.compareTo(straightFlush) < 0);

        assertTrue(royalFlush.compareTo(straightFlush) > 0);
        assertTrue(straightFlush.compareTo(royalFlush) < 0);
    }


    @Test
    void compareToShouldCompareSameHandRankByOrderedRanks()
    {
        EvaluatedHand pairOfKings = EvaluatedHand.create(HandRank.PAIR,
                                                         generateRandomValidFiveCardHand(),
                                                         List.of(Rank.KING, Rank.TEN, Rank.NINE, Rank.EIGHT));

        EvaluatedHand pairOfQueens = EvaluatedHand.create(HandRank.PAIR,
                                                          generateRandomValidFiveCardHand(),
                                                          List.of(Rank.QUEEN, Rank.ACE, Rank.TEN, Rank.FIVE));

        assertTrue(pairOfKings.compareTo(pairOfQueens) > 0);
        assertTrue(pairOfQueens.compareTo(pairOfKings) < 0);
    }

    @Test
    void compareToShouldReturnZeroForIdenticalHands()
    {
        EvaluatedHand hand1 = EvaluatedHand.create(HandRank.STRAIGHT,
                                                   generateRandomValidFiveCardHand(),
                                                   List.of(Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN, Rank.NINE));

        EvaluatedHand hand2 = EvaluatedHand.create(HandRank.STRAIGHT,
                                                   generateRandomValidFiveCardHand(),
                                                   List.of(Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN, Rank.NINE));

        assertEquals(0, hand1.compareTo(hand2));
        assertEquals(0, hand2.compareTo(hand1));
    }
}
