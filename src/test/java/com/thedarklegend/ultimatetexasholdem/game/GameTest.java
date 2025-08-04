package com.thedarklegend.ultimatetexasholdem.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest
{
   @Test
    void startShouldDealTwoCardsToPlayerAndDealer()
   {
       Game game = new Game();
       game.start();

       assertEquals(2, game.getPlayer().getHand().size());
       assertEquals(2, game.getDealer().getHand().size());
   }

    @Test
    void dealFlopShouldBurnOneCardAndDealThreeCommunityCards()
    {
        Game game = new Game();
        game.start();
        game.dealFlop();

        assertEquals(52 - 8, game.getDeck().size());
        assertEquals(3, game.getCommunityCards().size());
    }
}
