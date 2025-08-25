package com.thedarklegend.ultimatetexasholdem.model;

import com.thedarklegend.ultimatetexasholdem.util.StringUtils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Player implements Participant
{
    private static int playerCounter = 0;
    private static final int DEFAULT_CHIPS = 1000;
    private final String name;
    private int chips;
    private final Map<BetType, Integer> bets = new EnumMap<>(BetType.class);
    private final List<Card> hand = new ArrayList<>();
    private EvaluatedHand evaluatedHand;

    public Player()
    {
        this.name = "Player" + (++playerCounter);
        this.chips = DEFAULT_CHIPS;
    }

    public Player(String name, int startingChips)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name cannot be null or empty!");
        }

        if (startingChips < 0)
        {
            throw new IllegalArgumentException("Starting chips cannot be negative!");
        }

        this.name = name;
        this.chips = startingChips;
    }

    public String getName()
    {
        return name;
    }

    public int getChips()
    {
        return chips;
    }

    @Override
    public List<Card> getHand()
    {
        return new ArrayList<>(hand);
    }

    @Override
    public EvaluatedHand getEvaluatedHand()
    {
        return evaluatedHand;
    }

    @Override
    public void setEvaluatedHand(EvaluatedHand evaluatedHand)
    {
        this.evaluatedHand = evaluatedHand;
    }

    @Override
    public void receiveCard(Card card)
    {
        if (hand.size() >= 2)
        {
            throw new IllegalStateException("Player already has 2 cards!");
        }
        hand.add(card);
    }

    @Override
    public void resetHand()
    {
        hand.clear();
    }

    public static void resetPlayerCounter()
    {
        playerCounter = 0;
    }

    public int getAnte()
    {
        return bets.getOrDefault(BetType.ANTE, 0);
    }

    public int getBlind()
    {
        return bets.getOrDefault(BetType.BLIND, 0);
    }

    public int getTrips()
    {
        return bets.getOrDefault(BetType.TRIPS, 0);
    }

    public int getBets()
    {
        return bets.getOrDefault(BetType.BET, 0);
    }

    private void placeBetByType(BetType betType, int amount)
    {
        if (amount <= 0)
        {
            throw new IllegalArgumentException(StringUtils.capitalise(betType.name()) + " must be positive");
        }

        if (amount > chips)
        {
            throw new IllegalArgumentException("Not enough chips to play!");
        }

        chips -= amount;
        bets.merge(betType, amount, Integer::sum);
    }

    public void placeAnteAndBlind(int amount)
    {
        placeBetByType(BetType.ANTE, amount);
        placeBetByType(BetType.BLIND, amount);
    }

    public void placeTripsBet(int amount)
    {
        placeBetByType(BetType.TRIPS, amount);
    }

    public void placeBet(BettingRound round, int multiplier)
    {
        validateBet(round, multiplier);
        placeBetByType(BetType.BET, getAnte() * multiplier);
    }

    private void validateBet(BettingRound round, int multiplier)
    {
        switch (round)
        {
            case PRE_FLOP ->
            {
                if (multiplier != 3 && multiplier != 4)
                {
                    throw new IllegalArgumentException("Pre-Flop bet must be 3x or 4x the ante!");
                }
            }
            case FLOP ->
            {
                if (multiplier != 2)
                {
                    throw new IllegalArgumentException("Flop bet must be 2x the ante!");
                }
            }
            case TURN_AND_RIVER ->
            {
                if (multiplier != 1)
                {
                    throw new IllegalArgumentException("Turn-and-River bet must be 1x the ante!");
                }
            }
        }
    }

    private enum BetType
    {
        ANTE,
        BLIND,
        TRIPS,
        BET
    }

    enum BettingRound
    {
        PRE_FLOP,
        FLOP,
        TURN_AND_RIVER
    }
}
