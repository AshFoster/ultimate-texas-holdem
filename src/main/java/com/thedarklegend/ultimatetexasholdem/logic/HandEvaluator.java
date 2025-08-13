package com.thedarklegend.ultimatetexasholdem.logic;

import com.thedarklegend.ultimatetexasholdem.model.*;

import java.util.*;

public class HandEvaluator
{
    public static EvaluatedHand evaluate(List<Card> hand)
    {
        Objects.requireNonNull(hand, "Hand cannot be null!");

        if (hand.size() != 7)
        {
            throw new IllegalArgumentException("evaluate() requires exactly 7 cards but got " + hand.size());
        }

        EnumMap<Rank, List<Card>> cardsByRank = getCardsByRank(hand);
        EnumMap<Rank, List<Card>> pairs = getPairs(cardsByRank);

        if (!pairs.isEmpty())
        {
            List<Card> bestHand = pairs.values().iterator().next();

            List<Card> completeHand = new ArrayList<>(bestHand);
            for (Card card : hand)
            {
                if (!completeHand.contains(card)) completeHand.add(card);
                if (completeHand.size() == 5) break;
            }

            return EvaluatedHand.create(HandRank.PAIR,
                                        completeHand,
                                        Collections.emptyList());
        }

        return EvaluatedHand.create(HandRank.HIGH_CARD,
                                    hand.subList(0, 5),
                                    Collections.emptyList());
    }

    private static EnumMap<Rank, List<Card>> getPairs(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 2);
    }

    private static EnumMap<Rank, List<Card>> getGroupedRanksBySize(EnumMap<Rank, List<Card>> cardsByRank, int size)
    {
        EnumMap<Rank, List<Card>> pairs = new EnumMap<>(Rank.class);

        for (Map.Entry<Rank, List<Card>> entry : cardsByRank.entrySet())
        {
            if (entry.getValue().size() == size)
            {
                pairs.put(entry.getKey(), entry.getValue());
            }
        }

        return pairs;
    }

    private static EnumMap<Rank, List<Card>> getCardsByRank(List<Card> cards)
    {
        EnumMap<Rank, List<Card>> cardsByRank = new EnumMap<>(Rank.class);

        for (Card card : cards)
        {
            cardsByRank.computeIfAbsent(card.getRank(), rank -> new ArrayList<>()).add(card);
        }

        return cardsByRank;
    }
}
