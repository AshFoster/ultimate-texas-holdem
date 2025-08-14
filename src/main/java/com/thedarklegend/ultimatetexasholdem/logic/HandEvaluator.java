package com.thedarklegend.ultimatetexasholdem.logic;

import com.thedarklegend.ultimatetexasholdem.model.*;

import java.util.*;

public class HandEvaluator
{
    public static EvaluatedHand evaluate(List<Card> allCards)
    {
        Objects.requireNonNull(allCards, "Hand cannot be null!");

        if (allCards.size() != 7)
        {
            throw new IllegalArgumentException("evaluate() requires exactly 7 cards but got " + allCards.size());
        }

        EnumMap<Rank, List<Card>> cardsByRank = getCardsByRank(allCards);
        EnumMap<Rank, List<Card>> pairs = getPairs(cardsByRank);

        if (!pairs.isEmpty())
        {
            List<Card> currentHand = pairs.values().iterator().next();
            List<Card> bestHand = extractBestFiveCards(allCards, currentHand);
            List<Rank> orderedRanks = extractRanks(bestHand);

            return EvaluatedHand.create(HandRank.PAIR,
                                        bestHand,
                                        orderedRanks);
        }

        return EvaluatedHand.create(HandRank.HIGH_CARD,
                                    allCards.subList(0, 5),
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

    private static List<Card> extractBestFiveCards(List<Card> allCards, List<Card> currentCards)
    {
        int kickerCount = 5 - currentCards.size();
        List<Card> remainingCards = new ArrayList<>();
        List<Card> bestHand = new ArrayList<>(currentCards);
        for (Card card : allCards)
        {
            if (!currentCards.contains(card))
            {
                remainingCards.add(card);
            }
        }

        remainingCards.sort((a, b) -> b.getRank().getValue() - a.getRank().getValue());

        for (int i = 0; i < kickerCount && i < remainingCards.size(); i++)
        {
            bestHand.add(remainingCards.get(i));
        }

        return bestHand;
    }

    private static List<Rank> extractRanks(List<Card> currentCards)
    {
        return currentCards.stream()
                           .map(Card::getRank)
                           .distinct()
                           .toList();
    }
}
