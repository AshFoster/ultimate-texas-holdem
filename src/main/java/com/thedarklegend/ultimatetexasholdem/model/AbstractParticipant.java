package com.thedarklegend.ultimatetexasholdem.model;

import java.util.ArrayList;
import java.util.List;

public class AbstractParticipant implements Participant
{
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private EvaluatedHand evaluatedHand;

    public AbstractParticipant(String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name cannot be null or empty!");
        }

        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
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
            throw new IllegalStateException(this.getClass().getSimpleName() + " already has 2 cards!");
        }
        hand.add(card);
    }

    @Override
    public void resetHand()
    {
        hand.clear();
    }
}
