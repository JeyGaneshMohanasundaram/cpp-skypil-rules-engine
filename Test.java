import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        System.out.println("=== 1. Valid Sudoku ===");
        char[][] board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };
        System.out.println("Is valid Sudoku: " + isValidSudoku(board));

        System.out.println("\n=== 2. Find Duplicates in Array ===");
        int[] arr = {4, 3, 2, 7, 8, 2, 3, 1};
        System.out.println("Duplicates: " + findDuplicates(arr));

        System.out.println("\n=== 3. First Non-Repeating Character ===");
        String str = "laveleetcode";
        firstNonRepeatingCharacter(str)
                .ifPresentOrElse(
                        c -> System.out.println("First non-repeating: " + c),
                        () -> System.out.println("No unique character found")
                );

        System.out.println("\n=== 4. Group Anagrams ===");
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(words));

        System.out.println("\n=== 5. LRU Cache ===");
        LRUCache<Integer, String> lru = new LRUCache<>(2);
        lru.put(1, "A");
        lru.put(2, "B");
        System.out.println("Get 1: " + lru.get(1)); // marks 1 as recently used
        lru.put(3, "C"); // evicts key 2
        System.out.println("Cache contents (least to most recent): " + lru);

        System.out.println("\n=== 6. Stream API Coding Highlights ===");
        streamApiDemo();
        Test();
    }

    // 1. Valid Sudoku (9x9) check
    // Rules: each row, column, and 3x3 block must contain digits 1-9 without duplicate
    public static boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                if (val != '.') {
                    // Unique tags per digit in row, col, and sub-box
                    if (!seen.add(val + " in row " + r) ||
                        !seen.add(val + " in col " + c) ||
                        !seen.add(val + " in box " + (r / 3) + "-" + (c / 3))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // 2. Find duplicates in an array using Java Streams
    public static Set<Integer> findDuplicates(int[] nums) {
        Set<Integer> visited = new HashSet<>();
        return Arrays.stream(nums)
                .filter(n -> !visited.add(n)) // add returns false when already present
                .boxed()
                .collect(Collectors.toSet());
    }

    // 3. First non-repeating character (preserves order with LinkedHashMap)
    public static Optional<Character> firstNonRepeatingCharacter(String s) {
        return s.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1L)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    // 4. Group anagrams: words with same sorted characters map to the same list
    public static Map<String, List<String>> groupAnagrams(String[] words) {
        return Arrays.stream(words)
                .collect(Collectors.groupingBy(w -> {
                    char[] chars = w.toCharArray();
                    Arrays.sort(chars);
                    return new String(chars); // canonical key
                }));
    }

    // 5. LRU Cache based on LinkedHashMap in access-order mode
    public static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75f, true); // true = access-order
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    // 6. Common Stream API patterns (Filter, Group, Partition, Summary)
    public static void streamApiDemo() {
        record Item(String category, double price) {}
        List<Item> items = List.of(
                new Item("Electronics", 999.99),
                new Item("Electronics", 149.50),
                new Item("Books", 19.99),
                new Item("Books", 39.90),
                new Item("Clothing", 45.00)
        );

        // Group by category and compute average price
        Map<String, Double> avgPriceByCategory = items.stream()
                .collect(Collectors.groupingBy(
                        Item::category,
                        Collectors.averagingDouble(Item::price)
                ));
        System.out.println("Average price per category: " + avgPriceByCategory);

        // Partition items into expensive (> $50) and budget (<= $50)
        Map<Boolean, List<Item>> partitioned = items.stream()
                .collect(Collectors.partitioningBy(i -> i.price() > 50.0));
        System.out.println("Expensive items count: " + partitioned.get(true).size());
        System.out.println("Budget items count: " + partitioned.get(false).size());
    }

public static void Test() {

        String [] arr = {"eat", "tea", "tan", "ate", "nat", "bat"};
    Map<String, List<String>> map= Arrays.stream(arr).collect(Collectors.groupingBy(w -> {
        char[] ch = w.toCharArray();
        Arrays.sort(ch);
        return new String(ch);
    }));

    Map<String, List<String>> map2 = new HashMap<>();
    for(String val : arr){
        char[] ch = val.toCharArray();
        Arrays.sort(ch);
        String key = new String(ch);
        if(!map2.containsKey(key)){
            map2.put(key, new ArrayList<>());
        }
        map2.get(key).add(val);
    }
    System.out.println(map2);

    String s ="TesT";

    Optional<Character> s1=s.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                    Function.identity(),
                    LinkedHashMap::new,
                    Collectors.counting()
            ))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() == 1L)
            .map(Map.Entry::getKey)
            .findFirst();
    System.out.println(s1.orElse('N'));

}

}
