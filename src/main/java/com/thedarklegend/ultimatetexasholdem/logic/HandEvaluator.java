package com.thedarklegend.ultimatetexasholdem.logic;

import com.thedarklegend.ultimatetexasholdem.model.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.thedarklegend.ultimatetexasholdem.logic.HandEvaluatorUtils.*;

public class HandEvaluator
{
    public static EvaluatedHand evaluate(List<Card> allCards)
    {
        validateCards(allCards);

        EnumMap<Rank, List<Card>> cardsByRank = groupCardsByRank(allCards);
        List<List<Card>> pairs = extractPairs(cardsByRank);
        List<List<Card>> trips = extractTrips(cardsByRank);
        List<List<Card>> quads = extractQuads(cardsByRank);

        List<Card> flushes = extractAllFlushCards(allCards);
        List<Card> bestStraight = extractBestFiveCardStraight(allCards);
        List<Card> bestStraightFlush = extractBestFiveCardStraight(flushes);

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

    private static EvaluatedHand buildHand(HandRank handRank,
                                           List<Card> startingCards)

    {
        return buildHand(handRank, null, startingCards);
    }

    private static EvaluatedHand buildHand(HandRank handRank,
                                           List<Card> allCards,
                                           List<Card> startingCards)
    {
        boolean needsKickers = startingCards.size() < 5;
        List<Card> bestHand = needsKickers ? extractBestFiveCards(allCards, startingCards)
                                           : new ArrayList<>(startingCards);

        List<Rank> orderedRanks = extractRanks(bestHand);

        return EvaluatedHand.create(handRank, bestHand, orderedRanks);
    }

    private static EvaluatedHand generateBestStraightFlushHand(List<Card> bestStraightFlush)
    {
        if (bestStraightFlush.isEmpty())
        {
            return null;
        }

        HandRank handRank = bestStraightFlush.get(0).getRank() == Rank.ACE ? HandRank.ROYAL_FLUSH
                                                                           : HandRank.STRAIGHT_FLUSH;

        return buildHand(handRank, bestStraightFlush);
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

        return buildHand(HandRank.FULL_HOUSE, fullHouse);
    }

    private static EvaluatedHand generateBestFlushHand(List<Card> flushes)
    {
        if (flushes.isEmpty())
        {
            return null;
        }

        return buildHand(HandRank.FLUSH, flushes.subList(0, 5));
    }

    private static EvaluatedHand generateBestStraightHand(List<Card> straight)
    {
        if (straight.isEmpty())
        {
            return null;
        }

        return buildHand(HandRank.STRAIGHT, straight);
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
}
