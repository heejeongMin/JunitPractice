package org.example.Redis;

import redis.clients.jedis.Jedis;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 레디스는 기본으로 String 타입의 키와 다양한 데이터 타입 중 하나를 값으로 넣을 수 있는 맵구조
 *
 * 자바의 Set<String> 과 유사한 set 구조를 지원. set이 존재하지 않으면 redis가 생성
 *   - jedis.sadd("set이름", 요소1, 요소2, ...) : set 구조에 데이터 추가
 *   - jedis.sismember("set이름", 요소1) : set에 요소가 있는지 검사
 *
 * 자바의 List<String> 과 유사한 list 구조를 지원.list가 존재하지 않으면 redis가 생성
 *   - jedis.rpush(list이름, 요소1, 요소2, ...) : 요소의 오른쪽 끝에 추가가
 *   - jedis.lindex(list이름, 인덱스) : 정수 인덱스를 받아 list에 지정된 요소를 반환
 *
 * 자바의 Map<String, String>과 유사한 hash 구조 지원. key, value는 모두 string만 지원
 *   - jedis.hset(hash이름, key, value) : hash에 새로운 엔트리 추가
 *   - jedis.hget(hash이름, key) : hash에 값 조회
 *   - jedis.hincrBy(hash이름, key, 정수) : 지정한 hash이름을 가진 hash에서 key의 현재
 *                                         값을 가져와(없으면 0) 1만큼 증가시키도 다시 저장
 */

 public class JedisMaker {

    /**
     * Make a Jedis object and authenticate it.
     *
     * @return
     * @throws IOException
     */
    public static Jedis make() throws IOException {
        // connect to the server
        Jedis jedis = new Jedis("localhost", 6379);
        return jedis;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Jedis jedis = make();

        // String
        jedis.set("mykey", "myvalue");
        String value = jedis.get("mykey");
        System.out.println("Got value: " + value);

        // Set
        jedis.sadd("myset", "element1", "element2", "element3");
        System.out.println("element2 is member: " + jedis.sismember("myset", "element2"));

        // List
        jedis.rpush("mylist", "element1", "element2", "element3");
        System.out.println("element at index 1: " + jedis.lindex("mylist", 1));

        // Hash
        jedis.hset("myhash", "word1", Integer.toString(2));
        jedis.hincrBy("myhash", "word2", 1);
        System.out.println("frequency of word1: " + jedis.hget("myhash", "word1"));
        System.out.println("frequency of word2: " + jedis.hget("myhash", "word2"));

        jedis.close();
    }
}

