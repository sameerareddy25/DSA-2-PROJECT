public class SensorSegmentTree {

    static int[] arr = {71, 73, 78, 75, 82, 79, 77, 80};
    static int[] tree = new int[32];

    // Build Segment Tree
    static void build(int node, int start, int end) {

        if (start == end) {
            tree[node] = arr[start];
            return;
        }

        int mid = (start + end) / 2;

        build(2 * node, start, mid);
        build(2 * node + 1, mid + 1, end);

        tree[node] = Math.max(tree[2 * node],
                              tree[2 * node + 1]);
    }

    // Range Maximum Query
    static int queryMax(int node, int start, int end,
                        int left, int right) {

        // No overlap
        if (right < start || end < left)
            return Integer.MIN_VALUE;

        // Complete overlap
        if (left <= start && end <= right)
            return tree[node];

        int mid = (start + end) / 2;

        int maxLeft = queryMax(
                2 * node, start, mid,
                left, right);

        int maxRight = queryMax(
                2 * node + 1, mid + 1, end,
                left, right);

        return Math.max(maxLeft, maxRight);
    }

    // Point Update
    static void update(int node, int start, int end,
                       int index, int value) {

        if (start == end) {
            arr[index] = value;
            tree[node] = value;
            return;
        }

        int mid = (start + end) / 2;

        if (index <= mid)
            update(2 * node, start, mid,
                   index, value);
        else
            update(2 * node + 1, mid + 1, end,
                   index, value);

        tree[node] = Math.max(tree[2 * node],
                              tree[2 * node + 1]);
    }

    public static void main(String[] args) {

        int n = arr.length;

        build(1, 0, n - 1);

        // Query [3..7]
        int result1 =
            queryMax(1, 0, n - 1, 2, 6);

        System.out.println("Range Max [3..7] = "
                           + result1);

        // Update i = 4 (75 -> 88)
        update(1, 0, n - 1, 3, 88);

        // Query [2..6]
        int result2 =
            queryMax(1, 0, n - 1, 1, 5);

        System.out.println("Range Max [2..6] = "
                           + result2);
    }
}