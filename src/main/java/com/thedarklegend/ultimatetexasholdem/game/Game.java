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
    }

    public void dealFlop()
    {
        burn();
        for (int i = 0; i < 3; i++)
        {
            communityCards.add(deck.draw());
        }
    }

    public void dealTurnAndRiver()
    {
        burn();
        communityCards.add(deck.draw());
        burn();
        communityCards.add(deck.draw());
    }

    private void burn()
    {
        deck.draw();
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
