package org.example.Map;



import java.util.*;


/* 자가 균형 트리
 * http://thinkdast.com/balancing
 *
 * 트리 중간에 있는 노드를 제거하면 나머지 노드가 BST 속성을 유지하도록 재배열
 * http;//thinkdast.com/bstdel
 */

/**
 * Implementation of a Map using a binary search tree.
 *
 * @param <K>
 * @param <V>
 */
public class MyTreeMap<K, V> implements Map<K, V> {

    private int size = 0;
    private Node root = null;

    /**
     * Represents a node in the tree.
     */
    protected class Node {
        public K key;
        public V value;
        public Node left = null;
        public Node right = null;

        /**
         * @param key
         * @param value
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(Object target) {
        return findNode(target) != null;
    }

    /**
     * Returns the entry that contains the target key, or null if there is none.
     *
     * @param target
     */
    private Node findNode(Object target) {
        // some implementations can handle null as a key, but not this one
        if (target == null) {
            throw new IllegalArgumentException();
        }

        // something to make the compiler happy
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) target; // Comparable 인터페이스를 구현한 어떤 타입과도 동작하고, 그 타입의 compareTo 메서드는 K 또는 K의 상위 타입까지 받는다.

        Node node = root;
        while(node != null) { // 전체 트리를 검색하지 않는 방법
            int comp = k.compareTo(node.key);

            if(comp < 0) {
                node = node.left;
            } else if (comp > 0) {
                node = node.right;
            } else {
                return node;
            }

        }

        /* 내가 푼거 */
//        Deque<Node> deque = new ArrayDeque<Node>();
//        deque.push(root);
//
//        Node n = null;
//        while (!deque.isEmpty()) {
//            n = deque.pop();
//            if (n == null) continue;
//            if (n.left != null) deque.push(n.left);
//            if (n.right != null) deque.push(n.right);
//
//            if (n.key.equals(target)) {
//                return n;
//            }
//        }

        return null; // 대상을 찾지 못하고 트리 끝가지 다 순회하면 트리에 없는 것으로 판단하고 NULL 반환
    }

    /**
     * Compares two keys or two values, handling null correctly.
     *
     * @param target
     * @param obj
     * @return
     */
    private boolean equals(Object target, Object obj) {
        if (target == null) {
            return obj == null;
        }
        return target.equals(obj);
    }

    @Override
    public boolean containsValue(Object target) {
        return containsValueHelper(root, target);
    }

    private boolean containsValueHelper(Node node, Object target) {
        if(node == null) { //노드가 null 이면 대상을 찾지 못하고 트리 끝에 다른것임. 이것은 오직 한쪽에만 없다는 의미고, 다른쪽에는 가능성이 있음
            return false;
        }

        if(equals(target, node.value)) { //원하는 것을 찾았는지 검사. 찾으면 true 못찾았으면 계속 찾기
            return true;
        }

        if(containsValueHelper(node.left, target)){ //왼쪽 하위 트리에서 target을 찾는 재귀 호출
            return true;
        }

        if(containsValueHelper(node.right, target)){ //오른쪽 하위 트리에서 target을 찾는 재귀 호출
            return true;
        }

          /* 내가 푼거 */
//        Set valueSet = (Set) values();
//        for (Object value : valueSet) {
//            if (value.equals(target)) {
//                return true;
//            }
//        }

        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        Node node = findNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    //BST 속성은 왼쪽 하위 트리의 모든 노드가 node.key 보다 작고, 오른쪽 하위 트리의 모든 노드는 node.key보다 큼을 보장하기때문에 가능.
    private void addInOrder(Node node, Set<K> set){//트리 중위 순회 in-order traversal
        if(node == null) return; //노드가 null이면 하위 트리가 비었다는 것을 의미함으로 set 변수에 아무것도 추가하지 않고 반환. 노드가 null이 아니면 다음을 수행

        addInOrder(node.left, set);//왼쪽 하위 트리를 순서대로 순회
        set.add(node.key); //node.key 추가
        addInOrder(node.right, set); //오른쪽 하위 트리를 순서대로 순회

    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<>();
        addInOrder(root, set);
        return set;

        /* 내가 푼거 */
//        Set<K> set = new TreeSet<>();
//        Deque<Node> stack = new LinkedList<Node>();
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            Node node = stack.pop();
//            if (node == null) continue;
//            set.add(node.key);
//
//            stack.push(node.left);
//            stack.push(node.right);
//        }
//        return set;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }
        return putHelper(root, key, value);
    }

    private V putHelper(Node node, K key, V value) {
        //1. 키가 트리에 이미 있다면 기존 값을 새로운 값으로 대체
        //2. key가 트리에 없으면 새로운 노드를 만들고 이 노드를 추가할 위치를 찾음 다음 null 반환

        Comparable<? super K> k = (Comparable<? super K>) key;
        int comp = k.compareTo(node.key); //compareTo 메서드를 호출하여 트리의 어느 경로를 따라 가야할지 정한다.

        if(comp < 0) { //처음 여기가 들어오면 계속 왼쪽 트리를 검색하게 되는데 어느 노드를 가던 왼쪽은 항상 작기 때문
            if(node.left == null) {
                node.left = new Node(key, value);
                size++;
                return null;
            } else {
                return putHelper(node.left, key, value);
            }
        }

        if(comp>0){
            if(node.right == null){
                node.right = new Node(key, value);
                size++;
                return null;
            } else {
                return putHelper(node.right, key, value);
            }
        }

        //이 경우는 comp == 0 으로 키를 찾은 경우로 기존 값은 대체 한다.
        V oldvalue = node.value;
        node.value = value;
        return oldvalue;

        /* 내가 푼거 */
//        Deque<Node> deque = new ArrayDeque();
//        deque.push(node);
//
//        boolean isExist = false;
//        Node n = null;
//        while (!deque.isEmpty()) {
//            n = deque.pop();
//            if (n == null) continue;
//            if (n.left != null) deque.push(n.left);
//            if (n.right != null) deque.push(n.right);
//
//            if (n.key.equals(key)) {
//                n.value = value;
//                isExist = true;
//                break;
//            }
//        }
//
//        if (!isExist) {
//            Node newNode = makeNode(key, value);
//            Comparable<? super K> k = (Comparable<? super K>) n.key;
//
//            if (k.compareTo(key) <= 0) {
//                n.left = newNode;
//            } else {
//                n.right = newNode;
//            }
//            size++;
//        }
//        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        // OPTIONAL TODO: FILL THIS IN!
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<V>();
        Deque<Node> stack = new LinkedList<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node == null) continue;
            set.add(node.value);
            stack.push(node.left);
            stack.push(node.right);
        }
        return set;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Integer> map = new MyTreeMap<String, Integer>();
        map.put("Word1", 1);
        map.put("Word2", 2);
        Integer value = map.get("Word1");
        System.out.println(value);

        for (String key : map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }
    }

    /**
     * Makes a node.
     * <p>
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @param key
     * @param value
     * @return
     */
    public MyTreeMap<K, V>.Node makeNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Sets the instance variables.
     * <p>
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @param node
     * @param size
     */
    public void setTree(Node node, int size) {
        this.root = node;
        this.size = size;
    }

    /**
     * Returns the height of the tree.
     * <p>
     * This is only here for testing purposes.  Should not be used otherwise.
     *
     * @return
     */
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node node) {
        if (node == null) {
            return 0;
        }
        int left = heightHelper(node.left);
        int right = heightHelper(node.right);
        return Math.max(left, right) + 1;
    }
}