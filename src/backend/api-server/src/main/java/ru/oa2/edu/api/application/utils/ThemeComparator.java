package ru.oa2.edu.api.application.utils;

import ru.oa2.edu.api.domain.theme.ThemeDTO;

import java.util.Comparator;

public class ThemeComparator implements Comparator<ThemeDTO> {
    @Override
    public int compare(ThemeDTO t1, ThemeDTO t2) {
        return Long.compare(t1.getId(), t2.getId());
    }
}
