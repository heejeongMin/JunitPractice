package org.example.Wiki;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TermCounter {
    private Map<String, Integer> map;
    private String label;

    public TermCounter(String label) {
        this.label = label;
        this.map = new HashMap();
    }

    public String getLabel() {
        return this.label;
    }

    public int size() {
        return this.map.size();
    }

    public void processElements(Elements paragraphs) {
        Iterator var2 = paragraphs.iterator();

        while(var2.hasNext()) {
            Node node = (Node)var2.next();
            this.processTree(node);
        }

    }

    public void processTree(Node root) {
        Iterator var2 = (new WikiNodeIterable(root)).iterator();

        while(var2.hasNext()) {
            Node node = (Node)var2.next();
            if (node instanceof TextNode) {
                this.processText(((TextNode)node).text());
            }
        }

    }

    public void processText(String text) {
        String[] array = text.replaceAll("\\pP", " ").toLowerCase().split("\\s+");

        for(int i = 0; i < array.length; ++i) {
            String term = array[i];
            this.incrementTermCount(term);
        }

    }

    public void incrementTermCount(String term) {
        this.put(term, this.get(term) + 1);
    }

    public void put(String term, int count) {
        this.map.put(term, count);
    }

    public Integer get(String term) {
        Integer count = (Integer)this.map.get(term);
        return count == null ? 0 : count;
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    public void printCounts() {
        Iterator var1 = this.keySet().iterator();

        while(var1.hasNext()) {
            String key = (String)var1.next();
            Integer count = this.get(key);
            System.out.println(key + ", " + count);
        }

        System.out.println("Total of all counts = " + this.size());
    }

    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        WikiFetcher wf = new WikiFetcher();
        Elements paragraphs = wf.fetchWikipedia(url);
        TermCounter counter = new TermCounter(url.toString());
        counter.processElements(paragraphs);
        counter.printCounts();
    }
}
