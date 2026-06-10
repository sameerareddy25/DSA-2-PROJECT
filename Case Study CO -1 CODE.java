class RankNode {
    int key, height, size;
    RankNode left, right;

    RankNode(int key) {
        this.key = key;
        height = 1;
        size = 1;
    }
}

public class QuizClashAVL {

    static int height(RankNode n) {
        return (n == null) ? 0 : n.height;
    }

    static int size(RankNode n) {
        return (n == null) ? 0 : n.size;
    }

    static void update(RankNode n) {
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
            n.size = 1 + size(n.left) + size(n.right);
        }
    }

    static int balanceFactor(RankNode n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    // Right Rotation
    static RankNode rightRotate(RankNode y) {
        RankNode x = y.left;
        RankNode t2 = x.right;

        x.right = y;
        y.left = t2;

        update(y);
        update(x);

        return x;
    }

    // Left Rotation
    static RankNode leftRotate(RankNode x) {
        RankNode y = x.right;
        RankNode t2 = y.left;

        y.left = x;
        x.right = t2;

        update(x);
        update(y);

        return y;
    }

    // Insert Node
    static RankNode insert(RankNode root, int key) {

        if (root == null)
            return new RankNode(key);

        if (key < root.key)
            root.left = insert(root.left, key);

        else if (key > root.key)
            root.right = insert(root.right, key);

        else
            return root;

        update(root);

        int bf = balanceFactor(root);

        // LL
        if (bf > 1 && key < root.left.key)
            return rightRotate(root);

        // RR
        if (bf < -1 && key > root.right.key)
            return leftRotate(root);

        // LR
        if (bf > 1 && key > root.left.key) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RL
        if (bf < -1 && key < root.right.key) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    static RankNode minValueNode(RankNode node) {
        RankNode current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    // Delete Node
    static RankNode delete(RankNode root, int key) {

        if (root == null)
            return null;

        if (key < root.key)
            root.left = delete(root.left, key);

        else if (key > root.key)
            root.right = delete(root.right, key);

        else {

            if (root.left == null)
                return root.right;

            if (root.right == null)
                return root.left;

            RankNode temp = minValueNode(root.right);

            root.key = temp.key;

            root.right = delete(root.right, temp.key);
        }

        update(root);

        int bf = balanceFactor(root);

        // LL
        if (bf > 1 && balanceFactor(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (bf > 1 && balanceFactor(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (bf < -1 && balanceFactor(root.right) <= 0)
            return leftRotate(root);

        // RL
        if (bf < -1 && balanceFactor(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Rank Query (Highest Score = Rank 1)
    static int rankOf(RankNode root, int key) {

        int rank = 1;

        while (root != null) {

            if (key == root.key) {
                rank += size(root.right);
                return rank;
            }

            if (key < root.key) {
                rank += size(root.right) + 1;
                root = root.left;
            } else {
                root = root.right;
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        int scores[] = {
            820, 540, 910, 770, 880,
            460, 990, 600, 730,
            950, 510
        };

        RankNode root = null;

        for (int score : scores)
            root = insert(root, score);

        System.out.println("Rank of 770 = " +
                rankOf(root, 770));

        // Update 1
        root = delete(root, 540);
        root = insert(root, 815);

        // Update 2
        root = delete(root, 910);
        root = insert(root, 685);

        System.out.println("Rank of 685 = " +
                rankOf(root, 685));
    }
}