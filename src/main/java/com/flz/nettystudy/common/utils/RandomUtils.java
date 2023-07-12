package com.flz.nettystudy.common.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {
    private static final Random RANDOM = new Random();

    public static int nextInt() {
        return RANDOM.nextInt();
    }

    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static long nextLong() {
        return RANDOM.nextLong();
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
