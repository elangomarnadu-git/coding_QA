// Compile and run: javac Variable_Sliding_Window.java && java Variable_Sliding_Window
import java.util.Arrays;
import java.util.Scanner;

public class Variable_Sliding_Window {
    public static int[] longestSubarraySum(int[] arr, int k) {
        int left = 0, sum = 0, bestStart = 0, bestLen = 0;
        for (int right = 0; right < arr.length; right++) {
            sum += arr[right];
            while (sum > k && left <= right) sum -= arr[left++];
            if (sum == k && right - left + 1 > bestLen) {
                bestLen = right - left + 1;
                bestStart = left;
            }
        }
        return Arrays.copyOfRange(arr, bestStart, bestStart + bestLen);
    }

    public static int[] smallestSubarraySum(int[] arr, int k) {
        int left = 0, sum = 0, bestStart = 0, bestLen = Integer.MAX_VALUE;
        for (int right = 0; right < arr.length; right++) {
            sum += arr[right];
            while (sum > k && left <= right) sum -= arr[left++];
            if (sum == k && right - left + 1 < bestLen) {
                bestLen = right - left + 1;
                bestStart = left;
            }
        }
        if (bestLen == Integer.MAX_VALUE) return new int[0];
        return Arrays.copyOfRange(arr, bestStart, bestStart + bestLen);
    }

