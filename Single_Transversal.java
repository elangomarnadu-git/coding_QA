import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Single_Transversal {
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