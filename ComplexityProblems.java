import java.util.*;

public class ComplexityProblems {

    record StrCount(String s, int c) {}
    record IntPair(int a, int b) {}
    record Triple(int a, int b, int sum) {}
    record StrPair(String a, String b) {}
    record ValCount(int v, int c) {}

    // ---------- Problem 1: normalize, count, sort ----------
    static String sortChars(String s) {
        char[] c = s.toCharArray();
        Arrays.sort(c);
        return new String(c);
    }

    static String norm(String s) { return sortChars(s.toLowerCase()); }

    // Brute: linear scans for uniqueness and counting. O(N·K log K + N·U·K + U·K log U)
    static List<StrCount> normalizeCountBrute(String[] arr) {
        List<String> norms = new ArrayList<>();
        for (String s : arr) norms.add(norm(s));
        List<String> uniq = new ArrayList<>();
        for (String n : norms) if (!uniq.contains(n)) uniq.add(n);
        Collections.sort(uniq);
        List<StrCount> res = new ArrayList<>();
        for (String u : uniq) {
            int c = 0;
            for (String n : norms) if (n.equals(u)) c++;
            res.add(new StrCount(u, c));
        }
        return res;
    }

    // Optimized: HashMap counts, then sort the U keys. O(N·K log K + U·K log U)
    static List<StrCount> normalizeCountOpt(String[] arr) {
        Map<String, Integer> freq = new HashMap<>();
        for (String s : arr) freq.merge(norm(s), 1, Integer::sum);
        List<String> keys = new ArrayList<>(freq.keySet());
        Collections.sort(keys);
        List<StrCount> res = new ArrayList<>();
        for (String k : keys) res.add(new StrCount(k, freq.get(k)));
        return res;
    }

