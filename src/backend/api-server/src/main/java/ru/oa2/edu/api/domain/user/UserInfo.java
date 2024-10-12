package ru.oa2.edu.api.domain.user;

import java.util.Map;

public record UserInfo(String id, String family, String name, String email, UserDataStatistic userStatistic) {

}
