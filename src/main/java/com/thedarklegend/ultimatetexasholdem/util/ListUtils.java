package com.thedarklegend.ultimatetexasholdem.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ListUtils
{
    @SafeVarargs
    public static <T> List<T> combineLists(List<T>... lists)
    {
        if (lists == null) return null;
        if (lists.length == 0) return Collections.emptyList();

        return Stream.of(lists)
                     .filter(Objects::nonNull)
                     .flatMap(Collection::stream)
                     .toList();
    }
}
