package org.example.Wiki;

import org.jsoup.nodes.Node;

import java.util.*;

public class WikiNodeIterable implements Iterable<Node> {
    private Node root;

    public WikiNodeIterable(Node root) {
        this.root = root;
    }

    public Iterator<Node> iterator() {

        return new WikiNodeIterable.WikiNodeIterator(this.root);
    }

    private class WikiNodeIterator implements Iterator<Node> {
        Deque<Node> stack = new ArrayDeque();

        public WikiNodeIterator(Node node) {

            this.stack.push(WikiNodeIterable.this.root);
        }

        public boolean hasNext() {

            return !this.stack.isEmpty();
        }

        public Node next() {
            if (this.stack.isEmpty()) {
                throw new NoSuchElementException();
            } else {
                Node node = (Node)this.stack.pop();
                List<Node> nodes = new ArrayList(node.childNodes());
                Collections.reverse(nodes);
                Iterator var3 = nodes.iterator();

                while(var3.hasNext()) {
                    Node child = (Node)var3.next();
                    this.stack.push(child);
                }

                return node;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}