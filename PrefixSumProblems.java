import java.util.*;

public class PrefixSumProblems {

    // 1. Running sum
    static int[] runningSum(int[] arr) {
        int[] res = new int[arr.length];
        int run = 0;
        for (int i = 0; i < arr.length; i++) {
            run += arr[i];
            res[i] = run;
        }
        return res;
    }

    // 2. Pivot index
    static int pivotIndex(int[] arr) {
        int total = 0, left = 0;
        for (int x : arr) total += x;
        for (int i = 0; i < arr.length; i++) {
            if (left == total - left - arr[i]) return i;
            left += arr[i];
        }
        return -1;
    }

    // 3. Absolute difference of left and right sums
    static int[] leftRightDiff(int[] arr) {
        int total = 0, left = 0;
        for (int x : arr) total += x;
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = Math.abs(left - (total - left - arr[i]));
            left += arr[i];
        }
        return res;
    }

    // 4. Range sum queries
    static int[] rangeSums(int[] arr, int[][] queries) {
        int[] pre = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) pre[i + 1] = pre[i] + arr[i];
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            res[i] = pre[queries[i][1] + 1] - pre[queries[i][0]];
        }
        return res;
    }

    // 5. Highest altitude
    static int highestAltitude(int[] arr) {
        int alt = 0, best = 0;
        for (int x : arr) {
            alt += x;
            best = Math.max(best, alt);
        }
        return best;
    }

    // 6. Product except self (no division)
    static int[] productExceptSelf(int[] arr) {
        int n = arr.length;
        int[] res = new int[n];
        int left = 1;
        for (int i = 0; i < n; i++) {
            res[i] = left;
            left *= arr[i];
        }
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            res[i] *= right;
            right *= arr[i];
        }
        return res;
    }

    // 7. Maximum prefix sum (non-empty)
    static int maxPrefixSum(int[] arr) {
        int run = 0, best = Integer.MIN_VALUE;
        for (int x : arr) {
            run += x;
            best = Math.max(best, run);
        }
        return best;
    }

    // 8. Sum of all subarray sums
    static long sumOfSubarraySums(int[] arr) {
        int n = arr.length;
        long total = 0;
        for (int i = 0; i < n; i++) {
            total += (long) arr[i] * (i + 1) * (n - i);
        }
        return total;
    }

    // 9. Split into two non-empty contiguous parts with equal sum
    static boolean canSplit(int[] arr) {
        long total = 0, run = 0;
        for (int x : arr) total += x;
        for (int i = 0; i < arr.length - 1; i++) {   // keep right part non-empty
            run += arr[i];
            if (run * 2 == total) return true;
        }
        return false;
    }

    // 10. Count subarrays with sum == k
    static int countSubarraysSumK(int[] arr, int k) {
        Map<Integer, Integer> seen = new HashMap<>();
        seen.put(0, 1);
        int run = 0, count = 0;
        for (int x : arr) {
            run += x;
            count += seen.getOrDefault(run - k, 0);
            seen.merge(run, 1, Integer::sum);
        }
        return count;
    }

    // 11. Longest subarray with sum == k
    static int longestSubarraySumK(int[] arr, int k) {
        Map<Integer, Integer> first = new HashMap<>();
        first.put(0, -1);
        int run = 0, best = 0;
        for (int i = 0; i < arr.length; i++) {
            run += arr[i];
            if (first.containsKey(run - k)) {
                best = Math.max(best, i - first.get(run - k));
            }
            first.putIfAbsent(run, i);               // keep earliest index
        }
        return best;
    }

    // 12. Count subarrays with sum divisible by k
    static int countDivisibleByK(int[] arr, int k) {
        int[] seen = new int[k];
        seen[0] = 1;
        int run = 0, count = 0;
        for (int x : arr) {
            run = ((run + x) % k + k) % k;           // normalize negatives
            count += seen[run];
            seen[run]++;
        }
        return count;
    }

    // 13. Count subarrays with odd sum
    static long countOddSumSubarrays(int[] arr) {
        long even = 1, odd = 0;                      // empty prefix is even
        int parity = 0;
        for (int x : arr) {
            parity ^= (x & 1);
            if (parity == 1) odd++;
            else even++;
        }
        return even * odd;
    }

    // 14. 2D prefix sum rectangle query
    static class MatrixSum {
        private final int[][] p;

        MatrixSum(int[][] m) {
            int R = m.length, C = m[0].length;
            p = new int[R + 1][C + 1];
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    p[i + 1][j + 1] = m[i][j] + p[i][j + 1] + p[i + 1][j] - p[i][j];
                }
            }
        }

        int query(int r1, int c1, int r2, int c2) {
            return p[r2 + 1][c2 + 1] - p[r1][c2 + 1] - p[r2 + 1][c1] + p[r1][c1];
        }
    }

    // 15. Max sum rectangle in a matrix
    static int maxSumSubmatrix(int[][] matrix) {
        int R = matrix.length, C = matrix[0].length;
        int best = Integer.MIN_VALUE;
        for (int top = 0; top < R; top++) {
            int[] cols = new int[C];
            for (int bottom = top; bottom < R; bottom++) {
                for (int c = 0; c < C; c++) cols[c] += matrix[bottom][c];
                int cur = cols[0];                   // Kadane on collapsed rows
                best = Math.max(best, cur);
                for (int c = 1; c < C; c++) {
                    cur = Math.max(cols[c], cur + cols[c]);
                    best = Math.max(best, cur);
                }
            }
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(runningSum(new int[]{1, 2, 3, 4})));            // [1, 3, 6, 10]
        System.out.println(pivotIndex(new int[]{1, 3, 5, 2, 2}));                          // 2
        System.out.println(Arrays.toString(leftRightDiff(new int[]{10, 4, 8, 3})));        // [15, 1, 11, 22]
        System.out.println(Arrays.toString(
                rangeSums(new int[]{2, 4, 6, 8, 10}, new int[][]{{1, 3}, {0, 2}})));       // [18, 12]
        System.out.println(highestAltitude(new int[]{-5, 1, 5, 0, -7}));                   // 1
        System.out.println(Arrays.toString(productExceptSelf(new int[]{1, 2, 3, 4})));     // [24, 12, 8, 6]
        System.out.println(maxPrefixSum(new int[]{-2, 3, 5, -1, 4}));                      // 9
        System.out.println(sumOfSubarraySums(new int[]{1, 2, 3}));                         // 20
        System.out.println(canSplit(new int[]{1, 2, 3, 3}));                               // true
        System.out.println(countSubarraysSumK(new int[]{1, 2, 3}, 3));                     // 2
        System.out.println(longestSubarraySumK(new int[]{10, 5, 2, 7, 1, 9}, 15));         // 4
        System.out.println(countDivisibleByK(new int[]{4, 5, 0, -2, -3, 1}, 5));           // 7
        System.out.println(countOddSumSubarrays(new int[]{1, 2, 3}));                      // 4

        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(new MatrixSum(m).query(0, 0, 1, 1));                            // 12
        System.out.println(maxSumSubmatrix(new int[][]{{1, 2, -1}, {-3, 4, 5}, {2, -1, 3}})); // 12
    }
}