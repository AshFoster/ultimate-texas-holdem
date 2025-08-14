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
}
