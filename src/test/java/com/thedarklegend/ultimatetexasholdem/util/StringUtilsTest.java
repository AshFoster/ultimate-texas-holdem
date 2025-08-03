package com.thedarklegend.ultimatetexasholdem.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringUtilsTest
{
    @Test
    void capitaliseShouldReturnNullWhenInputIsNull()
    {
        assertNull(StringUtils.capitalise(null));
    }

    @Test
    void capitaliseShouldReturnEmptyStringWhenInputIsEmptyString()
    {
        assertEquals("", StringUtils.capitalise(""));
    }

    @Test
    void capitaliseShouldUppercaseOnlyTheFirstLetter()
    {
        assertEquals("Ash", StringUtils.capitalise("ash"));
        assertEquals("Junk", StringUtils.capitalise("junk"));
    }

    @Test
    void capitaliseShouldLowercaseRestOfString()
    {
        assertEquals("Ash", StringUtils.capitalise("aSH"));
        assertEquals("Junk", StringUtils.capitalise("jUNK"));
    }

    @Test
    void capitaliseShouldUppercaseSingleLetters()
    {
        assertEquals("A", StringUtils.capitalise("a"));
    }
}
