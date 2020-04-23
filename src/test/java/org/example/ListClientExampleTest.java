package org.example;

import org.example.List.ListClientExample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

class ListClientExampleTest {

    @Before
    void setUp() {
    }

    @After
    void tearDown() {
    }

    @Test
    void getList() {
    }

    @Test
    void main() {
        ListClientExample lce = new ListClientExample();
        List list = lce.getList();
        System.out.println("test");

    }
}