package com.cl3t4p.commandapi.parser;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class Parser<U> {

    public static final Parser<Player> PLAYER = new Parser<>() {
        @Override
        public Player parse(String string) throws IllegalArgumentException {
            Player player = Bukkit.getPlayer(string);
            if (player == null) {
                throw new IllegalArgumentException("Player is not online");
            }
            return player;
        }
    };

    public static final Parser<UUID> UUID = new Parser<>() {
        @Override
        public java.util.UUID parse(String string) throws IllegalArgumentException {
            try {
                return java.util.UUID.fromString(string);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid UUID");
            }
        }
    };

    public static final Parser<String> STRING = new Parser<>() {
        @Override
        public String parse(String string) throws IllegalArgumentException {
            return string;
        }
    };

    public static final Parser<Integer> INTEGER = new Parser<>() {
        @Override
        public Integer parse(String string) throws IllegalArgumentException {
            try {
                return Integer.valueOf(string);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid Integer");
            }
        }
    };

    public static final Parser<Double> DOUBLE = new Parser<>() {
        @Override
        public Double parse(String string) throws IllegalArgumentException {
            try {
                return Double.valueOf(string);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid Double");
            }
        }
    };

    public static final Parser<Float> FLOAT = new Parser<>() {
        @Override
        public Float parse(String string) throws IllegalArgumentException {
            try {
                return Float.valueOf(string);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid Float");
            }
        }
    };

    public static final Parser<java.net.URL> URL = new Parser<>() {
        @Override
        public java.net.URL parse(String string) throws IllegalArgumentException {
            try {
                return new java.net.URL(string);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid URL");
            }
        }
    };

    public static HashMap<Class<?>, Parser<?>> newMap() {
        HashMap<Class<?>, Parser<?>> map = new HashMap<>();
        map.put(Player.class, PLAYER);
        map.put(String.class, STRING);
        map.put(UUID.class, UUID);
        map.put(Integer.class, INTEGER);
        map.put(Double.class, DOUBLE);
        map.put(Float.class, FLOAT);
        map.put(java.net.URL.class, URL);
        return map;
    }

    public abstract U parse(String string) throws IllegalArgumentException;
}
