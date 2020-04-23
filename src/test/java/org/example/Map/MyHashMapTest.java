package org.example.Map;

import org.junit.Before;

/**
 * @author downey
 *
 */
public class MyHashMapTest extends MyBetterMapTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        map = new MyHashMap<String, Integer>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put(null, 0);
    }
}