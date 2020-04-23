package org.example.Map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyLinearMap<K, V> implements Map<K, V> {
    private List<MyLinearMap<K, V>.Entry> entries = new ArrayList();

    public MyLinearMap() {
    }

    public void clear() {
        this.entries.clear();
    }

    public boolean containsKey(Object target) {
        return this.findEntry(target) != null;
    }

    /*
       1. 타깃 키가 주어지면 엔트리를 검색하고
       2. 타킷을 키로 포함하는 엔트리를 반환하거나
       3. 못찾으면 null을 반환
     */
    private MyLinearMap<K, V>.Entry findEntry(Object target){
            for(Entry e : this.entries){
                if(this.equals(target,e.key)) {
                    return e;
                }
            }
        return null;
    }

    private boolean equals(Object target, Object obj) {
        if (target == null) {
            return obj == null;
        } else {
            return target.equals(obj);
        }
    }

    public boolean containsValue(Object target) {
        Iterator var2 = this.entries.iterator();

        java.util.Map.Entry entry;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            entry = (java.util.Map.Entry) var2.next();
        } while (!this.equals(target, entry.getValue()));

        return true;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    public V get(Object key) {
        Entry entry = findEntry(key);
        return (entry == null)? null : entry.getValue();
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet();
        Iterator var2 = this.entries.iterator();

        while (var2.hasNext()) {
            MyLinearMap<K, V>.Entry entry = (MyLinearMap.Entry) var2.next();
            set.add(entry.getKey());
        }

        return set;
    }

    public V put(K key, V value) {
        Entry entry = findEntry(key);
        if(entry == null){
            entries.add(new Entry(key, value));
            return null;
        } else {
            V oldVal = entry.getValue();
            entry.setValue(value);
            return oldVal;
        }
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        Iterator var2 = map.entrySet().iterator();

        while (var2.hasNext()) {
            java.util.Map.Entry<? extends K, ? extends V> entry = (java.util.Map.Entry) var2.next();
            this.put(entry.getKey(), entry.getValue());
        }

    }

    /* returns a value previously associated with the key */
    public V remove(Object key) {
        Entry entry = findEntry(key);
        if(entry == null){
            return null;
        } else {
            this.entries.remove(entry);
            return entry.getValue();
        }
    }

    public int size() {
        return this.entries.size();
    }

    public Collection<V> values() {
        Set<V> set = new HashSet();
        Iterator var2 = this.entries.iterator();

        while (var2.hasNext()) {
            MyLinearMap<K, V>.Entry entry = (MyLinearMap.Entry) var2.next();
            set.add(entry.getValue());
        }

        return set;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new MyLinearMap();
        map.put("Word1", 1);
        map.put("Word2", 2);
        Integer value = (Integer) map.get("Word1");
        System.out.println(value);
        Iterator var3 = map.keySet().iterator();

        while (var3.hasNext()) {
            String key = (String) var3.next();
            System.out.println(key + ", " + map.get(key));
        }

    }

    protected Collection<? extends java.util.Map.Entry<K, V>> getEntries() {
        return this.entries;
    }

    public class Entry implements java.util.Map.Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public V setValue(V newValue) {
            this.value = newValue;
            return this.value;
        }
    }
}
