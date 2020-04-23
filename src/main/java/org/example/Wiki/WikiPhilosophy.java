package org.example.Wiki;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WikiPhilosophy {
    static final List<String> visited = new ArrayList();
    static final WikiFetcher wf = new WikiFetcher();

    public WikiPhilosophy() {
    }

    public static void main(String[] args) throws IOException {
        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        testConjecture(destination, source, 10);
    }

    static List<String> urlList = new ArrayList<>();

    public static void testConjecture(String destination, String source, int limit) throws IOException {
        WikiFetcher wikiFetcher = new WikiFetcher();


        String url = "";
        while (limit > 0) {
            Elements elements = wikiFetcher.fetchWikipedia(source);
            for (int i = 0; i < elements.size(); i++) {
                Element ele = (Element) elements.get(i);
                url = recursiveDFS(ele);
                if (url.length() > 0) break;
            }
            System.out.println(url);
            source = "https://en.wikipedia.org" + url;
            url = "";
            limit--;
        }


    }

    public static String recursiveDFS(Node node) {
        String link = "";
        if (node.nodeName().equals("a")) {
            if ("i,em".indexOf(node.parentNode().nodeName()) == -1) { // 이탈릭체 아니고
                String url = node.attr("href");
                if (url.startsWith("/wiki")) {
                    if (!url.contains("(")) { //괄호를 포함하지 않고
                        if (!urlList.contains(url)) { //중복된 리스트가 아니고
                            Node child = node.childNodes().get(0);
                            if(child instanceof TextNode){
                                if(((TextNode) child).text().charAt(0) >=97){
                                    urlList.add(url);
                                    link = url;
                                    if (link.length() > 0) return link;
                                }
                            }
                        }
                    }
                }
            }
        }

        Iterator iter = node.childNodes().iterator();
        while (iter.hasNext()) {
            Node child = (Node) iter.next();
            link = recursiveDFS(child);
            if (link.length() > 0) return link;
        }
        return link;
    }

}
