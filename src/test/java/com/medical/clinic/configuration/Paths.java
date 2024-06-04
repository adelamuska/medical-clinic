package com.medical.clinic.configuration;

public enum Paths {
    LOGIN("/api/v1/auth/log-in"), SIGN_UP("/api/v1/auth/sign-up");

    private final String value;

    Paths(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
