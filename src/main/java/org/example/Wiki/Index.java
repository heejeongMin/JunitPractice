package org.example.Wiki;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Index {
    private Map<String, Set<TermCounter>> index = new HashMap();

    public Index() {
    }

    public void add(String term, TermCounter tc) {
        Set<TermCounter> set = this.get(term);
        if (set == null) {
            set = new HashSet();
            this.index.put(term, set);
        }

        ((Set)set).add(tc);
    }

    public Set<TermCounter> get(String term) {
        return (Set)this.index.get(term);
    }

    public void printIndex() {
        Iterator var1 = this.keySet().iterator();

        while(var1.hasNext()) {
            String term = (String)var1.next();
            System.out.println(term);
            Set<TermCounter> tcs = this.get(term);
            Iterator var4 = tcs.iterator();

            while(var4.hasNext()) {
                TermCounter tc = (TermCounter)var4.next();
                Integer count = tc.get(term);
                System.out.println("    " + tc.getLabel() + " " + count);
            }
        }

    }

    public Set<String> keySet() {
        return this.index.keySet();
    }

    public void indexPage(String url, Elements paragraphs) {
        //TermCounter 객체를 만들고 단락에 있는 단어를 셉니다.
        TermCounter tc = new TermCounter(url);
        tc.processElements(paragraphs);

        //TermCounter에 있는 각 검색어에 대해 TermCounter 객체를 인덱스에 추가합니다.
        Set<String> keys = tc.keySet();
        for(String key : keys) {
            this.add(key, tc);
        }

    }

    public static void main(String[] args) throws IOException {
        WikiFetcher wf = new WikiFetcher();
        Index indexer = new Index();
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.fetchWikipedia(url);
        indexer.indexPage(url, paragraphs);
        url = "https://en.wikipedia.org/wiki/Programming_language";
        paragraphs = wf.fetchWikipedia(url);
        indexer.indexPage(url, paragraphs);
        indexer.printIndex();
    }
}
