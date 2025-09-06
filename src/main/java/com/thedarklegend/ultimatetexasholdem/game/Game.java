package com.thedarklegend.ultimatetexasholdem.game;

import com.thedarklegend.ultimatetexasholdem.model.*;

import java.util.*;

public class Game
{
    private final Deck deck = new Deck();
    private final Dealer dealer = new Dealer();
    private final Player player;
    private final List<Card> communityCards = new ArrayList<>();
    private GamePhase gamePhase = GamePhase.NOT_STARTED;

    public Game()
    {
        this.player = new Player();
    }

    public Game(Player player)
    {
        this.player = player;
    }

    public void dealHoleCards()
    {
        player.receiveCard(deck.draw());
        dealer.receiveCard(deck.draw());
        player.receiveCard(deck.draw());
        dealer.receiveCard(deck.draw());
        gamePhase = GamePhase.PRE_FLOP;
    }

    public void dealFlop()
    {
        if (gamePhase != GamePhase.PRE_FLOP)
        {
            throw new IllegalStateException("Must deal hole cards first!");
        }

        burn();
        for (int i = 0; i < 3; i++)
        {
            communityCards.add(deck.draw());
        }
        gamePhase = GamePhase.FLOP;
    }

    public void dealTurnAndRiver()
    {
        if (gamePhase != GamePhase.FLOP)
        {
            throw new IllegalStateException("Must deal flop first!");
        }

        burn();
        communityCards.add(deck.draw());
        burn();
        communityCards.add(deck.draw());
        gamePhase = GamePhase.TURN_AND_RIVER;
    }

    private void burn()
    {
        deck.draw();
    }

    public void reset()
    {
        deck.reset();
        deck.shuffle();
        player.resetHand();
        dealer.resetHand();
        communityCards.clear();
        gamePhase = GamePhase.NOT_STARTED;
    }

    public Deck getDeck()
    {
        return deck;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Dealer getDealer()
    {
        return dealer;
    }

    public List<Card> getCommunityCards()
    {
        return new ArrayList<>(communityCards);
    }

    public GamePhase getGamePhase()
    {
        return gamePhase;
    }
}
