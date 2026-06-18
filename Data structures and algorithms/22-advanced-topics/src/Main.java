public class Main {
    static class SegmentTree {
        int n;
        int[] tree;

        SegmentTree(int[] nums) {
            n = nums.length;
            tree = new int[n * 4];
            build(nums, 1, 0, n - 1);
        }

        void build(int[] nums, int node, int left, int right) {
            if (left == right) {
                tree[node] = nums[left];
                return;
            }

            int mid = left + (right - left) / 2;
            build(nums, node * 2, left, mid);
            build(nums, node * 2 + 1, mid + 1, right);
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }

        int query(int queryLeft, int queryRight) {
            return query(1, 0, n - 1, queryLeft, queryRight);
        }

        int query(int node, int left, int right, int queryLeft, int queryRight) {
            if (queryRight < left || right < queryLeft) return 0;
            if (queryLeft <= left && right <= queryRight) return tree[node];

            int mid = left + (right - left) / 2;
            return query(node * 2, left, mid, queryLeft, queryRight)
                    + query(node * 2 + 1, mid + 1, right, queryLeft, queryRight);
        }
    }

    public static void main(String[] args) {
        SegmentTree st = new SegmentTree(new int[]{1, 3, 5, 7, 9});
        System.out.println(st.query(1, 3));
    }
}

