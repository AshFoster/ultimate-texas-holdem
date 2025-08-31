package com.thedarklegend.ultimatetexasholdem.game;

public enum GamePhase
{
    NOT_STARTED(false),
    PRE_FLOP(true),
    FLOP(true),
    TURN_AND_RIVER(true);

    private final boolean bettingPhase;

    GamePhase(boolean bettingAllowed)
    {
        this.bettingPhase = bettingAllowed;
    }

    public boolean isBettingPhase()
    {
        return bettingPhase;
    }
}
