import java.util.*;

public class Solutions {

    // 1. Two Sum -- O(n) time, O(n) space
    static int[] twoSum(int[] arr, int target) {
        Map<Integer, Integer> seen = new HashMap<>(); // value -> index
        for (int i = 0; i < arr.length; i++) {
            Integer j = seen.get(target - arr[i]);
            if (j != null) return new int[]{j, i};
            seen.put(arr[i], i);
        }
        return new int[]{};
    }

    // 2. Valid Anagram -- O(n)
    static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);
        for (char c : t.toCharArray()) {
            Integer n = freq.get(c);
            if (n == null || n == 0) return false;
            freq.put(c, n - 1);
        }
        return true;
    }

    // 3. Group Anagrams -- O(n * k log k)
    static List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (String w : words) {
            char[] chars = w.toCharArray();
            Arrays.sort(chars);
            groups.computeIfAbsent(new String(chars), k -> new ArrayList<>()).add(w);
        }
        return new ArrayList<>(groups.values());
    }

    // 4. First Unique Character -- O(n)
    static int firstUniqChar(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);
        for (int i = 0; i < s.length(); i++) {
            if (freq.get(s.charAt(i)) == 1) return i;
        }
        return -1;
    }

    // 5. Top K Frequent Elements -- O(n) using bucket sort by frequency
    @SuppressWarnings("unchecked")
    static int[] topKFrequent(int[] arr, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : arr) freq.merge(x, 1, Integer::sum);

        List<Integer>[] buckets = new List[arr.length + 1];
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            int f = e.getValue();
            if (buckets[f] == null) buckets[f] = new ArrayList<>();
            buckets[f].add(e.getKey());
        }

        int[] res = new int[k];
        int idx = 0;
        for (int f = buckets.length - 1; f >= 0 && idx < k; f--) {
            if (buckets[f] == null) continue;
            for (int x : buckets[f]) {
                if (idx == k) break;
                res[idx++] = x;
            }
        }
        return res;
    }

    // 6. Subarray Sum Equals K (negatives/zeros OK) -- O(n)
    static int subarraySum(int[] arr, int k) {
        Map<Long, Integer> seen = new HashMap<>();
        seen.put(0L, 1); // empty prefix
        long prefix = 0;
        int count = 0;
        for (int x : arr) {
            prefix += x;
            count += seen.getOrDefault(prefix - k, 0);
            seen.merge(prefix, 1, Integer::sum);
        }
        return count;
    }

    // 7. Longest Consecutive Sequence -- O(n)
    static int longestConsecutive(int[] arr) {
        Set<Integer> nums = new HashSet<>();
        for (int x : arr) nums.add(x);
        int best = 0;
        for (int x : nums) {
            if (!nums.contains(x - 1)) { // start of a run
                int y = x;
                while (nums.contains(y + 1)) y++;
                best = Math.max(best, y - x + 1);
            }
        }
        return best;
    }

    // 8. Intersection of Two Arrays -- O(n + m)
    static int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> a = new HashSet<>();
        for (int x : nums1) a.add(x);
        Set<Integer> res = new HashSet<>();
        for (int x : nums2) if (a.contains(x)) res.add(x);
        int[] out = new int[res.size()];
        int i = 0;
        for (int x : res) out[i++] = x;
        return out;
    }

    // 9. Minimum Window Substring -- O(n + m)
    static String minWindow(String s, String t) {
        if (s.isEmpty() || t.isEmpty()) return "";
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int missing = t.length();
        int left = 0, bestStart = 0, bestLen = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (need[c] > 0) missing--;
            need[c]--;

            if (missing == 0) {
                // shrink from the left while the window stays valid
                while (need[s.charAt(left)] < 0) {
                    need[s.charAt(left)]++;
                    left++;
                }
                if (right - left + 1 < bestLen) {
                    bestStart = left;
                    bestLen = right - left + 1;
                }
                // drop s[left] to search for the next window
                need[s.charAt(left)]++;
                missing++;
                left++;
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
    }

    // 10. Subarrays with exactly K distinct integers -- O(n)
    // exactly(k) = atMost(k) - atMost(k - 1)
    static int subarraysWithKDistinct(int[] arr, int k) {
        return atMost(arr, k) - atMost(arr, k - 1);
    }

    private static int atMost(int[] arr, int k) {
        if (k == 0) return 0;
        Map<Integer, Integer> freq = new HashMap<>();
        int left = 0, res = 0;
        for (int right = 0; right < arr.length; right++) {
            freq.merge(arr[right], 1, Integer::sum);
            while (freq.size() > k) {
                int l = arr[left++];
                if (freq.merge(l, -1, Integer::sum) == 0) freq.remove(l);
            }
            res += right - left + 1;
        }
        return res;
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
            System.out.println("1. Two Sum");
            System.out.println("2. Valid Anagram");
            System.out.println("3. Group Anagrams");
            System.out.println("4. First Unique Character");
            System.out.println("5. Top K Frequent Elements");
            System.out.println("6. Subarray Sum Equals K");
            System.out.println("7. Longest Consecutive Sequence");
            System.out.println("8. Intersection of Two Arrays");
            System.out.println("9. Minimum Window Substring");
            System.out.println("10. Subarrays with Exactly K Distinct Integers");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) {
                if (scanner.hasNext()) {
                    System.out.println("Expected an integer menu choice.");
                }
                break;
            }
            int choice = scanner.nextInt();
            if (choice == 0) break;
            if (choice < 1 || choice > 10) {
                System.out.println("Invalid choice.");
                continue;
            }

            switch (choice) {
                case 1: {
                    int[] arr = readArray(scanner);
                    if (arr == null) return;
                    System.out.print("Target: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Expected an integer target.");
                        return;
                    }
                    System.out.println("Indices: " + Arrays.toString(twoSum(arr, scanner.nextInt())));
                    break;
                }
                case 2: {
                    System.out.print("First string: ");
                    if (!scanner.hasNext()) return;
                    String first = scanner.next();
                    System.out.print("Second string: ");
                    if (!scanner.hasNext()) return;
                    System.out.println("Are anagrams: " + isAnagram(first, scanner.next()));
                    break;
                }
                case 3: {
                    System.out.print("Number of words: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Expected an integer word count.");
                        return;
                    }
                    int count = scanner.nextInt();
                    if (count < 0) {
                        System.out.println("Word count cannot be negative.");
                        return;
                    }
                    String[] words = new String[count];
                    System.out.println("Enter " + count + " words:");
                    for (int i = 0; i < count; i++) {
                        if (!scanner.hasNext()) {
                            System.out.println("Expected a word for every entry.");
                            return;
                        }
                        words[i] = scanner.next();
                    }
                    System.out.println("Anagram groups: " + groupAnagrams(words));
                    break;
                }
                case 4: {
                    System.out.print("String: ");
                    if (!scanner.hasNext()) return;
                    System.out.println("First unique character index: " + firstUniqChar(scanner.next()));
                    break;
                }
                case 5: {
                    int[] arr = readArray(scanner);
                    if (arr == null) return;
                    System.out.print("K: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Expected an integer for K.");
                        return;
                    }
                    int k = scanner.nextInt();
                    Set<Integer> distinct = new HashSet<>();
                    for (int value : arr) distinct.add(value);
                    int distinctValues = distinct.size();
                    if (k < 0 || k > distinctValues) {
                        System.out.println("K must be between 0 and the number of distinct array values.");
                        break;
                    }
                    System.out.println("Top K frequent: " + Arrays.toString(topKFrequent(arr, k)));
                    break;
                }
                case 6: {
                    int[] arr = readArray(scanner);
                    if (arr == null) return;
                    System.out.print("Target sum: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Expected an integer target sum.");
                        return;
                    }
                    System.out.println("Subarray count: " + subarraySum(arr, scanner.nextInt()));
                    break;
                }
                case 7: {
                    int[] arr = readArray(scanner);
                    if (arr == null) return;
                    System.out.println("Longest consecutive sequence length: " + longestConsecutive(arr));
                    break;
                }
                case 8: {
                    System.out.println("First array.");
                    int[] first = readArray(scanner);
                    if (first == null) return;
                    System.out.println("Second array.");
                    int[] second = readArray(scanner);
                    if (second == null) return;
                    System.out.println("Intersection: " + Arrays.toString(intersection(first, second)));
                    break;
                }
                case 9: {
                    System.out.print("Source string: ");
                    if (!scanner.hasNext()) return;
                    String source = scanner.next();
                    System.out.print("Target string: ");
                    if (!scanner.hasNext()) return;
                    System.out.println("Minimum window: " + minWindow(source, scanner.next()));
                    break;
                }
                case 10: {
                    int[] arr = readArray(scanner);
                    if (arr == null) return;
                    System.out.print("K distinct values: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Expected an integer for K.");
                        return;
                    }
                    int k = scanner.nextInt();
                    if (k < 1) {
                        System.out.println("K must be at least 1.");
                        break;
                    }
                    System.out.println("Subarray count: " + subarraysWithKDistinct(arr, k));
                    break;
                }
                default:
                    break;
            }
        }
        scanner.close();
    }
}