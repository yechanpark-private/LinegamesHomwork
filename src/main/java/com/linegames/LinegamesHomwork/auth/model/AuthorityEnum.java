package com.linegames.LinegamesHomwork.auth.model;

public enum AuthorityEnum {
    MEMBER("MEMBER"), ADMIN("ADMIN");

    private String authorityName;

    AuthorityEnum(String roleName) {
        this.authorityName = roleName;
    }

    public String getAuthorityName() {
        return authorityName;
    }
}
