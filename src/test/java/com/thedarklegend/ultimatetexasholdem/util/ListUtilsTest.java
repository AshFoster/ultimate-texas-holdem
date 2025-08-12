package com.thedarklegend.ultimatetexasholdem.util;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ListUtilsTest
{
    @Test
    void combineListsShouldCombineTwoListsIntoOne()
    {
        List<String> string1 = List.of("Hello", "There");
        List<String> string2 = List.of("Cheese", "Chicken");

        assertEquals(List.of("Hello", "There", "Cheese", "Chicken"), ListUtils.combineLists(string1, string2));
    }

    @Test
    void combineListsShouldReturnNullWhenInputIsNull()
    {
        assertNull(ListUtils.combineLists(null));
    }

    @Test
    void combineListsShouldReturnAnEmptyListWhenInputListIsEmpty()
    {
        assertEquals(Collections.emptyList(), ListUtils.combineLists(Collections.emptyList()));
    }

    @Test
    void combineListsShouldCombineMultipleListsIntoOne()
    {
        List<String> string1 = List.of("Hello", "There");
        List<String> string2 = List.of("Cheese", "Chicken");
        List<String> string3 = List.of("Egg", "Dead");

        assertEquals(List.of("Hello", "There", "Cheese", "Chicken", "Egg", "Dead"),
                     ListUtils.combineLists(string1, string2, string3));
    }

    @Test
    void combineListsShouldIgnoreNullLists()
    {
        List<String> string1 = List.of("Hello", "There");
        List<String> string2 = List.of("Egg", "Dead");

        assertEquals(List.of("Hello", "There", "Egg", "Dead"),
                     ListUtils.combineLists(string1, null, string2));
    }
}
