package com.thedarklegend.ultimatetexasholdem.game;

import com.thedarklegend.ultimatetexasholdem.model.Card;
import com.thedarklegend.ultimatetexasholdem.model.Dealer;
import com.thedarklegend.ultimatetexasholdem.model.Deck;
import com.thedarklegend.ultimatetexasholdem.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Game
{
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private final List<Card> communityCards;
    private GamePhase gamePhase = GamePhase.NOT_STARTED;

    public Game()
    {
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();
        this.communityCards = new ArrayList<>();
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
        gamePhase = GamePhase.HOLE_CARDS;
    }

    public void dealFlop()
    {
        if (gamePhase != GamePhase.HOLE_CARDS)
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
}
