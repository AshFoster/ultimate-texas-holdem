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
        List<List<Card>> trips = getTrips(cardsByRank);

        if ((!pairs.isEmpty() && !trips.isEmpty()) || trips.size() > 1)
        {
            return generateBestFullHouseHand(trips, pairs);
        }

        EnumMap<Suit, List<Card>> cardsBySuit = getCardsBySuit(allCards);

        List<Card> flushes = getAllFlushCards(cardsBySuit);
        List<Card> bestStraight = getBestFiveCardStraight(allCards);

        if (!flushes.isEmpty() && !bestStraight.isEmpty())
        {
            List<Card> bestStraightFlush = getBestFiveCardStraight(flushes);

            if (!bestStraight.isEmpty())
            {
                return generateBestStraightFlushHand(bestStraightFlush);
            }
        }

        if (!bestStraight.isEmpty())
        {
            return generateBestStraightHand(bestStraight);
        }

        if (!flushes.isEmpty())
        {
            return generateBestFlushHand(flushes);
        }

        if (pairs.size() == 1)
        {
            return generateBestPairHand(allCards, pairs);
        }

        if (pairs.size() >= 2)
        {
            return generateBestTwoPairHand(allCards, pairs);
        }

        if (!trips.isEmpty())
        {
            return generateBestThreeOfAKindHand(allCards, trips);
        }

        List<List<Card>> quads = getQuads(cardsByRank);

        if (!quads.isEmpty())
        {
            return generateBestFourOfAKindHand(allCards, quads);
        }

        return generateBestHighCardHand(allCards);
    }

    private static EvaluatedHand generateBestPairHand(List<Card> allCards,
                                                      List<List<Card>> pairs)
    {
        HandRank handRank = HandRank.PAIR;

        List<Card> currentHand = pairs.get(0);
        List<Card> bestHand = extractBestFiveCards(allCards, currentHand);
        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);
        return EvaluatedHand.create(handRank,
                                    bestHand,
                                    orderedRanks);
    }

    private static EvaluatedHand generateBestTwoPairHand(List<Card> allCards,
                                                         List<List<Card>> pairs)
    {
        HandRank handRank = HandRank.TWO_PAIR;

        List<Card> currentHand = pairs.get(0);
        currentHand.addAll(pairs.get(1));

        List<Card> bestHand = extractBestFiveCards(allCards, currentHand);
        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);
        return EvaluatedHand.create(handRank,
                                    bestHand,
                                    orderedRanks);
    }

    private static EvaluatedHand generateBestThreeOfAKindHand(List<Card> allCards,
                                                              List<List<Card>> trips)
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

    private static EvaluatedHand generateBestFourOfAKindHand(List<Card> allCards,
                                                             List<List<Card>> quads)
    {
        HandRank handRank = HandRank.FOUR_OF_A_KIND;

        List<Card> currentHand = quads.get(0);
        List<Card> bestHand = extractBestFiveCards(allCards, currentHand);
        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);
        return EvaluatedHand.create(handRank,
                                    bestHand,
                                    orderedRanks);
    }

    private static EvaluatedHand generateBestFullHouseHand(List<List<Card>> trips,
                                                           List<List<Card>> pairs)
    {
        HandRank handRank = HandRank.FULL_HOUSE;

        List<Card> bestHand = trips.get(0);

        if (trips.size() > 1)
        {
            bestHand.addAll(trips.get(1).subList(0,2));
        }
        else
        {
            bestHand.addAll(pairs.get(0));
        }

        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);
        return EvaluatedHand.create(handRank,
                                    bestHand,
                                    orderedRanks);
    }

    private static EvaluatedHand generateBestFlushHand(List<Card> flushes)
    {
        HandRank handRank = HandRank.FLUSH;

        List<Card> bestHand = flushes.subList(0, 5);
        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);
        return EvaluatedHand.create(handRank,
                                    bestHand,
                                    orderedRanks);
    }

    private static EvaluatedHand generateBestStraightHand(List<Card> straight)
    {
        HandRank handRank = HandRank.STRAIGHT;

        List<Card> bestHand = new ArrayList<>(straight);
        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);
        return EvaluatedHand.create(handRank,
                                    bestHand,
                                    orderedRanks);
    }

    private static EvaluatedHand generateBestStraightFlushHand(List<Card> straightFlush)
    {
        HandRank handRank =
                straightFlush.get(0).getRank() == Rank.ACE
                        ? HandRank.ROYAL_FLUSH
                        : HandRank.STRAIGHT_FLUSH;

        List<Card> bestHand = new ArrayList<>(straightFlush);
        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);
        return EvaluatedHand.create(handRank,
                                    bestHand,
                                    orderedRanks);
    }

    private static EvaluatedHand generateBestHighCardHand(List<Card> allCards)
    {
        HandRank handRank = HandRank.HIGH_CARD;

        List<Card> bestHand = extractBestFiveCards(allCards, Collections.emptyList());
        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);
        return EvaluatedHand.create(handRank,
                                    bestHand,
                                    orderedRanks);
    }

    private static List<List<Card>> getPairs(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 2);
    }

    private static List<List<Card>> getTrips(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 3);
    }

    private static List<List<Card>> getQuads(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 4);
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

    private static List<Card> getAllFlushCards(EnumMap<Suit, List<Card>> cardsBySuit)
    {
        List<Card> flushCards = new ArrayList<>();

        for (Map.Entry<Suit, List<Card>> entry : cardsBySuit.entrySet())
        {
            if (entry.getValue().size() >= 5)
            {
                flushCards.addAll(entry.getValue());
            }
        }

        flushCards.sort((a, b) -> b.getRank().getValue() - a.getRank().getValue());
        return flushCards;
    }

    private static List<Card> getBestFiveCardStraight(List<Card> allCards)
    {
        List<Card> bestStraight = new ArrayList<>();
        List<Card> straightCards = new ArrayList<>();
        List<Card> allCardsCopy = new ArrayList<>(allCards);

        allCardsCopy.sort((a, b) -> b.getRank().getValue() - a.getRank().getValue());

        for (Card card : allCardsCopy)
        {
            if (card.getRank() == Rank.ACE)
            {
                allCardsCopy.add(card);
                break;
            }
        }

        for (int i = 0; i < allCardsCopy.size(); i++)
        {
            boolean isIndexPlusOneInRange = i + 1 < allCardsCopy.size();
            boolean isNextSameRank = isIndexPlusOneInRange && allCardsCopy.get(i).getRank() == allCardsCopy.get(i + 1).getRank();

            if (isNextSameRank)
            {
                continue;
            }

            boolean isPreviousNotSequential = !straightCards.isEmpty()
                    && allCardsCopy.get(i).getRank().getValue() + 1 != straightCards.get(straightCards.size() - 1).getRank().getValue();
            boolean isNextNotSequential = isIndexPlusOneInRange
                    && isPreviousNotSequential
                    && allCardsCopy.get(i + 1).getRank().getValue() - 1 != allCardsCopy.get(i).getRank().getValue();
            boolean isLowAceAndPreviousIsTwo = !straightCards.isEmpty()
                    && allCardsCopy.get(i).getRank() == Rank.ACE
                    && allCardsCopy.get(i - 1).getRank() == Rank.TWO;

            if (isPreviousNotSequential && isNextNotSequential && !isLowAceAndPreviousIsTwo)
            {
                straightCards.clear();

                if (allCardsCopy.size() - i < 5)
                {
                    break;
                }
            }

            straightCards.add(allCardsCopy.get(i));
        }

        if (straightCards.size() >= 5)
        {
            bestStraight = straightCards.subList(0, 5);
        }

        return bestStraight;
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

    private static EnumMap<Suit, List<Card>> getCardsBySuit(List<Card> cards)
    {
        EnumMap<Suit, List<Card>> cardsBySuit = new EnumMap<>(Suit.class);

        for (Card card : cards)
        {
            cardsBySuit.computeIfAbsent(card.getSuit(), suit -> new ArrayList<>()).add(card);
        }

        return cardsBySuit;
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
