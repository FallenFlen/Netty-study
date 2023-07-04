package com.flz.nettystudy.common.exception;

public class MyClientDisconnectedException extends RuntimeException {
    public MyClientDisconnectedException() {
    }

    public MyClientDisconnectedException(String message) {
        super(message);
    }
}
