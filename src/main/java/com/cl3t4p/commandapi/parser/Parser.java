package com.cl3t4p.commandapi.parser;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;


/**
 * This class is used to parse a string to a specific type.
 * 
 * @author cl3t4p
 * @version 0.2
 * @since 0.2
 */
public abstract class Parser<U> {

    @Getter
    @Setter
    private String message;

    /**
     * Parse a string to a specific type.
     * @param message The message to return if the string cannot be parsed.
     * @throws IllegalArgumentException If the string cannot be parsed will return message.
     */
    public Parser(String message) {
        this.message = message;
    }

    public static final Parser<Player> PLAYER = new Parser<>("Player is not online") {
        @Override
        public Player parse(String string) throws IllegalArgumentException {
            Player player = Bukkit.getPlayer(string);
            if (player == null) {
                throw new IllegalArgumentException(getMessage());
            }
            return player;
        }
    };

    public static final Parser<UUID> UUID = new Parser<>("Invalid UUID") {
        @Override
        public java.util.UUID parse(String string) throws IllegalArgumentException {
            try {
                return java.util.UUID.fromString(string);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage());
            }
        }
    };

    public static final Parser<String> STRING = new Parser<>("") {
        @Override
        public String parse(String string) throws IllegalArgumentException {
            return string;
        }
    };

    public static final Parser<Integer> INTEGER = new Parser<>("Invalid Integer") {
        @Override
        public Integer parse(String string) throws IllegalArgumentException {
            try {
                return Integer.valueOf(string);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage());
            }
        }
    };

    public static final Parser<Double> DOUBLE = new Parser<>("Invalid Double") {
        @Override
        public Double parse(String string) throws IllegalArgumentException {
            try {
                return Double.valueOf(string);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage());
            }
        }
    };

    public static final Parser<Float> FLOAT = new Parser<>("Invalid Float") {
        @Override
        public Float parse(String string) throws IllegalArgumentException {
            try {
                return Float.valueOf(string);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage());
            }
        }
    };

    public static final Parser<java.net.URL> URL = new Parser<>("Invalid URL") {
        @Override
        public java.net.URL parse(String string) throws IllegalArgumentException {
            try {
                return new java.net.URL(string);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage());
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
