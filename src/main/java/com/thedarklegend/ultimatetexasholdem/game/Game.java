package com.thedarklegend.ultimatetexasholdem.game;

import com.thedarklegend.ultimatetexasholdem.model.Dealer;
import com.thedarklegend.ultimatetexasholdem.model.Deck;
import com.thedarklegend.ultimatetexasholdem.model.Player;

public class Game
{
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;

    public Game()
    {
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();
    }

    public void start()
    {
        deck.shuffle();
        dealHoleCards();
    }

    private void dealHoleCards()
    {
        player.receiveCard(deck.draw());
        dealer.receiveCard(deck.draw());
        player.receiveCard(deck.draw());
        dealer.receiveCard(deck.draw());
    }

    public Player getPlayer()
    {
        return player;
    }

    public Dealer getDealer()
    {
        return dealer;
    }
}
