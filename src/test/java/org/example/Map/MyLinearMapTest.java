package org.example.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MyLinearMapTest {
    protected Map<String, Integer> map;

    public MyLinearMapTest() {
    }

    @Before
    public void setUp() throws Exception {
        this.map = new MyLinearMap();
        this.map.put("One", 1);
        this.map.put("Two", 2);
        this.map.put("Three", 3);
        this.map.put((String) null, 0);
    }

    @Test
    public void testClear() {
        this.map.clear();
        Assert.assertThat(this.map.size(), CoreMatchers.is(0));
    }

    @Test
    public void testContainsKey() {
        Assert.assertThat(this.map.containsKey("Three"), CoreMatchers.is(true));
        Assert.assertThat(this.map.containsKey((Object)null), CoreMatchers.is(true));
        Assert.assertThat(this.map.containsKey("Four"), CoreMatchers.is(false));
    }

    @Test
    public void testContainsValue() {
        Assert.assertThat(this.map.containsValue(3), CoreMatchers.is(true));
        Assert.assertThat(this.map.containsValue(0), CoreMatchers.is(true));
        Assert.assertThat(this.map.containsValue(4), CoreMatchers.is(false));
    }

    @Test
    public void testGet() {
        Assert.assertThat((Integer)this.map.get("Three"), CoreMatchers.is(3));
        Assert.assertThat((Integer)this.map.get((Object)null), CoreMatchers.is(0));
        Assert.assertThat((Integer)this.map.get("Four"), CoreMatchers.nullValue());
    }

    @Test
    public void testIsEmpty() {
        Assert.assertThat(this.map.isEmpty(), CoreMatchers.is(false));
        this.map.clear();
        Assert.assertThat(this.map.isEmpty(), CoreMatchers.is(true));
    }

    @Test
    public void testKeySet() {
        Set<String> keySet = this.map.keySet();
        Assert.assertThat(keySet.size(), CoreMatchers.is(4));
        Assert.assertThat(keySet.contains("Three"), CoreMatchers.is(true));
        Assert.assertThat(keySet.contains((Object)null), CoreMatchers.is(true));
        Assert.assertThat(keySet.contains("Four"), CoreMatchers.is(false));
    }

    @Test
    public void testPut() {
        this.map.put("One", 11);
        Assert.assertThat(this.map.size(), CoreMatchers.is(4));
        Assert.assertThat((Integer)this.map.get("One"), CoreMatchers.is(11));
        this.map.put("Five", 5);
        Assert.assertThat(this.map.size(), CoreMatchers.is(5));
        Assert.assertThat((Integer)this.map.get("Five"), CoreMatchers.is(5));
    }

    @Test
    public void testPutAll() {
        Map<String, Integer> m = new HashMap();
        m.put("Six", 6);
        m.put("Seven", 7);
        m.put("Eight", 8);
        this.map.putAll(m);
        Assert.assertThat(this.map.size(), CoreMatchers.is(7));
    }

    @Test
    public void testRemove() {
        this.map.remove("One");
        Assert.assertThat(this.map.size(), CoreMatchers.is(3));
        Assert.assertThat((Integer)this.map.get("One"), CoreMatchers.nullValue());
    }

    @Test
    public void testSize() {
        Assert.assertThat(this.map.size(), CoreMatchers.is(4));
    }

    @Test
    public void testValues() {
        Collection<Integer> keySet = this.map.values();
        Assert.assertThat(keySet.size(), CoreMatchers.is(4));
        Assert.assertThat(keySet.contains(3), CoreMatchers.is(true));
        Assert.assertThat(keySet.contains(0), CoreMatchers.is(true));
        Assert.assertThat(keySet.contains(4), CoreMatchers.is(false));
    }

}