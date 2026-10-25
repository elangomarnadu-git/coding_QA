import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Single_Transversal {
    // ===== QUESTION 1 =====
    // Question: Count even and odd values in an array.
    // Input: arr = [1, 2, 3, 4] | Output: [2, 2]
    public static int[] countEvenOdd(int[] arr) {
        int even = 0;
        int odd = 0;
        for (int num : arr) {
            if (num % 2 == 0) even++;
            else odd++;
        }
        return new int[]{even, odd};
    }

    // ===== QUESTION 2 =====
    // Question: Find the maximum number of consecutive ones.
    // Input: arr = [1, 1, 0, 1, 1, 1] | Output: 3
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

    // ===== QUESTION 3 =====
    // Question: Find the maximum profit from one buy and one later sell.
    // Input: prices = [7, 1, 5, 3, 6, 4] | Output: 5
    public static int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }
        return maxProfit;
    }

    // ===== QUESTION 4 =====
    // Question: Find the maximum arr[j] - arr[i] where j is greater than i.
    // Input: arr = [2, 3, 10, 6, 4, 8, 1] | Output: 8
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

    // ===== QUESTION 5 =====
    // Question: Find the missing value from the range 0 through n.
    // Input: arr = [3, 0, 1] | Output: 2
    public static int missingNumber(int[] arr) {
        int n = arr.length;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        for (int num : arr) actualSum += num;
        return expectedSum - actualSum;
    }

    // ===== QUESTION 6 =====
    // Question: Find the first repeating value when scanning the array from left to right.
    // Input: arr = [10, 5, 3, 4, 3, 5, 6] | Output: 5
    public static int firstRepeating(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        int result = -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (seen.contains(arr[i])) result = arr[i];
            else seen.add(arr[i]);
        }
        return result;
    }

    // ===== QUESTION 7 =====
    // Question: Find an index whose left and right sums are equal, or -1 if none exists.
    // Input: arr = [1, 3, 5, 2, 2] | Output: 2
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

    // ===== QUESTION 8 =====
    // Question: Find the value appearing more than half the time, or -1 if none exists.
    // Input: arr = [2, 2, 1, 1, 1, 2, 2] | Output: 2
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

    // ===== QUESTION 9 =====
    // Question: Find the maximum sum of a contiguous subarray.
    // Input: arr = [-2, 1, -3, 4, -1, 2, 1, -5, 4] | Output: 6
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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an algorithm:");
            System.out.println("1. Count even and odd numbers");
            System.out.println("2. Maximum consecutive ones");
            System.out.println("3. Best time to buy and sell stock");
            System.out.println("4. Maximum difference arr[j] - arr[i]");
            System.out.println("5. Missing number (values from 0 to n)");
            System.out.println("6. First repeating element");
            System.out.println("7. Equilibrium index");
            System.out.println("8. Majority element");
            System.out.println("9. Maximum contiguous subarray sum");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) break;
            int choice = scanner.nextInt();
            if (choice == 0) break;
            if (choice < 1 || choice > 9) {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.print("Array length: ");
            if (!scanner.hasNextInt()) break;
            int length = scanner.nextInt();
            if (length < 0) {
                System.out.println("Array length cannot be negative.");
                continue;
            }

            int[] arr = new int[length];
            System.out.println("Enter " + length + " integers, separated by spaces:");
            boolean validInput = true;
            for (int i = 0; i < length; i++) {
                if (!scanner.hasNextInt()) {
                    validInput = false;
                    break;
                }
                arr[i] = scanner.nextInt();
            }
            if (!validInput) {
                System.out.println("Expected an integer for every array value.");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.println("Even/odd counts: " + Arrays.toString(countEvenOdd(arr)));
                    break;
                case 2:
                    System.out.println("Maximum consecutive ones: " + maxConsecutiveOnes(arr));
                    break;
                case 3:
                    System.out.println("Maximum stock profit: " + maxProfit(arr));
                    break;
                case 4:
                    System.out.println("Maximum difference: " + maxDifference(arr));
                    break;
                case 5:
                    System.out.println("Missing number: " + missingNumber(arr));
                    break;
                case 6:
                    System.out.println("First repeating element: " + firstRepeating(arr));
                    break;
                case 7:
                    System.out.println("Equilibrium index: " + equilibriumIndex(arr));
                    break;
                case 8:
                    System.out.println("Majority element: " + majorityElement(arr));
                    break;
                case 9:
                    System.out.println("Maximum subarray sum: " + maxSubArraySum(arr));
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }
}