import java.util.Arrays;
import java.util.Scanner;

public class Two_pointers {
    // ===== QUESTION 1 =====
    // Question: Remove all occurrences of k in place and return the remaining length.
    // Input: nums = [3, 2, 2, 3], k = 3 | Output: length 2, prefix [2, 2]
    public static int removeElement(int[] nums, int k) {
        int write = 0;
        for (int read = 0; read < nums.length; read++)
            if (nums[read] != k) nums[write++] = nums[read];
        return write;
    }

    // ===== QUESTION 2 =====
    // Question: Move negative values before non-negative values while preserving their order.
    // Input: arr = [1, -2, 3, -4] | Output: [-2, -4, 1, 3]
    public static int[] moveNegatives(int[] arr) {
        int[] result = new int[arr.length]; int index = 0;
        for (int num : arr) if (num < 0) result[index++] = num;
        for (int num : arr) if (num >= 0) result[index++] = num;
        return result;
    }

    // ===== QUESTION 3 =====
    // Question: Remove duplicates from a sorted array in place, allowing at most two copies.
    // Input: nums = [1, 1, 1, 2, 2, 3] | Output: length 5, prefix [1, 1, 2, 2, 3]
    public static int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;
        int write = 2;
        for (int read = 2; read < nums.length; read++)
            if (nums[read] != nums[write - 2])
                nums[write++] = nums[read];
        return write;
    }

    // ===== QUESTION 4 =====
    // Question: Merge two sorted arrays into one sorted array.
    // Input: a = [1, 3, 5], b = [2, 4, 6] | Output: [1, 2, 3, 4, 5, 6]
    public static int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length]; int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length)
            result[k++] = a[i] <= b[j] ? a[i++] : b[j++];
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];
        return result;
    }

    // ===== QUESTION 5 =====
    // Question: Reverse an array in place.
    // Input: arr = [1, 2, 3, 4] | Output: [4, 3, 2, 1]
    public static void reverse(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right]; arr[right] = temp;
            left++; right--;
        }
    }

    // ===== QUESTION 6 =====
    // Question: Find two numbers in a sorted array that sum to target; return 1-based indices.
    // Input: nums = [2, 7, 11, 15], target = 9 | Output: [1, 2]
    public static int[] twoSum(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) return new int[]{left + 1, right + 1};
            if (sum < target) left++;
            else right--;
        }
        return new int[]{-1, -1};
    }

    // ===== QUESTION 7 =====
    // Question: Return the squares of a sorted array in sorted order.
    // Input: nums = [-4, -1, 0, 3, 10] | Output: [0, 1, 9, 16, 100]
    public static int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];
        int left = 0, right = nums.length - 1, position = nums.length - 1;
        while (left <= right) {
            int leftSquare = nums[left] * nums[left], rightSquare = nums[right] * nums[right];
            if (leftSquare > rightSquare) {
                result[position--] = leftSquare;
                left++;
            } else {
                result[position--] = rightSquare;
                right--;
            }
        }
        return result;
    }

    // ===== QUESTION 8 =====
    // Question: Rearrange an array so that even values come before odd values.
    // Input: arr = [3, 1, 2, 4] | Output: [4, 2, 1, 3]
    public static void sortByParity(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            while (left < right && arr[left] % 2 == 0) left++;
            while (left < right && arr[right] % 2 != 0) right--;
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right]; arr[right] = temp;
            }
        }
    }

    // ===== QUESTION 9 =====
    // Question: Find the maximum water area between two vertical lines.
    // Input: height = [1, 8, 6, 2, 5, 4, 8, 3, 7] | Output: 49
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1, maxArea = 0;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, area);
            if (height[left] < height[right]) left++;
            else right--;
        }
        return maxArea;
    }

    // ===== QUESTION 10 =====
    // Question: Sort an array containing only 0, 1, and 2.
    // Input: nums = [2, 0, 2, 1, 1, 0] | Output: [0, 0, 1, 1, 2, 2]
    public static void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low];
                nums[low] = nums[mid]; nums[mid] = temp;
                low++; mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid];
                nums[mid] = nums[high]; nums[high] = temp;
                high--;
            }
        }
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an algorithm:");
            System.out.println("1. Remove all occurrences of k");
            System.out.println("2. Move negatives to beginning (stable)");
            System.out.println("3. Remove duplicates, allowing at most twice");
            System.out.println("4. Merge two sorted arrays");
            System.out.println("5. Reverse array in place");
            System.out.println("6. Two sum in sorted array");
            System.out.println("7. Sorted squares");
            System.out.println("8. Even before odd");
            System.out.println("9. Container with most water");
            System.out.println("10. Sort colors (values 0, 1, and 2)");
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
                System.out.println("Enter the first sorted array.");
                int[] a = readArray(scanner);
                if (a == null) break;
                System.out.println("Enter the second sorted array.");
                int[] b = readArray(scanner);
                if (b == null) break;
                System.out.println("Merged array: " + Arrays.toString(merge(a, b)));
                continue;
            }

            if (choice == 6) System.out.println("Enter a sorted array.");
            int[] arr = readArray(scanner);
            if (arr == null) break;

            switch (choice) {
                case 1:
                    System.out.print("Value to remove: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Expected an integer value.");
                        break;
                    }
                    int value = scanner.nextInt();
                    int newLength = removeElement(arr, value);
                    System.out.println("Remaining elements: "
                            + Arrays.toString(Arrays.copyOf(arr, newLength)));
                    break;
                case 2:
                    System.out.println("Negatives first: " + Arrays.toString(moveNegatives(arr)));
                    break;
                case 3:
                    int uniqueLength = removeDuplicates(arr);
                    System.out.println("Elements after removing excess duplicates: "
                            + Arrays.toString(Arrays.copyOf(arr, uniqueLength)));
                    break;
                case 5:
                    reverse(arr);
                    System.out.println("Reversed array: " + Arrays.toString(arr));
                    break;
                case 6:
                    System.out.print("Target sum: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Expected an integer target.");
                        break;
                    }
                    int target = scanner.nextInt();
                    System.out.println("1-based indices: " + Arrays.toString(twoSum(arr, target)));
                    break;
                case 7:
                    System.out.println("Sorted squares: " + Arrays.toString(sortedSquares(arr)));
                    break;
                case 8:
                    sortByParity(arr);
                    System.out.println("Even before odd: " + Arrays.toString(arr));
                    break;
                case 9:
                    System.out.println("Maximum area: " + maxArea(arr));
                    break;
                case 10:
                    sortColors(arr);
                    System.out.println("Sorted colors: " + Arrays.toString(arr));
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }
}
