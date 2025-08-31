package com.thedarklegend.ultimatetexasholdem.controller;

import com.thedarklegend.ultimatetexasholdem.game.GamePhase;
import com.thedarklegend.ultimatetexasholdem.model.Player;
import com.thedarklegend.ultimatetexasholdem.model.PlayerAction;

import java.util.LinkedList;
import java.util.Queue;

public class MockPlayerController implements PlayerController
{
    private final Player player;
    private final Queue<PlayerAction> actions = new LinkedList<>();

    public MockPlayerController(Player player)
    {
        this.player = player;
    }

    @Override
    public Player getPlayer()
    {
        return player;
    }

    @Override
    public PlayerAction getPlayerAction(GamePhase gamePhase)
    {
        if (actions.isEmpty())
        {
            throw new IllegalStateException("No more actions for " + player.getName());
        }
        return actions.poll();
    }

    public void addAction(PlayerAction action)
    {
        actions.add(action);
    }
}
