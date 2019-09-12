package com.linegames.LinegamesHomwork.auth.model;

/**
 * 관리되는 권한에 대한 Enum
 */
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
