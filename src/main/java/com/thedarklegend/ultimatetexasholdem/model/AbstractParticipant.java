package com.thedarklegend.ultimatetexasholdem.model;

import java.util.ArrayList;
import java.util.List;

public class AbstractParticipant implements Participant
{
    private final List<Card> hand = new ArrayList<>();
    private EvaluatedHand evaluatedHand;
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
