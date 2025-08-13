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
}