    public static int minSubArrayLen(int[] arr, int k) {
        int left = 0, sum = 0, best = Integer.MAX_VALUE;
        for (int right = 0; right < arr.length; right++) {
            sum += arr[right];
            while (sum >= k && left <= right) {
                best = Math.min(best, right - left + 1);
                sum -= arr[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    public static int[] longestWithKOdds(int[] arr, int k) {
        if (k < 0) return new int[0];
        int left = 0, odds = 0, bestStart = 0, bestLen = 0;
        for (int right = 0; right < arr.length; right++) {
            if (arr[right] % 2 != 0) odds++;
            while (odds > k) {
                if (arr[left] % 2 != 0) odds--;
                left++;
            }
            if (odds == k && right - left + 1 > bestLen) {
                bestLen = right - left + 1;
                bestStart = left;
            }
        }
        return Arrays.copyOfRange(arr, bestStart, bestStart + bestLen);
    }

    public static String longestUnique(String s) {
        int[] last = new int[128];
        Arrays.fill(last, -1);
        int left = 0, bestStart = 0, bestLen = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (last[c] >= left) left = last[c] + 1;
            last[c] = right;
            if (right - left + 1 > bestLen) {
                bestLen = right - left + 1;
                bestStart = left;
            }
        }
        return s.substring(bestStart, bestStart + bestLen);
    }

    public static String longestAtMostKDistinct(String s, int k) {
        if (k <= 0) return "";
        int[] count = new int[128];
        int left = 0, distinct = 0, bestStart = 0, bestLen = 0;
        for (int right = 0; right < s.length(); right++) {
            if (count[s.charAt(right)]++ == 0) distinct++;
            while (distinct > k) {
                if (--count[s.charAt(left++)] == 0) distinct--;
            }
            if (right - left + 1 > bestLen) {
                bestLen = right - left + 1;
                bestStart = left;
            }
        }
        return s.substring(bestStart, bestStart + bestLen);
    }

    public static String longestExactlyKDistinct(String s, int k) {
        if (k <= 0) return "";
        int[] count = new int[128];
        int left = 0, distinct = 0, bestStart = 0, bestLen = 0;
        for (int right = 0; right < s.length(); right++) {
            if (count[s.charAt(right)]++ == 0) distinct++;
            while (distinct > k) {
                if (--count[s.charAt(left++)] == 0) distinct--;
            }
            if (distinct == k && right - left + 1 > bestLen) {
                bestLen = right - left + 1;
                bestStart = left;
            }
        }
        return s.substring(bestStart, bestStart + bestLen);
    }

    public static int[] longestOnes(int[] arr, int k) {
        int left = 0, zeros = 0, bestStart = 0, bestLen = 0;
        for (int right = 0; right < arr.length; right++) {
            if (arr[right] == 0) zeros++;
            while (zeros > k) {
                if (arr[left++] == 0) zeros--;
            }
            if (right - left + 1 > bestLen) {
                bestLen = right - left + 1;
                bestStart = left;
            }
        }
        return Arrays.copyOfRange(arr, bestStart, bestStart + bestLen);
    }

    public static int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int left = 0, maxCount = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(right) - 'A']);
            if (right - left + 1 - maxCount > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static String minWindow(String s, String t) {
        if (t.length() > s.length()) return "";
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int missing = t.length();
        int left = 0, bestStart = 0, bestLen = Integer.MAX_VALUE;
        for (int right = 0; right < s.length(); right++) {
            if (need[s.charAt(right)]-- > 0) missing--;
            while (missing == 0) {
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestStart = left;
                }
                if (++need[s.charAt(left++)] > 0) missing++;
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
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
            System.out.println("1. Longest subarray with sum exactly k");
            System.out.println("2. Smallest subarray with sum exactly k");
            System.out.println("3. Minimum length subarray with sum at least k");
            System.out.println("4. Longest subarray with exactly k odd numbers");
            System.out.println("5. Longest substring without repeating characters");
            System.out.println("6. Longest substring with at most k distinct characters");
            System.out.println("7. Longest substring with exactly k distinct characters");
            System.out.println("8. Longest subarray of ones after flipping at most k zeros");
            System.out.println("9. Longest repeating character replacement");
            System.out.println("10. Minimum window substring");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) break;
            int choice = scanner.nextInt();
            if (choice == 0) break;
            if (choice < 1 || choice > 10) {
                System.out.println("Invalid choice.");
                continue;
            }

            if (choice == 5) {
                String text = readString(scanner, "String: ");
                if (text == null) break;
                System.out.println("Longest substring: " + longestUnique(text));
                continue;
            }

            if (choice == 10) {
                String text = readString(scanner, "Source string: ");
                if (text == null) break;
                String pattern = readString(scanner, "Target string: ");
                if (pattern == null) break;
                System.out.println("Minimum window: " + minWindow(text, pattern));
                continue;
            }

            if (choice == 6 || choice == 7 || choice == 9) {
                String text = readString(scanner, "String: ");
                if (text == null) break;
                int maxK = choice == 9 ? Integer.MAX_VALUE : text.length();
                int minK = choice == 9 ? 0 : 1;
                Integer k = readIntInRange(scanner, "k: ", minK, maxK);
                if (k == null) continue;
                if (choice == 9 && !text.matches("[A-Z]*")) {
                    System.out.println("Use uppercase English letters only.");
                    continue;
                }
                if (choice == 6) {
                    System.out.println("Longest substring: " + longestAtMostKDistinct(text, k));
                } else if (choice == 7) {
                    System.out.println("Longest substring: " + longestExactlyKDistinct(text, k));
                } else {
                    System.out.println("Longest length: " + characterReplacement(text, k));
                }
                continue;
            }

            int[] arr = readArray(scanner);
            if (arr == null) break;
            if (arr.length == 0) {
                System.out.println("Array must not be empty.");
                continue;
            }

            if (choice == 4 || choice == 8) {
                Integer k = readIntInRange(scanner, choice == 4 ? "Number of odd values k: " : "Zeros to flip k: ",
                        0, arr.length);
                if (k == null) continue;
                if (choice == 4) {
                    System.out.println("Longest subarray: " + Arrays.toString(longestWithKOdds(arr, k)));
                } else {
                    boolean binary = true;
                    for (int value : arr) binary &= value == 0 || value == 1;
                    if (binary) {
                        System.out.println("Longest subarray: " + Arrays.toString(longestOnes(arr, k)));
                    } else {
                        System.out.println("Array values must be 0 or 1.");
                    }
                }
                continue;
            }

            Integer k = readIntInRange(scanner, "Target sum k: ", 1, Integer.MAX_VALUE);
            if (k == null) continue;
            switch (choice) {
                case 1:
                    System.out.println("Longest subarray: " + Arrays.toString(longestSubarraySum(arr, k)));
                    break;
                case 2:
                    System.out.println("Smallest subarray: " + Arrays.toString(smallestSubarraySum(arr, k)));
                    break;
                case 3:
                    System.out.println("Minimum length: " + minSubArrayLen(arr, k));
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }
}
