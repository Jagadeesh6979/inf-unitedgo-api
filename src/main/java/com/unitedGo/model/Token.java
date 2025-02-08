package com.unitedGo.model;

import lombok.Data;

@Data
public class Token {
    private String token;
    private String type;

    public Token(String token) {
        this.token = token;
        this.type = "Bearer";
    }
}
