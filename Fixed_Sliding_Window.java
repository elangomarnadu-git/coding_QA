// Compile and run: javac Fixed_Sliding_Window.java && java Fixed_Sliding_Window
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Fixed_Sliding_Window {
    // ===== QUESTION 1 =====
    // Question: Find the maximum sum of any k consecutive elements.
    // Input: arr = [2, 1, 5, 1, 3, 2], k = 3 | Output: 9
    public static int maxSum(int[] arr, int k) {
        int left = 0, window = 0;
        int best = Integer.MIN_VALUE;
        for (int right = 0; right < arr.length; right++) {
            window += arr[right];
            if (right - left + 1 > k) window -= arr[left++];
            if (right - left + 1 == k) best = Math.max(best, window);
        }
        return best;
    }

    // ===== QUESTION 2 =====
    // Question: Find the maximum product of any k consecutive elements.
    // Input: arr = [0, 2, 1, 5, 1, 3, 2], k = 3 | Output: 15
    public static long maxProduct(int[] arr, int k) {
        long best = Long.MIN_VALUE;
        for (int left = 0; left + k <= arr.length; left++) {
            long prod = 1;
            int right = left;
            while (right < left + k) prod *= arr[right++];
            best = Math.max(best, prod);
        }
        return best;
    }

    // ===== QUESTION 3 =====
    // Question: Return the floored average of every subarray of size k.
    // Input: arr = [0, 2, 1, 5, 1, 3, 2], k = 3 | Output: [1, 2, 2, 3, 2]
    public static int[] averages(int[] arr, int k) {
        int n = arr.length;
        int[] res = new int[n - k + 1];
        int window = 0, left = 0;
        for (int right = 0; right < n; right++) {
            window += arr[right];
            if (right - left + 1 > k) window -= arr[left++];
            if (right - left + 1 == k) res[left] = window / k;
        }
        return res;
    }

    public static boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) >= 0;
    }

    // ===== QUESTION 4 =====
    // Question: Find the maximum number of vowels in a substring of length k.
    // Input: s = "abciiidef", k = 3 | Output: 3
    public static int maxVowels(String s, int k) {
        int left = 0, cur = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            if (isVowel(s.charAt(right))) cur++;
            if (right - left + 1 > k && isVowel(s.charAt(left++))) cur--;
            if (right - left + 1 == k) best = Math.max(best, cur);
        }
        return best;
    }

    // ===== QUESTION 5 =====
    // Question: Return the first negative number in every window of size k, or 0 if absent.
    // Input: arr = [12, -1, -7, 8, -15, 30, 16, 28], k = 3 | Output: [-1, -1, -7, -15, -15, 0]
    public static int[] firstNegatives(int[] arr, int k) {
        int n = arr.length;
        int[] res = new int[n - k + 1];
        Deque<Integer> neg = new ArrayDeque<>();
        int left = 0;
        for (int right = 0; right < n; right++) {
            if (arr[right] < 0) neg.addLast(right);
            if (right - left + 1 > k) left++;
            while (!neg.isEmpty() && neg.peekFirst() < left) neg.pollFirst();
            if (right - left + 1 == k)
                res[left] = neg.isEmpty() ? 0 : arr[neg.peekFirst()];
        }
        return res;
    }

    // ===== QUESTION 6 =====
    // Question: Count substrings of text that are anagrams of pattern.
    // Input: text = "forxxorfxdofr", pattern = "for" | Output: 3
    public static int countAnagrams(String text, String pattern) {
        int n = text.length(), k = pattern.length();
        if (k > n) return 0;
        int[] need = new int[128], window = new int[128];
        for (int i = 0; i < k; i++) need[pattern.charAt(i)]++;
        int count = 0, left = 0;
        for (int right = 0; right < n; right++) {
            window[text.charAt(right)]++;
            if (right - left + 1 > k) window[text.charAt(left++)]--;
            if (right - left + 1 == k && Arrays.equals(need, window)) count++;
        }
        return count;
    }

    // ===== QUESTION 7 =====
    // Question: Does s2 contain a substring that is a permutation of s1?
    // Input: s1 = "ab", s2 = "eidbaooo" | Output: true
    public static boolean checkInclusion(String s1, String s2) {
        int k = s1.length();
        if (k > s2.length()) return false;
        int[] need = new int[128], window = new int[128];
        for (int i = 0; i < k; i++) need[s1.charAt(i)]++;
        int left = 0;
        for (int right = 0; right < s2.length(); right++) {
            window[s2.charAt(right)]++;
            if (right - left + 1 > k) window[s2.charAt(left++)]--;
            if (right - left + 1 == k && Arrays.equals(need, window)) return true;
        }
        return false;
    }

    // ===== QUESTION 8 =====
    // Question: Return the starting indices of all substrings of s that are anagrams of p.
    // Input: s = "cbaebabacd", p = "abc" | Output: [0, 6]
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int n = s.length(), k = p.length();
        if (k > n) return res;
        int[] need = new int[128], window = new int[128];
        for (int i = 0; i < k; i++) need[p.charAt(i)]++;
        int left = 0;
        for (int right = 0; right < n; right++) {
            window[s.charAt(right)]++;
            if (right - left + 1 > k) window[s.charAt(left++)]--;
            if (right - left + 1 == k && Arrays.equals(need, window)) res.add(left);
        }
        return res;
    }

    // ===== QUESTION 9 =====
    // Question: Find the maximum element in every contiguous subarray of size k.
    // Input: arr = [1, 3, -1, -3, 5, 3, 6, 7], k = 3 | Output: [3, 3, 5, 5, 6, 7]
    public static int[] maxSlidingWindow(int[] arr, int k) {
        int n = arr.length;
        int[] res = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        int left = 0;
        for (int right = 0; right < n; right++) {
            while (!dq.isEmpty() && arr[dq.peekLast()] <= arr[right]) dq.pollLast();
            dq.addLast(right);
            if (right - left + 1 > k) left++;
            while (!dq.isEmpty() && dq.peekFirst() < left) dq.pollFirst();
            if (right - left + 1 == k) res[left] = arr[dq.peekFirst()];
        }
        return res;
    }

    // ===== QUESTION 10 =====
    // Question: Take exactly k cards from either end to maximize the score.
    // Input: cardPoints = [1, 2, 3, 4, 5, 6, 1], k = 3 | Output: 12
    public static int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int size = n - k;
        int total = 0;
        for (int x : cardPoints) total += x;
        if (size == 0) return total;
        int window = 0, left = 0;
        int minWindow = Integer.MAX_VALUE;
        for (int right = 0; right < n; right++) {
            window += cardPoints[right];
            if (right - left + 1 > size) window -= cardPoints[left++];
            if (right - left + 1 == size) minWindow = Math.min(minWindow, window);
        }
        return total - minWindow;
    }

    private static int[] readArray(Scanner scanner) {
        System.out.print("Array length: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Expected an integer for the array length.");
            return null;
        }
        int length = scanner.nextInt();
        if (length < 0) {
            System.out.println("Array length cannot be negative.");
            return null;
        }

        int[] arr = new int[length];
        System.out.println("Enter " + length + " integers, separated by spaces:");
        for (int i = 0; i < length; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Expected an integer for every array value.");
                return null;
            }
            arr[i] = scanner.nextInt();
        }
        return arr;
    }

    private static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNext()) {
            System.out.println("Expected a string value.");
            return null;
        }
        return scanner.next();
    }

    private static Integer readIntInRange(Scanner scanner, String prompt, int min, int max) {
        System.out.print(prompt);
        if (!scanner.hasNextInt()) {
            System.out.println("Expected an integer value.");
            return null;
        }
        int value = scanner.nextInt();
        if (value < min || value > max) {
            System.out.println("Enter a value from " + min + " to " + max + ".");
            return null;
        }
        return value;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an algorithm:");
            System.out.println("1. Maximum sum of k consecutive elements");
            System.out.println("2. Maximum product of k consecutive elements");
            System.out.println("3. Average of every subarray of size k");
            System.out.println("4. Maximum vowels in a substring of length k");
            System.out.println("5. First negative in every window of size k");
            System.out.println("6. Count anagram substrings");
            System.out.println("7. Check whether a string contains a permutation");
            System.out.println("8. Find all anagrams in a string");
            System.out.println("9. Sliding window maximum");
            System.out.println("10. Maximum points from cards");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) break;
            int choice = scanner.nextInt();
            if (choice == 0) break;
            if (choice < 1 || choice > 10) {
                System.out.println("Invalid choice.");
                continue;
            }

            if (choice == 4) {
                String text = readString(scanner, "String: ");
                if (text == null) break;
                Integer k = readIntInRange(scanner, "Window size: ", 1, text.length());
                if (k == null) continue;
                System.out.println("Maximum vowels: " + maxVowels(text, k));
                continue;
            }

            if (choice >= 6 && choice <= 8) {
                String first = readString(scanner, choice == 7 ? "First string: " : "Text: ");
                if (first == null) break;
                String second = readString(scanner, choice == 7 ? "Second string: " : "Pattern: ");
                if (second == null) break;
                switch (choice) {
                    case 6:
                        System.out.println("Anagram count: " + countAnagrams(first, second));
                        break;
                    case 7:
                        System.out.println("Contains permutation: " + checkInclusion(first, second));
                        break;
                    case 8:
                        System.out.println("Anagram starting indices: " + findAnagrams(first, second));
                        break;
                    default:
                        break;
                }
                continue;
            }

            int[] arr = readArray(scanner);
            if (arr == null) break;
            if (choice == 10) {
                Integer k = readIntInRange(scanner, "Number of cards to take: ", 0, arr.length);
                if (k == null) continue;
                System.out.println("Maximum score: " + maxScore(arr, k));
                continue;
            }

            if (arr.length == 0) {
                System.out.println("Array must not be empty.");
                continue;
            }
            Integer k = readIntInRange(scanner, "Window size: ", 1, arr.length);
            if (k == null) continue;
            switch (choice) {
                case 1:
                    System.out.println("Maximum sum: " + maxSum(arr, k));
                    break;
                case 2:
                    System.out.println("Maximum product: " + maxProduct(arr, k));
                    break;
                case 3:
                    System.out.println("Averages: " + Arrays.toString(averages(arr, k)));
                    break;
                case 5:
                    System.out.println("First negatives: " + Arrays.toString(firstNegatives(arr, k)));
                    break;
                case 9:
                    System.out.println("Window maximums: " + Arrays.toString(maxSlidingWindow(arr, k)));
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }
}
