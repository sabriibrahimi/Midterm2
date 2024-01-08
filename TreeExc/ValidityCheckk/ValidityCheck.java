package TreeExc.ValidityCheckk;


//To write a Validitcheck function that will check if for a given tree it is true
// that for each node its children have an info-field with a lower equal value than
// the value of the info-field of that node and, depending on that, to print true or false.

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

class BNode<E extends Comparable<E>> {

    public E info;
    public BNode<E> left;
    public BNode<E> right;

    static int LEFT = 1;
    static int RIGHT = 2;

    public BNode(E info) {
        this.info = info;
        left = null;
        right = null;
    }

    public BNode() {
        this.info = null;
        left = null;
        right = null;
    }

    public BNode(E info, BNode<E> left, BNode<E> right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

}

class BTree<E extends Comparable<E>> {
    public BNode<E> root;

    public BTree() {
        root = null;
    }

    public BTree(E info) {
        root = new BNode<E>(info);
    }

    public void makeRoot(E elem) {
        root = new BNode<E>(elem);
    }

    public void makeRootNode(BNode<E> node) {
        root = node;
    }

    public BNode<E> addChild(BNode<E> node, int where, E elem) {
        BNode<E> tmp = new BNode<E>(elem);

        if (where == BNode.LEFT) {
            if (node.left != null)
                return null;
            node.left = tmp;
        } else {
            if (node.right != null)
                return null;
            node.right = tmp;
        }
        return tmp;
    }

    public BNode<E> addChildNode(BNode<E> node, int where, BNode<E> tmp) {
        if (where == BNode.LEFT) {
            if (node.left != null)
                return null;
            node.left = tmp;
        } else {
            if (node.right != null)
                return null;
            node.right = tmp;
        }
        return tmp;
    }

    private boolean validityCheckRecursive(BNode<E> node) {
        if (node == null) return true;
        if (node.left == null && node.right == null) return true;

        if (node.left == null) {
            if (node.right.info.compareTo(node.info) <= 0)
                return validityCheckRecursive(node.right);
            else return false;
        }

        if (node.right == null) {
            if (node.left.info.compareTo(node.info) <= 0)
                return validityCheckRecursive(node.left);
            else return false;
        }

        if (node.left.info.compareTo(node.info) <= 0 &&
                node.right.info.compareTo(node.info) <= 0)
            return validityCheckRecursive(node.left) && validityCheckRecursive(node.right);


        return false;
    }

    public boolean validityCheck() {
        return validityCheckRecursive(root);
    }
}


public class ValidityCheck {
    public static void main(String[] args) throws Exception {
        int i, j, k;
        int index;
        String action;
        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        BNode<Integer> nodes[] = new BNode[N];
        BTree<Integer> tree = new BTree<Integer>();

        for (i = 0; i < N; i++)
            nodes[i] = new BNode<Integer>();

        for (i = 0; i < N; i++) {
            line = br.readLine();
            st = new StringTokenizer(line);
            index = Integer.parseInt(st.nextToken());
            nodes[index].info = Integer.parseInt(st.nextToken());
            action = st.nextToken();
            if (action.equals("LEFT")) {
                tree.addChildNode(nodes[Integer.parseInt(st.nextToken())], BNode.LEFT, nodes[index]);
            } else if (action.equals("RIGHT")) {
                tree.addChildNode(nodes[Integer.parseInt(st.nextToken())], BNode.RIGHT, nodes[index]);
            } else {
                tree.makeRootNode(nodes[index]);
            }
        }
        System.out.println(tree.validityCheck());
        br.close();
    }
}
