package com.thedarklegend.ultimatetexasholdem.controller;

import com.thedarklegend.ultimatetexasholdem.model.BettingRound;
import com.thedarklegend.ultimatetexasholdem.model.Player;
import com.thedarklegend.ultimatetexasholdem.model.PlayerAction;

public interface PlayerController
{
    Player getPlayer();
    PlayerAction getPlayerAction(BettingRound round);
}
