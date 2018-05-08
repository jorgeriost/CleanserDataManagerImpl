package com.taiger.nlp.cleanser.model;

public class Pair<K, V> {
	
	private final K key;
    private final V value;

    public static <K, V> Pair<K, V> createPair(K key, V value) {
        return new Pair<K, V>(key, value);
    }

    public Pair(K element0, V element1) {
        this.key = element0;
        this.value = element1;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
