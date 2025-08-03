package com.thedarklegend.ultimatetexasholdem.util;

public class StringUtils
{
    public static String capitalise(String input)
    {
        if (input == null) return null;
        if (input.isBlank()) return input;

        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
