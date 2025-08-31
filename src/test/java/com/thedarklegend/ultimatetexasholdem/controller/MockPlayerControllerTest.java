package com.thedarklegend.ultimatetexasholdem.controller;

import com.thedarklegend.ultimatetexasholdem.model.BettingRound;
import com.thedarklegend.ultimatetexasholdem.model.Player;
import com.thedarklegend.ultimatetexasholdem.model.PlayerAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MockPlayerControllerTest
{
    private Player player;
    private MockPlayerController controller;

    @BeforeEach
    void beforeEachTest()
    {
        player = new Player("Ash", 1000);
        controller = new MockPlayerController(player);
    }

    @Test
    void getPlayer_shouldReturnCorrectPlayer()
    {
        assertEquals("Ash", controller.getPlayer().getName());
        assertEquals(1000, controller.getPlayer().getChips());
    }

    @Test
    void getPlayerAction_shouldReturnActionsInOrder()
    {
        controller.addAction(PlayerAction.CHECK);
        controller.addAction(PlayerAction.BET);
        assertEquals(PlayerAction.CHECK, controller.getPlayerAction(BettingRound.PRE_FLOP));
        assertEquals(PlayerAction.BET, controller.getPlayerAction(BettingRound.FLOP));
    }

    @Test
    void getPlayerAction_shouldThrowException_whenNoMoreActions()
    {
        assertThrows(IllegalStateException.class,
                     () -> controller.getPlayerAction(BettingRound.TURN_AND_RIVER));
    }
}
