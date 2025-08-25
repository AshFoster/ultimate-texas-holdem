package com.thedarklegend.ultimatetexasholdem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest extends AbstractParticipantTest
{
    @Override
    protected Participant createParticipant()
    {
        return new Player();
    }

    @Test
    void parameterisedConstructor_setsNameAndStartingChipsCorrectly()
    {
        Player player = new Player("Ash", 2000);

        assertEquals("Ash", player.getName());
        assertEquals(2000, player.getChips());
    }

    @Test
    void parameterisedConstructor_throwsExceptionWhenNameIsNullOrEmptyOrWhitespace()
    {
        assertThrows(IllegalArgumentException.class, () -> new Player(null, 2000));
        assertThrows(IllegalArgumentException.class, () -> new Player("", 2000));
        assertThrows(IllegalArgumentException.class, () -> new Player("    ", 2000));
    }

    @Test
    void parameterisedConstructor_throwsExceptionWhenChipsIsNegative()
    {
        assertThrows(IllegalArgumentException.class, () -> new Player("Ash", -10));
    }

    @Test
    void defaultConstructorAppliesDefaultNameAndChipsCorrectly()
    {
        Player player = (Player) participant;

        assertEquals("Player1", player.getName());
        assertEquals(1000, player.getChips());
    }

    @Test
    void defaultConstructorAppliesDefaultNameAndChipCountCorrectlyForMultiplePlayers()
    {
        Player player1 = (Player) participant;
        Player player2 = new Player();
        Player player3 = new Player();

        assertEquals("Player1", player1.getName());
        assertEquals(1000, player1.getChips());
        assertEquals("Player2", player2.getName());
        assertEquals(1000, player2.getChips());
        assertEquals("Player3", player3.getName());
        assertEquals(1000, player3.getChips());
    }
}
