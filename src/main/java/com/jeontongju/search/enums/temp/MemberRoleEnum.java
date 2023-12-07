package com.jeontongju.search.enums.temp;

public enum MemberRoleEnum {
    ROLE_CONSUMER("consumer"),
    ROLE_SELLER("seller"),
    ROLE_ADMIN("admin");

    private final String value;

    private MemberRoleEnum(String value) { this.value = value; }
}