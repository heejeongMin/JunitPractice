package org.example.Wiki;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * 트리를 탐색하는 방법
 * 1. 깊이 우선 탐색 - Depth First Search (DFS)
 *      : 트리의 루트에서 시작하여 첫 번째 자식 노드를 선택
 *        1) 자식이 자식을 가지고 있으면 첫 번째 자식을 다시 선택
 *        2) 자식이 없는 노드에 도착하면 부모노드 선택
 *        3) 부모노드에 다음 자식이 있다면 그 자식 노드로 이동
 *        4) 다음 자식이 없는 경우 거슬러 올라감
 *        5) 루트의 마짐가 노드까지 탐색하면 종료
 *
 *  2. 스택
 *      1) LIFO (Last In, First Out 후입선출)
 *      2) 리스트를 사용하여 스택을 구현하면 우연히 잘못된 순서로 요소를 제거할수 있음.
 *         스택은 리스트보다 작은 메서드를 제공하는데 이는 코드의 가독성이 높아지고 오류 발생 가능성은 낮춘다.
 *      3) 자바로 스택을 구현하는 세가지 선택사항
 *          a. ArrayList/LinkedList 클래스 : 요소의 끝에 넣고 제거. 단 잘못된 위치에 요소룰 추가하거나 잘못된 순서로 제거하지 않도록 주의
 *          b. Stack 클래스 : 표준 구현. 단 오래된 자바 버전이어서 이후에 나온 JCF와 일치하지 않음 (Java Collection Framework)
 *          c. ArrayDeque/Deque 인터페이스 : Deque는 양쪽에 끝이 있는 큐
 */
public class WikiNodeExample {
    public WikiNodeExample() {
    }

    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        //문서를 다운로드 하고 파싱하기
        Connection conn = Jsoup.connect(url);//String url을 받아 웹서버 접속
        Document doc = conn.get(); //get메서드가 html을 다운로드하여 파싱하고 DOM 트리를 나타내는 Document 객체 반환

        //내용을 선택하고 단락 추출하기
        Element content = doc.getElementById("mw-content-text");
        Elements paras = content.select("p");// String인자와 일치하는 모든 요소반환
        Element firstPara = (Element)paras.get(1);

//        recursiveDFS(firstPara);
//        System.out.println();
//        iterativeDFS(firstPara);
//        System.out.println();
        Iterable<Node> iter = new WikiNodeIterable(firstPara);//WikiNodeItarable은 깊이우선탐색으로 나타나는 순서대로 출력
        for(Node node : iter){
            if (node instanceof TextNode){
                System.out.println(node);
            }
        }

//        Iterator var8 = iter.iterator();
//
//        while(var8.hasNext()) {
//            Node node = (Node)var8.next();
//            if (node instanceof TextNode) {
//                System.out.print(node);
//            }
//        }

    }

    /* DFS를 구현하는 방법 2. 반복적 방법*/
    /* Deque와 Iterator 사용
    *   - Deque를 구현하는 클래스 : LinkedList, ArrayDeque
    *     : LinkedList클래스는 List와 Deque모두 구현. 할당하는 인터페이스에따른 메서드만 사용가능
    *     : Deque<Node> deque = new LinkedList<Node>(); -> Deque 메서드만 사용
    *     : List<Node> deque = new LinkedList<Node>(); -> List 메서드만 사용
    *     : LinkedList<Node> deque = new Linked<Node>(); -> 모든 메서드 호출 가능하지만 서로 다른 인터페이스가 혼합되어 가독성이 떨어짐
    *
    *
    * */
    private static void iterativeDFS(Node root) {
        Deque<Node> stack = new ArrayDeque();
        stack.push(root);

        while(!stack.isEmpty()) {
            Node node = (Node)stack.pop();
            if (node instanceof TextNode) {
                System.out.print(node); //자기자신
            }

            List<Node> nodes = new ArrayList(node.childNodes()); //자식노드
            Collections.reverse(nodes);
            Iterator var4 = nodes.iterator();

            while(var4.hasNext()) {
                Node child = (Node)var4.next();
                stack.push(child); //결국 제일 첫 자식이 맨 위에 들어가게되는 구조
            }
        }

    }

    /* DFS를 구현하는 방법 1. 재귀적 방법*/
    /* 이 예제는 자식 노드를 탐색하기에 앞서 각 TextNode의 내용을 출력 --> 전위 순회
      (순회 순서 : 전위(pre-order), 후위(post-order), 중위(in-order)
      참고 이진트리 순회: http://thinkdast.com/treetrav
      참고 호출 스택 : http://thinkdast.com/callstack
    * */
    private static void recursiveDFS(Node node) {
        if (node instanceof TextNode) {
            System.out.print(node);
        }

        Iterator var1 = node.childNodes().iterator();

        while(var1.hasNext()) {
            Node child = (Node)var1.next(); //노드에 자식이 있으면 재귀
            recursiveDFS(child);
        }

    }


}
