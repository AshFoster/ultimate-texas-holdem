package com.thedarklegend.ultimatetexasholdem.model;

import java.util.List;

public interface Participant
{
    List<Card> getHand();
    EvaluatedHand getEvaluatedHand();
    void setEvaluatedHand(EvaluatedHand evaluatedHand);
    void receiveCard(Card card);
    void resetHand();
}
