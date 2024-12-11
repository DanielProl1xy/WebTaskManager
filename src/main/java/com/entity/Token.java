package com.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("token")
public class Token {

    private final Long userid;

    private final String token;

    public Token(Long userid, String token) {
        this.userid = userid;
        this.token =token;
    }

    public Long getUserid() {
        return userid;
    }

    public String getToken() {
        return token;
    }
}
