package ru.oa2.edu.api.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Getter
@Setter
public class UserDataStatistic {
    private TreeSet<String> labels;
    private TreeSet<Integer> finishedCount;
    private TreeSet<Integer> total;
    private List<TreeSet<Integer>> series;

    public UserDataStatistic() {
        labels = new TreeSet<>();
        total = new TreeSet<>();
        finishedCount = new TreeSet<>();
    }

    public void addLabel(String label) {
        labels.add(label);
    }

    public void addFinishedCount(int count) {
        finishedCount.add(count);
    }

    public void addTotal(int count) {
        total.add(count);
    }

    public List<TreeSet<Integer>> getSeries() {
        return List.of(total, finishedCount);
    }
}
