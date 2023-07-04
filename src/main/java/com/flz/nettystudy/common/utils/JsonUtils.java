package com.flz.nettystudy.common.utils;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {
    private static final Gson GSON = new Gson();

    public static String silentMarshal(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T silentUnmarshal(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
}
