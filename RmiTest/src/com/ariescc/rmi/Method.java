package com.ariescc.rmi;

public enum Method {

    REGISTER("register"),
    ADD("add"),
    QUERY("query"),
    DELETE("delete"),
    CLEAR("clear");

    private final String text;

    private Method(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
