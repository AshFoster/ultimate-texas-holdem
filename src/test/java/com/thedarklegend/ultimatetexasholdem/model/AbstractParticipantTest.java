package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractParticipantTest
{
    protected Participant participant;

    protected abstract Participant createParticipant();

    @BeforeEach
    void beforeEachTest()
    {
        participant = createParticipant();
    }

    @Test
    void playerCanReceiveTwoCards()
    {
        participant.receiveCard(new Card(Rank.ACE, Suit.SPADES));
        participant.receiveCard(new Card(Rank.KING, Suit.SPADES));

        assertEquals(2, participant.getHand().size());
    }

    @Test
    void playerCannotReceiveMoreThanTwoCards()
    {
        participant.receiveCard(new Card(Rank.ACE, Suit.SPADES));
        participant.receiveCard(new Card(Rank.KING, Suit.SPADES));

        assertThrows(IllegalStateException.class, () -> participant.receiveCard(new Card(Rank.QUEEN, Suit.SPADES)));
    }

    @Test
    void resetHandClearsThePlayersHand()
    {
        participant.receiveCard(new Card(Rank.ACE, Suit.SPADES));
        participant.receiveCard(new Card(Rank.KING, Suit.SPADES));
        participant.resetHand();

        assertEquals(0, participant.getHand().size());
    }
}
