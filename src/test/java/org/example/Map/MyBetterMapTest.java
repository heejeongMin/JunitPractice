package org.example.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyBetterMapTest extends MyLinearMapTest{

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        map = new MyBetterMap<String, Integer>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put(null, 0);
    }
}