package com.thedarklegend.ultimatetexasholdem.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest
{
   @Test
    void startShouldDealTwoCardsToPlayerAndDealer()
   {
       Game game = new Game();
       game.reset();
       game.dealHoleCards();

       assertEquals(2, game.getPlayer().getHand().size());
       assertEquals(2, game.getDealer().getHand().size());
   }

    @Test
    void dealFlopShouldBurnOneCardAndDealThreeCommunityCards()
    {
        Game game = new Game();
        game.reset();
        game.dealHoleCards();
        game.dealFlop();

        assertEquals(52 - 8, game.getDeck().size());
        assertEquals(3, game.getCommunityCards().size());
    }

    @Test
    void dealTurnAndRiverShouldBurnTwoCardsAndDealTwoCommunityCards()
    {
        Game game = new Game();
        game.reset();
        game.dealHoleCards();
        game.dealFlop();
        game.dealTurnAndRiver();

        assertEquals(52 - 12, game.getDeck().size());
        assertEquals(5, game.getCommunityCards().size());
    }

    @Test
    void resetShouldClearHandsAndCommunityCards()
    {
        Game game = new Game();
        game.reset();
        game.dealHoleCards();
        game.dealFlop();
        game.dealTurnAndRiver();
        game.reset();

        assertEquals(0, game.getPlayer().getHand().size());
        assertEquals(0, game.getDealer().getHand().size());
        assertEquals(0, game.getCommunityCards().size());
    }

    @Test
    void callingDealFlopOutOfOrderThrowsException()
    {
        Game game = new Game();

        assertThrows(IllegalStateException.class, game::dealFlop);
    }

    @Test
    void callingDealTurnAndRiverOutOfOrderThrowsException()
    {
        Game game = new Game();

        assertThrows(IllegalStateException.class, game::dealTurnAndRiver);
    }
}
