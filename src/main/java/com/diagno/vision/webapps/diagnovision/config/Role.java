package com.diagno.vision.webapps.diagnovision.config;

public enum Role {
    ROLE_REGULAR("REGULAR"),
    ROLE_PROFESSIONAL("PROFESSIONAL"),
    ROLE_ADMIN("ADMIN");

    private String value;

    Role(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
