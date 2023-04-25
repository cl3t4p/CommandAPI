package com.cl3t4p.commandapi.parser;

import com.cl3t4p.commandapi.CommandManager;
import com.cl3t4p.lib.chatlib.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

/**
 * This class is used to parse a string to a specific type.
 *
 * @author cl3t4p
 *
 * @version 0.3
 *
 * @since 0.2
 */
public abstract class Parser<U> {

    public static final Parser<Player> PLAYER = new Parser<>("player") {

        @Override
        public Response<Player> parse(String[] string, int index) throws IllegalArgumentException {
            Player player = Bukkit.getPlayer(string[index]);
            if (player == null) {
                throw new IllegalArgumentException(getKey());
            }
            return new Response<>(player, index);
        }
    };
    public static final Parser<UUID> UUID = new Parser<>("uuid") {

        @Override
        public Response<java.util.UUID> parse(String[] string, int index) throws IllegalArgumentException {
            try {
                return new Response<>(java.util.UUID.fromString(string[index]), index);
            } catch (Exception e) {
                throw new IllegalArgumentException(getKey());
            }
        }
    };
    public static final Parser<String> STRING = new Parser<>("string") {

        @Override
        public Response<String> parse(String[] string, int index) throws IllegalArgumentException {
            return new Response<>(string[index], index);
        }
    };
    public static final Parser<Integer> INTEGER = new Parser<>("integer") {
        @Override
        public Response<Integer> parse(String[] string, int index) throws IllegalArgumentException {
            try {
                return new Response<>(Integer.valueOf(string[index]), index);
            } catch (Exception e) {
                throw new IllegalArgumentException(getKey());
            }
        }
    };
    public static final Parser<Double> DOUBLE = new Parser<>("double") {

        @Override
        public Response<Double> parse(String[] string, int index) throws IllegalArgumentException {
            try {
                return new Response<>(Double.valueOf(string[index]), index);
            } catch (Exception e) {
                throw new IllegalArgumentException(getKey());
            }
        }
    };
    public static final Parser<Float> FLOAT = new Parser<>("float") {
        @Override
        public Response<Float> parse(String[] string, int index) throws IllegalArgumentException {
            try {
                return new Response<>(Float.valueOf(string[index]), index);
            } catch (Exception e) {
                throw new IllegalArgumentException(getKey());
            }
        }
    };
    public static final Parser<java.net.URL> URL = new Parser<>("url") {

        @Override
        public Response<java.net.URL> parse(String[] string, int index) throws IllegalArgumentException {
            try {
                return new Response<>(new java.net.URL(string[index]), index);
            } catch (Exception e) {
                throw new IllegalArgumentException(getKey());
            }
        }

    };
    public static final Parser<String[]> STRING_ARRAY = new Parser<>("string_array") {


        @Override
        public Response<String[]> parse(String[] string, int index) throws IllegalArgumentException {
            String[] returnArray = new String[string.length - index];
            if (string.length - index >= 0)
                System.arraycopy(string, index, returnArray, 0, string.length - index);
            return new Response<>(returnArray, index);
        }
    };
    public static final Parser<Vector> VECTOR = new Parser<>("location") {



        @Override
        public Response<Vector> parse(String[] args, int index) throws IllegalArgumentException {
            Vector vec = new Vector();
            vec.setX(DOUBLE.parse(args, index).getObject());
            vec.setY(DOUBLE.parse(args, index + 1).getObject());
            vec.setZ(DOUBLE.parse(args, index + 2).getObject());
            return new Response<>(vec, index + 2);
        }
    };

    /**
     * Default parsers
     */
    public static HashMap<Class<?>, Parser<?>> newMap() {
        HashMap<Class<?>, Parser<?>> map = new HashMap<>();
        map.put(Player.class, PLAYER);
        map.put(String.class, STRING);
        map.put(UUID.class, UUID);
        map.put(Integer.class, INTEGER);
        map.put(Double.class, DOUBLE);
        map.put(Float.class, FLOAT);
        map.put(java.net.URL.class, URL);
        map.put(Vector.class, VECTOR);
        map.put(String[].class, STRING_ARRAY);
        return map;
    }

    public static void populateMessenger(Messenger messenger){
        populateIfPresent("player","The player is not online or it does not exits",messenger);
        populateIfPresent("string","Invalid String ???",messenger);
        populateIfPresent("uuid","Invalid UUID",messenger);
        populateIfPresent("integer","Invalid integer",messenger);
        populateIfPresent("double","Invalid double",messenger);
        populateIfPresent("float","Invalid float",messenger);
        populateIfPresent("url","Invalid url",messenger);
        populateIfPresent("location","Invalid location",messenger);
        populateIfPresent("string_array","Invalid string array ???",messenger);

    }
    private static void populateIfPresent(String key,String message,Messenger messenger){
        if(!messenger.containsKey(key)){
            messenger.addMessage(CommandManager.MSG_PREFIX+"parser_"+key,message);
        }
    }

    private final String key;
    public Parser(String key) {
        this.key = key;
    }


    /**
     * Key of the message to be sent
     *
     * @return the message to be sent.
     */
    public String getKey(){
        return CommandManager.MSG_PREFIX+"parser_"+key;
    }

    public abstract Response<U> parse(String[] string, int index) throws IllegalArgumentException;

    /**
     * This class is used for returning the object and a new index if the parsed object has multiple inputs
     */
    public static class Response<T> {

        private final T object;
        private final Integer index;

        public Response(T object, Integer index) {
            this.index = index;
            this.object = object;
        }

        public T getObject() {
            return object;
        }

        public Integer getIndex() {
            return index;
        }
    }
}
