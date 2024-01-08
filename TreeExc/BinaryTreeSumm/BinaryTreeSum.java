package TreeExc.BinaryTreeSumm;


//For a given binary tree, find the length of the longest consisting of consecutive nodes (parent-child, that is,
//a path moving from top to bottom in the hierarchy) with values in successively increasing order (the difference
//between a given predecessor and follower in the path is 1).
//
//The maximum length of a path with nodes in consecutive ascending order is 3 (for nodes: 10,11,12). Clarification:
//The path 10,11,13 is not taken into account because the puzzle between 13 and 11 is seen, which should be 1 to be
//considered a valid path.
//
//The maximum length of a path with consecutive nodes in ascending order is 2 (for node 8,9). Clarification: Path 5,8,9
//is not taken into account because it is seen that the puzzle between 8 and 5 is not equal to 1. Therefore, the path
//8,9 remains the only path in this binary tree with nodes in consecutive ascending order.


import java.io.BufferedReader;
import java.io.InputStreamReader;
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

}

class BTree<E extends Comparable<E>> {
    public BNode<E> root;

    public BTree() {
        root = null;
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


}

public class BinaryTreeSum {

    public static int maxPateka(BNode<Integer> node, int prevValue, int prevLen) {
        if (node == null)
            return prevLen;
        int curr = node.info;
        if (curr == prevValue + 1) {
            int levo = maxPateka(node.left, curr, prevLen + 1);
            int desno = maxPateka(node.right, curr, prevLen + 1);

            return Math.max(levo, desno);
        }

        int levo = maxPateka(node.left, curr, 1);
        int desno = maxPateka(node.right, curr, 1);
        int pateka = Math.max(levo, desno);

        return pateka;
    }

    public static int maxPatekaCall(BNode<Integer> root) {
        if (root == null)
            return 0;
        return maxPateka(root, root.info - 1, 0);
    }

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
        br.close();

        System.out.println(maxPatekaCall(tree.root));

    }
}

