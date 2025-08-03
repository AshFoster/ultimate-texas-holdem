package com.thedarklegend.ultimatetexasholdem.model;

import com.thedarklegend.ultimatetexasholdem.util.StringUtils;

public class Card
{
    Rank rank;
    Suit suit;

    public Card(Rank rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank()
    {
        return rank;
    }

    public void setRank(Rank rank)
    {
        this.rank = rank;
    }

    public Suit getSuit()
    {
        return suit;
    }

    public void setSuit(Suit suit)
    {
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode()
    {
        return 31 * rank.hashCode() + suit.hashCode();
    }
    @Override
    public String toString()
    {
        return StringUtils.capitalise(rank.name()) + " of " + StringUtils.capitalise(suit.name());
    }
}
