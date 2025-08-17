package com.thedarklegend.ultimatetexasholdem.logic;

import com.thedarklegend.ultimatetexasholdem.model.Card;
import com.thedarklegend.ultimatetexasholdem.model.Rank;
import com.thedarklegend.ultimatetexasholdem.model.Suit;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

class HandEvaluatorUtils
{
    static EnumMap<Rank, List<Card>> groupCardsByRank(List<Card> cards)
    {
        EnumMap<Rank, List<Card>> cardsByRank = new EnumMap<>(Rank.class);

        for (Card card : cards)
        {
            cardsByRank.computeIfAbsent(card.getRank(), rank -> new ArrayList<>()).add(card);
        }

        return cardsByRank;
    }

    static EnumMap<Suit, List<Card>> groupCardsBySuit(List<Card> cards)
    {
        EnumMap<Suit, List<Card>> cardsBySuit = new EnumMap<>(Suit.class);

        for (Card card : cards)
        {
            cardsBySuit.computeIfAbsent(card.getSuit(), suit -> new ArrayList<>()).add(card);
        }

        return cardsBySuit;
    }

    static List<List<Card>> extractGroupedRanksBySize(EnumMap<Rank, List<Card>> cardsByRank, int size)
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

    static List<List<Card>> extractQuads(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return extractGroupedRanksBySize(cardsByRank, 4);
    }

    static List<List<Card>> extractTrips(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return extractGroupedRanksBySize(cardsByRank, 3);
    }

    static List<List<Card>> extractPairs(EnumMap<Rank, List<Card>> cardsByRank)
    {
        return extractGroupedRanksBySize(cardsByRank, 2);
    }

    static List<Card> extractAllFlushCards(List<Card> allCards)
    {
        EnumMap<Suit, List<Card>> cardsBySuit = groupCardsBySuit(allCards);
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

    static List<Card> extractBestFiveCardStraight(List<Card> allCards)
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

    static List<Card> extractBestFiveCards(List<Card> allCards, List<Card> currentCards)
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

    static List<Rank> extractRanks(List<Card> currentCards)
    {
        return currentCards.stream()
                           .map(Card::getRank)
                           .distinct()
                           .toList();
    }
}
