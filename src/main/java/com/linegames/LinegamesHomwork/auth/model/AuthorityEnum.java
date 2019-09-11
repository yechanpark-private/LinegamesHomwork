package com.linegames.LinegamesHomwork.auth.model;

public enum AuthorityEnum {
    MEMBER("ROLE_MEMBER"), ADMIN("ROLE_ADMIN");

    private String authorityName;

    AuthorityEnum(String roleName) {
        this.authorityName = roleName;
    }

    public String getAuthorityName() {
        return authorityName;
    }
}
