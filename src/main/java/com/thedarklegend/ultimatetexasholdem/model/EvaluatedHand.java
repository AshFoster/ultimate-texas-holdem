package com.thedarklegend.ultimatetexasholdem.model;

import java.util.List;
import java.util.Objects;

public class EvaluatedHand
{
    private final HandRank handRank;
    private final List<Card> hand;
    private final List<Rank> orderedRanks;

    private EvaluatedHand(HandRank handRank, List<Card> hand, List<Rank> orderedRanks)
    {
        this.handRank = handRank;
        this.hand = hand;
        this.orderedRanks = orderedRanks;
    }

    public static EvaluatedHand create(HandRank handRank, List<Card> hand, List<Rank> orderedRanks)
    {
        Objects.requireNonNull(handRank, "HandRank cannot be null!");
        Objects.requireNonNull(hand, "Hand cannot be null!");
        Objects.requireNonNull(orderedRanks, "Comparable ranks cannot be null!");

        if (hand.size() != 5)
        {
            throw new IllegalArgumentException("Hand size must be 5!");
        }

        return new EvaluatedHand(handRank, List.copyOf(hand), List.copyOf(orderedRanks));
    }

    public HandRank getHandRank()
    {
        return handRank;
    }

    public List<Card> getHand()
    {
        return hand;
    }

    public List<Rank> getOrderedRanks()
    {
        return orderedRanks;
    }
}
