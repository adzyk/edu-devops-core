package ru.oa2.edu.api.domain.user;

public class User {
    private Long id;
    private String internalId;

    public User() {

    }

    public User(String internalId) {
        this.internalId = internalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }
}
