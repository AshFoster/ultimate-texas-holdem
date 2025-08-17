package com.thedarklegend.ultimatetexasholdem.logic;

import com.thedarklegend.ultimatetexasholdem.model.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class HandEvaluator
{
    public static EvaluatedHand evaluate(List<Card> allCards)
    {
        validateCards(allCards);

        EnumMap<Rank, List<Card>> cardsByRank = getCardsByRank(allCards);
        List<List<Card>> pairs = getPairs(cardsByRank);
        List<List<Card>> trips = getTrips(cardsByRank);
        List<List<Card>> quads = getQuads(cardsByRank);

        List<Card> flushes = getAllFlushCards(allCards);
        List<Card> bestStraight = getBestFiveCardStraight(allCards);
        List<Card> bestStraightFlush = getBestFiveCardStraight(flushes);

        return Stream.<Supplier<EvaluatedHand>>
                             of(() -> generateBestStraightFlushHand(bestStraightFlush),
                                () -> generateBestFourOfAKindHand(allCards, quads),
                                () -> generateBestFullHouseHand(trips, pairs),
                                () -> generateBestFlushHand(flushes),
                                () -> generateBestStraightHand(bestStraight),
                                () -> generateBestThreeOfAKindHand(allCards, trips),
                                () -> generateBestTwoPairHand(allCards, pairs),
                                () -> generateBestPairHand(allCards, pairs),
                                () -> generateBestHighCardHand(allCards))
                     .map(Supplier::get)
                     .filter(Objects::nonNull)
                     .findFirst()
                     .orElseThrow();
    }

    private static void validateCards(List<Card> allCards)
    {
        Objects.requireNonNull(allCards, "Hand cannot be null!");

        if (allCards.size() != 7)
        {
            throw new IllegalArgumentException("evaluate() requires exactly 7 cards but got " + allCards.size());
        }
    }

    private static EvaluatedHand generateBestStraightFlushHand(List<Card> bestStraightFlush)
    {
        if (bestStraightFlush.isEmpty())
        {
            return null;
        }

        HandRank handRank = bestStraightFlush.get(0).getRank() == Rank.ACE ? HandRank.ROYAL_FLUSH
                                                                           : HandRank.STRAIGHT_FLUSH;

        return buildHand(handRank, null, bestStraightFlush);
    }

    private static EvaluatedHand generateBestFourOfAKindHand(List<Card> allCards,
                                                             List<List<Card>> quads)
    {
        if (quads.isEmpty())
        {
            return null;
        }

        return buildHand(HandRank.FOUR_OF_A_KIND, allCards, quads.get(0));
    }

    private static EvaluatedHand generateBestFullHouseHand(List<List<Card>> trips,
                                                           List<List<Card>> pairs)
    {
        if (trips.isEmpty() || (pairs.isEmpty() && trips.size() < 2))
        {
            return null;
        }

        List<Card> fullHouse = new ArrayList<>(trips.get(0));

        if (trips.size() > 1)
        {
            fullHouse.addAll(trips.get(1).subList(0,2));
        }
        else
        {
            fullHouse.addAll(pairs.get(0));
        }

        return buildHand(HandRank.FULL_HOUSE, null, fullHouse);
    }

    private static EvaluatedHand generateBestFlushHand(List<Card> flushes)
    {
        if (flushes.isEmpty())
        {
            return null;
        }

        return buildHand(HandRank.FLUSH, null, flushes.subList(0, 5));
    }

    private static EvaluatedHand generateBestStraightHand(List<Card> straight)
    {
        if (straight.isEmpty())
        {
            return null;
        }

        return buildHand(HandRank.STRAIGHT, null, straight);
    }

    private static EvaluatedHand generateBestThreeOfAKindHand(List<Card> allCards,
                                                              List<List<Card>> trips)
    {
        if (trips.isEmpty())
        {
            return null;
        }

        return buildHand(HandRank.THREE_OF_A_KIND, allCards, trips.get(0));
    }

    private static EvaluatedHand generateBestTwoPairHand(List<Card> allCards,
                                                         List<List<Card>> pairs)
    {
        if (pairs.isEmpty())
        {
            return null;
        }

        List<Card> twoPairs = new ArrayList<>(pairs.get(0));

        if (pairs.size() < 2)
        {
            return null;
        }

        twoPairs.addAll(pairs.get(1));

        return buildHand(HandRank.TWO_PAIR, allCards, twoPairs);
    }

    private static EvaluatedHand generateBestPairHand(List<Card> allCards,
                                                      List<List<Card>> pairs)
    {
        if (pairs.isEmpty())
        {
            return null;
        }

        return buildHand(HandRank.PAIR, allCards, pairs.get(0));
    }

    private static EvaluatedHand generateBestHighCardHand(List<Card> allCards)
    {
        return buildHand(HandRank.HIGH_CARD, allCards, Collections.emptyList());
    }

    private static List<List<Card>> getQuads(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 4);
    }

    private static List<Card> getAllFlushCards(List<Card> allCards)
    {
        EnumMap<Suit, List<Card>> cardsBySuit = getCardsBySuit(allCards);
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

    private static List<List<Card>> getTrips(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 3);
    }

    private static List<List<Card>> getPairs(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return getGroupedRanksBySize(cardsByRank, 2);
    }

    private static EvaluatedHand buildHand(HandRank handRank,
                                           List<Card> allCards,
                                           List<Card> startingCards)
    {
        boolean needsKickers = startingCards.size() < 5;
        List<Card> bestHand = needsKickers ? extractBestFiveCards(allCards, startingCards)
                                           : new ArrayList<>(startingCards);

        List<Rank> orderedRanks = extractRanks(bestHand);

        System.out.println(bestHand);
        System.out.println(orderedRanks);

        return EvaluatedHand.create(handRank, bestHand, orderedRanks);
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
