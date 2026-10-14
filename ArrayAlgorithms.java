import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayAlgorithms {
    public static int[] countEvenOdd(int[] arr) {
        int even = 0;
        int odd = 0;
        for (int num : arr) {
            if (num % 2 == 0) even++;
            else odd++;
        }
        return new int[]{even, odd};
    }

    public static int maxConsecutiveOnes(int[] arr) {
        int count = 0;
        int maxCount = 0;
        for (int num : arr) {
            if (num == 1) {
                count++;
                maxCount = Math.max(maxCount, count);
            } else {
                count = 0;
            }
        }
        return maxCount;
    }

    public static int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }
        return maxProfit;
    }

    public static int maxDifference(int[] arr) {
        if (arr.length < 2) return 0;
        int minElement = arr[0];
        int maxDiff = arr[1] - arr[0];
        for (int i = 1; i < arr.length; i++) {
            maxDiff = Math.max(maxDiff, arr[i] - minElement);
            minElement = Math.min(minElement, arr[i]);
        }
        return maxDiff;
    }

    public static int missingNumber(int[] arr) {
        int n = arr.length;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        for (int num : arr) actualSum += num;
        return expectedSum - actualSum;
    }

    public static int firstRepeating(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        int result = -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (seen.contains(arr[i])) result = arr[i];
            else seen.add(arr[i]);
        }
        return result;
    }

    public static int equilibriumIndex(int[] arr) {
        int totalSum = 0;
        for (int num : arr) totalSum += num;
        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            totalSum -= arr[i];
            if (leftSum == totalSum) return i;
            leftSum += arr[i];
        }
        return -1;
    }

    public static int majorityElement(int[] arr) {
        int candidate = 0;
        int count = 0;
        for (int num : arr) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (candidate == num) {
                count++;
            } else {
                count--;
            }
        }
        count = 0;
        for (int num : arr) {
            if (num == candidate) count++;
        }
        return count > arr.length / 2 ? candidate : -1;
    }

    public static int maxSubArraySum(int[] arr) {
        if (arr.length == 0) return 0;
        int currentSum = arr[0];
        int maxSum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            currentSum = Math.max(arr[i], currentSum + arr[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println("Even/odd counts: " + Arrays.toString(countEvenOdd(new int[]{2, 4, 6, 8, 1, 3, 5})));
        System.out.println("Maximum consecutive ones: " + maxConsecutiveOnes(new int[]{1, 1, 1, 0, 1}));
        System.out.println("Maximum stock profit: " + maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println("Maximum difference: " + maxDifference(new int[]{2, 3, 10, 6, 4, 8, 1}));
        System.out.println("Missing number: " + missingNumber(new int[]{3, 0, 1}));
        System.out.println("First repeating element: " + firstRepeating(new int[]{10, 5, 3, 4, 3, 5, 6}));
        System.out.println("Equilibrium index: " + equilibriumIndex(new int[]{1, 3, 5, 2, 2}));
        System.out.println("Majority element: " + majorityElement(new int[]{2, 2, 1, 1, 2}));
        System.out.println("Maximum subarray sum: " + maxSubArraySum(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}