    // ---------- Problem 2: duplicate pairs ----------
    // Brute: all index pairs, then sort. O(N² log N) worst case
    static List<IntPair> dupPairsBrute(int[] arr) {
        List<IntPair> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++)
            for (int j = i + 1; j < arr.length; j++)
                if (arr[i] == arr[j]) res.add(new IntPair(arr[i], arr[j]));
        res.sort(Comparator.comparingInt(IntPair::a).thenComparingInt(IntPair::b));
        return res;
    }

    // Optimized: count values, sort distinct values, emit f(f-1)/2 pairs per value.
    // O(N + U log U + P), where P is the number of pairs (output size).
    static List<IntPair> dupPairsOpt(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : arr) freq.merge(x, 1, Integer::sum);
        List<Integer> vals = new ArrayList<>(freq.keySet());
        Collections.sort(vals);
        List<IntPair> res = new ArrayList<>();
        for (int v : vals) {
            long f = freq.get(v);
            for (long k = 0; k < f * (f - 1) / 2; k++) res.add(new IntPair(v, v));
        }
        return res;
    }

    // ---------- Problem 3: equal rows after sorting ----------
    static int[][] sortedRows(int[][] m) {
        int[][] c = new int[m.length][];
        for (int i = 0; i < m.length; i++) {
            c[i] = m[i].clone();
            Arrays.sort(c[i]);
        }
        return c;
    }

    // Brute: compare every pair of rows. O(N·M log M + N²·M)
    static List<IntPair> equalRowsBrute(int[][] m) {
        int[][] c = sortedRows(m);
        List<IntPair> res = new ArrayList<>();
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++)
                if (Arrays.equals(c[i], c[j])) res.add(new IntPair(i, j));
        return res;
    }

    // Optimized: group rows by their sorted content. O(N·M log M + N·M + output)
    static List<IntPair> equalRowsOpt(int[][] m) {
        int[][] c = sortedRows(m);
        Map<String, List<Integer>> groups = new HashMap<>();
        List<IntPair> res = new ArrayList<>();
        for (int i = 0; i < c.length; i++) {
            List<Integer> g = groups.computeIfAbsent(Arrays.toString(c[i]), k -> new ArrayList<>());
            for (int prev : g) res.add(new IntPair(prev, i));
            g.add(i);
        }
        res.sort(Comparator.comparingInt(IntPair::a).thenComparingInt(IntPair::b));
        return res;
    }

    // ---------- Problem 4: frequency sort and search ----------
    static Map<Integer, Integer> freqMap(int[] arr) {
        Map<Integer, Integer> f = new HashMap<>();
        for (int x : arr) f.merge(x, 1, Integer::sum);
        return f;
    }

    // Order: frequency descending, then value ascending
    static Comparator<Integer> freqCmp(Map<Integer, Integer> f) {
        return (x, y) -> {
            int c = Integer.compare(f.get(y), f.get(x));
            return c != 0 ? c : Integer.compare(x, y);
        };
    }

    static List<Integer> freqSorted(Map<Integer, Integer> f) {
        List<Integer> list = new ArrayList<>(f.keySet());
        list.sort(freqCmp(f));
        return list;
    }

    // Brute: linear search. O(N + U log U + U)
    static int freqSearchBrute(int[] arr, int target) {
        return freqSorted(freqMap(arr)).indexOf(target);
    }

    // Optimized: binary search using the SAME comparator as the sort. O(N + U log U)
    // The list is NOT ordered by value, so a plain binary search on value is invalid.
    static int freqSearchOpt(int[] arr, int target) {
        Map<Integer, Integer> f = freqMap(arr);
        if (!f.containsKey(target)) return -1;
        List<Integer> list = freqSorted(f);
        int idx = Collections.binarySearch(list, target, freqCmp(f));
        return idx >= 0 ? idx : -1;
    }

    // ---------- Problem 5: pairs above target ----------
    // Brute: all pairs, filter, sort. O(N² log N)
    static List<Triple> pairsAboveBrute(int[] arr, int target) {
        List<Triple> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++)
            for (int j = i + 1; j < arr.length; j++) {
                int s = arr[i] + arr[j];
                if (s > target) res.add(new Triple(Math.min(arr[i], arr[j]), Math.max(arr[i], arr[j]), s));
            }
        res.sort(Comparator.comparingInt(Triple::sum).thenComparingInt(Triple::a).thenComparingInt(Triple::b));
        return res;
    }

    // Optimized: sort, binary search the first valid partner, emit only qualifying pairs.
    // Still O(N² log N) worst case because the output itself can be ~N² pairs.
    static List<Triple> pairsAboveOpt(int[] arr, int target) {
        int[] s = arr.clone();
        Arrays.sort(s);
        List<Triple> res = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            int lo = i + 1, hi = s.length;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (s[i] + s[mid] > target) hi = mid; else lo = mid + 1;
            }
            for (int j = lo; j < s.length; j++) res.add(new Triple(s[i], s[j], s[i] + s[j]));
        }
        res.sort(Comparator.comparingInt(Triple::sum).thenComparingInt(Triple::a).thenComparingInt(Triple::b));
        return res;
    }

    // If only the COUNT is needed: sort + two pointers, O(N log N)
    static long countPairsAbove(int[] arr, int target) {
        int[] s = arr.clone();
        Arrays.sort(s);
        int l = 0, r = s.length - 1;
        long c = 0;
        while (l < r) {
            if (s[l] + s[r] > target) { c += r - l; r--; }
            else l++;
        }
        return c;
    }

    // ---------- Problem 6: anagrams across two arrays ----------
    // Brute: sort both strings for every pair. O(N·M·K log K)
    static List<StrPair> anagramsBrute(String[] a1, String[] a2) {
        List<StrPair> res = new ArrayList<>();
        for (String x : a1)
            for (String y : a2)
                if (sortChars(x).equals(sortChars(y))) res.add(new StrPair(x, y));
        return res;
    }

    // Optimized: index arr2 by sorted key. O((N + M)·K log K + output)
    static List<StrPair> anagramsOpt(String[] a1, String[] a2) {
        Map<String, List<String>> idx = new HashMap<>();
        for (String y : a2) idx.computeIfAbsent(sortChars(y), k -> new ArrayList<>()).add(y);
        List<StrPair> res = new ArrayList<>();
        for (String x : a1)
            for (String y : idx.getOrDefault(sortChars(x), List.of())) res.add(new StrPair(x, y));
        return res;
    }

    // ---------- Problem 7: smallest gap after dedupe ----------
    static int minGapSorted(int[] s) {
        if (s.length < 2) return -1;
        int best = Integer.MAX_VALUE;
        for (int i = 1; i < s.length; i++) best = Math.min(best, s[i] - s[i - 1]);
        return best;
    }

    // Brute: dedupe with List.contains. O(N·U + U log U)
    static int smallestGapBrute(int[] arr) {
        List<Integer> uniq = new ArrayList<>();
        for (int x : arr) if (!uniq.contains(x)) uniq.add(x);
        int[] s = uniq.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(s);
        return minGapSorted(s);
    }

    // Optimized: HashSet dedupe. O(N + U log U) average
    static int smallestGapOpt(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int x : arr) set.add(x);
        int[] s = set.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(s);
        return minGapSorted(s);
    }

    // ---------- Problem 8: prefix pairs ----------
    // Brute: sort, then every pair with startsWith. O(N·K log N + N²·K)
    static List<StrPair> prefixPairsBrute(String[] words) {
        String[] w = words.clone();
        Arrays.sort(w);
        List<StrPair> res = new ArrayList<>();
        for (int i = 0; i < w.length; i++)
            for (int j = i + 1; j < w.length; j++)
                if (w[j].startsWith(w[i])) res.add(new StrPair(w[i], w[j]));
        return res;
    }

    // Optimized: after sorting, strings sharing a prefix are contiguous right after it,
    // so stop at the first mismatch. O(N·K log N + output·K)
    static List<StrPair> prefixPairsOpt(String[] words) {
        String[] w = words.clone();
        Arrays.sort(w);
        List<StrPair> res = new ArrayList<>();
        for (int i = 0; i < w.length; i++)
            for (int j = i + 1; j < w.length && w[j].startsWith(w[i]); j++)
                res.add(new StrPair(w[i], w[j]));
        return res;
    }

    // ---------- Problem 9: match counts ----------
    // Brute: count by scanning arr2 for each element. O(N·M + N log N)
    static List<ValCount> matchCountsBrute(int[] a1, int[] a2) {
        List<ValCount> res = new ArrayList<>();
        for (int x : a1) {
            int c = 0;
            for (int y : a2) if (x == y) c++;
            if (c > 0) res.add(new ValCount(x, c));
        }
        res.sort(Comparator.comparingInt(ValCount::c));   // stable
        return res;
    }

    // Optimized: frequency map of arr2. O(N + M + N log N)
    static List<ValCount> matchCountsOpt(int[] a1, int[] a2) {
        Map<Integer, Integer> freq = freqMap(a2);
        List<ValCount> res = new ArrayList<>();
        for (int x : a1) {
            int c = freq.getOrDefault(x, 0);
            if (c > 0) res.add(new ValCount(x, c));
        }
        res.sort(Comparator.comparingInt(ValCount::c));
        return res;
    }

    // ---------- Problem 10: sort once, binary search queries ----------
    // Brute: linear scan per query. O(N·Q)
    static boolean[] searchQueriesBrute(int[] arr, int[] queries) {
        boolean[] res = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++)
            for (int x : arr)
                if (x == queries[i]) { res[i] = true; break; }
        return res;
    }

    // Optimized: sort once, binary search each. O((N + Q) log N)
    static boolean[] searchQueriesOpt(int[] arr, int[] queries) {
        int[] s = arr.clone();
        Arrays.sort(s);
        boolean[] res = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++)
            res[i] = Arrays.binarySearch(s, queries[i]) >= 0;
        return res;
    }

    // ---------- Demo ----------
    public static void main(String[] args) {
        String[] p1 = {"cat", "act", "dog", "tac", "god"};
        System.out.println(normalizeCountBrute(p1));   // [act=3, dgo=2]
        System.out.println(normalizeCountOpt(p1));

        int[] p2 = {10, 20, 10, 30, 20};
        System.out.println(dupPairsBrute(p2));         // (10,10), (20,20)
        System.out.println(dupPairsOpt(p2));

        int[][] p3 = {{3, 1, 2}, {2, 3, 1}, {4, 5, 6}};
        System.out.println(equalRowsBrute(p3));        // (0,1)
        System.out.println(equalRowsOpt(p3));

        int[] p4 = {4, 4, 2, 2, 2, 7};
        System.out.println(freqSearchBrute(p4, 4));    // 1
        System.out.println(freqSearchOpt(p4, 4));

        int[] p5 = {2, 4, 6, 8};
        System.out.println(pairsAboveBrute(p5, 9));    // (2,8,10) (4,6,10) (4,8,12) (6,8,14)
        System.out.println(pairsAboveOpt(p5, 9));
        System.out.println(countPairsAbove(p5, 9));    // 4

        String[] a1 = {"cat", "dog"}, a2 = {"act", "god", "bat"};
        System.out.println(anagramsBrute(a1, a2));     // (cat,act), (dog,god)
        System.out.println(anagramsOpt(a1, a2));

        int[] p7 = {8, 2, 5, 2, 9, 5};
        System.out.println(smallestGapBrute(p7));      // 1
        System.out.println(smallestGapOpt(p7));

        String[] p8 = {"app", "apple", "bat", "application"};
        System.out.println(prefixPairsBrute(p8));      // (app,apple), (app,application)
        System.out.println(prefixPairsOpt(p8));

        int[] m1 = {10, 20, 30}, m2 = {20, 20, 10, 40};
        System.out.println(matchCountsBrute(m1, m2));  // (10,1), (20,2)
        System.out.println(matchCountsOpt(m1, m2));

        int[] p10 = {40, 10, 30, 20, 50}, q = {30, 60, 10};
        System.out.println(Arrays.toString(searchQueriesBrute(p10, q)));  // [true, false, true]
        System.out.println(Arrays.toString(searchQueriesOpt(p10, q)));
    }
}