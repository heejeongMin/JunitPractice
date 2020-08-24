package org.example.Wiki;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

public class WikiFetcher {
    private long lastRequestTime = -1L;
    private long minInterval = 1000L;

    public WikiFetcher() {
    }

    /* URL을 파싱하고 본문을 가져옴. 단락 요소의 리스트를 반환 */
    public Elements fetchWikipedia(String url) throws IOException {
        this.sleepIfNeeded();
        Connection conn = Jsoup.connect(url);
        Document doc = conn.get();
        Element content = doc.getElementById("mw-content-text");
        Elements paras = content.select("p");
        return paras;
    }

    public Elements readWikipedia(String url) throws IOException {
        URL realURL = new URL(url);
        String filename = "http://" + realURL.getHost() + realURL.getPath();
        InputStream stream = WikiFetcher.class.getClassLoader().getResourceAsStream(filename);
        Document doc = Jsoup.parse(stream, "UTF-8", filename);
        Element content = doc.getElementById("mw-content-text");
        Elements paras = content.select("p");
        return paras;
    }

    private void sleepIfNeeded() {
        if (this.lastRequestTime != -1L) {
            long currentTime = System.currentTimeMillis();
            long nextRequestTime = this.lastRequestTime + this.minInterval;
            if (currentTime < nextRequestTime) {
                try {
                    Thread.sleep(nextRequestTime - currentTime);
                } catch (InterruptedException var6) {
                    System.err.println("Warning: sleep interrupted in fetchWikipedia.");
                }
            }
        }

        this.lastRequestTime = System.currentTimeMillis();
    }

    public static void main(String[] args) throws IOException {
        WikiFetcher wf = new WikiFetcher();
        String url = "http://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.fetchWikipedia(url);
        Iterator var4 = paragraphs.iterator();

        while(var4.hasNext()) {
            Element paragraph = (Element)var4.next();
            System.out.println(paragraph);
        }

    }
}
