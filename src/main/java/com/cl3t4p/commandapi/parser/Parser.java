package com.cl3t4p.commandapi.parser;


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



    public Parser() {
    }

    /**
     * Message to be sent when the parser fails.
     * @param index index of the string that failed to parse.
     * @return the message to be sent.
     */
    public abstract String getMessage(int index);

    public static final Parser<Player> PLAYER = new Parser<>() {
        @Override
        public String getMessage(int index) {
            return "Player not found or not online at index " + index;
        }


        @Override
        public Player parse(String[] string,int index) throws IllegalArgumentException {
            Player player = Bukkit.getPlayer(string[index]);
            if (player == null) {
                throw new IllegalArgumentException(getMessage(index));
            }
            return player;
        }
    };

    public static final Parser<UUID> UUID = new Parser<>() {
        @Override
        public String getMessage(int index) {
            return "Invalid UUID at index " + index;
        }

        @Override
        public java.util.UUID parse(String[] string,int index) throws IllegalArgumentException {
            try {
                return java.util.UUID.fromString(string[index]);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage(index));
            }
        }
    };

    public static final Parser<String> STRING = new Parser<>() {
        @Override
        public String getMessage(int index) {
            return "";
        }

        @Override
        public String parse(String[] string,int index) throws IllegalArgumentException {
            return string[index];
        }
    };

    public static final Parser<Integer> INTEGER = new Parser<>() {
        @Override
        public String getMessage(int index) {
            return "Invalid Integer at index " + index;
        }

        @Override
        public Integer parse(String[] string,int index) throws IllegalArgumentException {
            try {
                return Integer.valueOf(string[index]);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage(index));
            }
        }
    };

    public static final Parser<Double> DOUBLE = new Parser<>() {
        @Override
        public String getMessage(int index) {
            return "Invalid Double at index " + index;
        }

        @Override
        public Double parse(String[] string,int index) throws IllegalArgumentException {
            try {
                return Double.valueOf(string[index]);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage(index));
            }
        }
    };

    public static final Parser<Float> FLOAT = new Parser<>() {
        @Override
        public String getMessage(int index) {
            return "Invalid Float at index " + index;
        }

        @Override
        public Float parse(String[] string,int index) throws IllegalArgumentException {
            try {
                return Float.valueOf(string[index]);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage(index));
            }
        }
    };

    public static final Parser<java.net.URL> URL = new Parser<>() {
        @Override
        public String getMessage(int index) {
            return "Invalid URL at index " + index;
        }

        @Override
        public java.net.URL parse(String[] string,int index) throws IllegalArgumentException {
            try {
                return new java.net.URL(string[index]);
            } catch (Exception e) {
                throw new IllegalArgumentException(getMessage(index));
            }
        }


    };

    public static final Parser<String[]> STRING_ARRAY = new Parser<>() {
        @Override
        public String getMessage(int index) {
            return "";
        }

        @Override
        public String[] parse(String[] string,int index) throws IllegalArgumentException {
            String[] returnArray = new String[string.length - index];
            if (string.length - index >= 0)
                System.arraycopy(string, index, returnArray, 0, string.length - index);
            return returnArray;
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

    public abstract U parse(String[] string,int index) throws IllegalArgumentException;
}
