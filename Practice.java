import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Practice {
    public static void main(String[] args) {
       //anagram();
       // containsDuplicate();
        //firstRepeatingChar();
        //firstNonRepeatingChar();
        //topKFrequent();
        //twoSum();
        //nMinusOne();
        findDuplicatesValuesAndLastIndex();
    }

    private static void findDuplicatesValuesAndLastIndex() {
        int[] arr = new int[]{1,2,3,3,1};
        IntStream intStream = IntStream.range(0, arr.length).filter(i -> IntStream.of(arr).anyMatch(j -> j == arr[i] && i != j));

        Arrays.stream(arr).boxed().collect(Collectors.groupingBy(n -> n,Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue()>1)
                .forEach(entry -> {
                    int lastIndex = Arrays.stream(arr).boxed().collect(Collectors.toList()).lastIndexOf(entry.getKey());
                    System.out.println("Duplicate value:"+entry.getKey()+" Last index:"+lastIndex);
                });
    }

    private static void nMinusOne() {
        int[] arr = new int[]{1,2,3,6,9};
        int k=3;
        int sum=0;
        int result[] =new int [arr.length];

        for(int i=0;i<arr.length;i++){
            sum=0;
            for(int j=0; j<=i;j++){
                sum+=arr[j];
                if(i>=3 && j>=3){
                    sum-=arr[j-3];
                }
            }
            result[i]=sum;
        }
        System.out.println("result:"+Arrays.toString(result));

    }

    private static void twoSum() {
        int[] arr = new int[]{ 4, 5, 6};
        int target = 10;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                sum = arr[i] + arr[j];
                if (sum == target) {
                    System.out.println("First index:" + i + "SecondIndex" + j);
                    return;
                }
            }
        }
    }

    private static void topKFrequent() {
        //Input: nums = [1,2,2,3,3,3], k = 2

        //Output: [2,3]
        int[] nums= new int[]{1, 2, 2, 3, 3, 3};
        int k = 2;
        Arrays.stream(nums).boxed().collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(k)
                .map(Map.Entry::getKey).toList();
    }

    private static void firstRepeatingChar() {
        String str = "laveleetcode";

        Map<Character, Integer> map = new HashMap();
        int count = 1;
        for (char ch : str.toCharArray()) {
            if (map.get(ch) == null) {
                map.put(ch, count);
            } else {
                count = map.get(ch);
                map.put(ch, count + 1);
            }

            if (map.get(ch) > 1) {
                System.out.println("first not repeart char :" + ch);
                break;
            }
        }
    }
    private static void firstNonRepeatingChar() {
        String str = "laveleetcode";

       Optional<Character> chr= str.chars().mapToObj(ch -> (char) ch).collect(Collectors.groupingBy(c->c,LinkedHashMap::new,Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue()==1).map(Map.Entry::getKey).findFirst();
        System.out.println("first non repeart char :" + chr);
    }

    private static void containsDuplicate() {
        int[] nums = {1, 2, 3, 4, 5, 1};
        boolean hasDuplicate= Arrays.stream(nums).boxed().collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1).findFirst().isPresent();
        System.out.println(hasDuplicate);

    }

    private static void anagram() {
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        Map<String, List<String>> map= Arrays.stream(words)
                .collect(Collectors.groupingBy(w -> {
                    char[] ch = w.toCharArray();
                    Arrays.sort(ch);
                    return new String(ch);
                }));
        System.out.println(map);
    }
}
