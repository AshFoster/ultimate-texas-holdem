package com.thedarklegend.ultimatetexasholdem.logic;

import com.thedarklegend.ultimatetexasholdem.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HandEvaluatorTest
{
    @Test
    void evaluateShouldThrowException_whenHandIsNull()
    {
        Executable executable = () -> HandEvaluator.evaluate(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void evaluateShouldThrowException_whenHandDoesNotContainSevenCards()
    {
        Executable executable = () -> HandEvaluator.evaluate(List.of(new Card(Rank.ACE, Suit.SPADES)));

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfPair_whenProvidedHandContainsAPair()
    {
        List<Card> hand = List.of(new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.ACE, Suit.CLUBS),
                                  new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.TEN, Suit.SPADES),
                                  new Card(Rank.THREE, Suit.SPADES),
                                  new Card(Rank.QUEEN, Suit.DIAMONDS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS));

        assertEquals(HandRank.PAIR, HandEvaluator.evaluate(hand).getHandRank());
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithBestKickers_whenProvidedHandContainsAPair()
    {
        List<Card> hand = List.of(new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.ACE, Suit.CLUBS),
                                  new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.TEN, Suit.SPADES),
                                  new Card(Rank.THREE, Suit.SPADES),
                                  new Card(Rank.QUEEN, Suit.DIAMONDS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);

        assertTrue(evaluatedHand.getHand().contains(new Card(Rank.QUEEN, Suit.DIAMONDS)));
        assertTrue(evaluatedHand.getHand().contains(new Card(Rank.TEN, Suit.SPADES)));
        assertTrue(evaluatedHand.getHand().contains(new Card(Rank.FIVE, Suit.DIAMONDS)));
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithOrderedRanks_whenProvidedHandContainsAPair()
    {
        List<Card> hand = List.of(new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.ACE, Suit.CLUBS),
                                  new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.TEN, Suit.SPADES),
                                  new Card(Rank.THREE, Suit.SPADES),
                                  new Card(Rank.QUEEN, Suit.DIAMONDS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.ACE, Rank.QUEEN, Rank.TEN, Rank.FIVE);

        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfTwoPairAndBestKickersAndOrderedRanks_whenProvidedHandContainsTwoPair()
    {
        List<Card> hand = List.of(new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.TWO, Suit.CLUBS),
                                  new Card(Rank.THREE, Suit.SPADES),
                                  new Card(Rank.QUEEN, Suit.DIAMONDS),
                                  new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.ACE, Suit.CLUBS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.ACE, Rank.TWO, Rank.QUEEN);

        assertEquals(HandRank.TWO_PAIR, evaluatedHand.getHandRank());
        assertTrue(evaluatedHand.getHand().contains(new Card(Rank.QUEEN, Suit.DIAMONDS)));
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfThreeOfAKindAndBestKickersAndOrderedRanks_whenProvidedHandContainsThreeOfAKind()
    {
        List<Card> hand = List.of(new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.TWO, Suit.CLUBS),
                                  new Card(Rank.TWO, Suit.HEARTS),
                                  new Card(Rank.QUEEN, Suit.DIAMONDS),
                                  new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.JACK, Suit.CLUBS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.TWO, Rank.ACE, Rank.QUEEN);

        assertEquals(HandRank.THREE_OF_A_KIND, evaluatedHand.getHandRank());
        assertTrue(evaluatedHand.getHand().contains(new Card(Rank.ACE, Suit.SPADES)));
        assertTrue(evaluatedHand.getHand().contains(new Card(Rank.QUEEN, Suit.DIAMONDS)));
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfFourOfAKindAndBestKickersAndOrderedRanks_whenProvidedHandContainsFourOfAKind()
    {
        List<Card> hand = List.of(new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.TWO, Suit.CLUBS),
                                  new Card(Rank.TWO, Suit.HEARTS),
                                  new Card(Rank.TWO, Suit.DIAMONDS),
                                  new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.JACK, Suit.CLUBS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.TWO, Rank.ACE);

        assertEquals(HandRank.FOUR_OF_A_KIND, evaluatedHand.getHandRank());
        assertTrue(evaluatedHand.getHand().contains(new Card(Rank.ACE, Suit.SPADES)));
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfFullHouseAndOrderedRanks_whenProvidedHandContainsAFullHouse()
    {
        List<Card> hand = List.of(new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.TWO, Suit.CLUBS),
                                  new Card(Rank.TWO, Suit.HEARTS),
                                  new Card(Rank.ACE, Suit.DIAMONDS),
                                  new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.JACK, Suit.CLUBS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.TWO, Rank.ACE);

        assertEquals(HandRank.FULL_HOUSE, evaluatedHand.getHandRank());
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfFullHouse_whenProvidedHandContainsTwoTrips()
    {
        List<Card> hand = List.of(new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.TWO, Suit.CLUBS),
                                  new Card(Rank.TWO, Suit.HEARTS),
                                  new Card(Rank.ACE, Suit.DIAMONDS),
                                  new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.ACE, Suit.CLUBS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);

        assertEquals(HandRank.FULL_HOUSE, evaluatedHand.getHandRank());
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfFLushAndOrderedRanks_whenProvidedHandContainsAFlush()
    {
        List<Card> hand = List.of(new Card(Rank.TWO, Suit.HEARTS),
                                  new Card(Rank.THREE, Suit.HEARTS),
                                  new Card(Rank.SEVEN, Suit.HEARTS),
                                  new Card(Rank.NINE, Suit.HEARTS),
                                  new Card(Rank.TEN, Suit.HEARTS),
                                  new Card(Rank.JACK, Suit.HEARTS),
                                  new Card(Rank.ACE, Suit.HEARTS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.ACE, Rank.JACK, Rank.TEN, Rank.NINE, Rank.SEVEN);

        assertEquals(HandRank.FLUSH, evaluatedHand.getHandRank());
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfStraightAndOrderedRanks_whenProvidedHandContainsAStraight()
    {
        List<Card> hand = List.of(new Card(Rank.TWO, Suit.HEARTS),
                                  new Card(Rank.THREE, Suit.SPADES),
                                  new Card(Rank.FOUR, Suit.DIAMONDS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS),
                                  new Card(Rank.SIX, Suit.CLUBS),
                                  new Card(Rank.SEVEN, Suit.HEARTS),
                                  new Card(Rank.EIGHT, Suit.SPADES));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE, Rank.FOUR);

        assertEquals(HandRank.STRAIGHT, evaluatedHand.getHandRank());
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfStraightAndOrderedRanks_whenProvidedHandContainsAnAceLowStraight()
    {
        List<Card> hand = List.of(new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.TWO, Suit.HEARTS),
                                  new Card(Rank.THREE, Suit.SPADES),
                                  new Card(Rank.FOUR, Suit.DIAMONDS),
                                  new Card(Rank.FIVE, Suit.DIAMONDS),
                                  new Card(Rank.SEVEN, Suit.CLUBS),
                                  new Card(Rank.TEN, Suit.HEARTS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.FIVE, Rank.FOUR, Rank.THREE, Rank.TWO, Rank.ACE);

        assertEquals(HandRank.STRAIGHT, evaluatedHand.getHandRank());
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfStraightFlushAndOrderedRanks_whenProvidedHandContainsAStraightFlush()
    {
        List<Card> hand = List.of(new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.TWO, Suit.SPADES),
                                  new Card(Rank.THREE, Suit.SPADES),
                                  new Card(Rank.FOUR, Suit.SPADES),
                                  new Card(Rank.FIVE, Suit.SPADES),
                                  new Card(Rank.SEVEN, Suit.CLUBS),
                                  new Card(Rank.TEN, Suit.HEARTS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.FIVE, Rank.FOUR, Rank.THREE, Rank.TWO, Rank.ACE);

        assertEquals(HandRank.STRAIGHT_FLUSH, evaluatedHand.getHandRank());
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }

    @Test
    void evaluateShouldReturnAnEvaluatedHandWithHandRankOfRoyalFlushAndOrderedRanks_whenProvidedHandContainsARoyalFlush()
    {
        List<Card> hand = List.of(new Card(Rank.TEN, Suit.SPADES),
                                  new Card(Rank.JACK, Suit.SPADES),
                                  new Card(Rank.QUEEN, Suit.SPADES),
                                  new Card(Rank.KING, Suit.SPADES),
                                  new Card(Rank.ACE, Suit.SPADES),
                                  new Card(Rank.ACE, Suit.CLUBS),
                                  new Card(Rank.ACE, Suit.HEARTS));

        EvaluatedHand evaluatedHand = HandEvaluator.evaluate(hand);
        List<Rank> orderedRanks = List.of(Rank.ACE, Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN);

        assertEquals(HandRank.ROYAL_FLUSH, evaluatedHand.getHandRank());
        assertEquals(evaluatedHand.getOrderedRanks(), orderedRanks);
    }
}
