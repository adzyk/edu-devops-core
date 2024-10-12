package ru.oa2.edu.api.domain.user;

import java.util.Map;

public record UserStatistic(Map<String, StatJob> stats) {
}
