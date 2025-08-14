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
        List<List<Card>> pairs = getPairs(cardsByRank);

        if (!pairs.isEmpty())
        {
            HandRank handRank = HandRank.PAIR;
            List<Card> currentHand = pairs.get(0);

            if (pairs.size() >= 2)
            {
                currentHand.addAll(pairs.get(1));
                handRank = HandRank.TWO_PAIR;
            }

            List<Card> bestHand = extractBestFiveCards(allCards, currentHand);
            List<Rank> orderedRanks = extractRanks(bestHand);

            System.out.println(bestHand);
            System.out.println(orderedRanks);
            return EvaluatedHand.create(handRank,
                                        bestHand,
                                        orderedRanks);
        }

        List<List<Card>> trips = getTrips(cardsByRank);

        if (!trips.isEmpty())
        {
            HandRank handRank = HandRank.THREE_OF_A_KIND;
            List<Card> currentHand = trips.get(0);

            List<Card> bestHand = extractBestFiveCards(allCards, currentHand);
            List<Rank> orderedRanks = extractRanks(bestHand);

            System.out.println(bestHand);
            System.out.println(orderedRanks);
            return EvaluatedHand.create(handRank,
                                        bestHand,
                                        orderedRanks);
        }

        return EvaluatedHand.create(HandRank.HIGH_CARD,
                                    allCards.subList(0, 5),
                                    Collections.emptyList());
    }

    private static List<List<Card>> getPairs(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 2);
    }

    private static List<List<Card>> getTrips(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 3);
    }

    private static List<List<Card>> getGroupedRanksBySize(EnumMap<Rank, List<Card>> cardsByRank, int size)
    {
        List<List<Card>> groupedRanks = new ArrayList<>();

        for (Map.Entry<Rank, List<Card>> entry : cardsByRank.entrySet())
        {
            if (entry.getValue().size() == size)
            {
                groupedRanks.add(entry.getValue());
            }
        }

        groupedRanks.sort((a, b) -> b.get(0).getRank().getValue() - a.get(0).getRank().getValue());
        return groupedRanks;
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
