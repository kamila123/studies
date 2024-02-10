import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Given string str. The task is to find the maximum occurring character in the string str.
 * <p>
 * Examples:
 * <p>
 * Input: geeksforgeeks
 * Output: e
 * Explanation: ‘e’ occurs 4 times in the string
 * <p>
 * Input: test
 * Output: t
 * Explanation: ‘t’ occurs 2 times in the string
 */
public class MaxCharacterCount {

    public static Map<Character, Integer> findMaxCharacterCount(String str) {
        Map<Character, Integer> max = new HashMap<>();

        String firstChar = String.valueOf(str.charAt(0));

        max.put(str.charAt(0), str.length() - str.replace(firstChar, "").length());

        for (char i : str.toCharArray()) {
            int count = str.length() - str.replace(String.valueOf(i), "").length();
            if (count > max.get(max.keySet().iterator().next())) {
                max.clear();
                max.put(i, count);
            }
        }

        return max;
    }

    static int optimized(String str) {

        int str_length = str.length();
        int max_num = 0;
        String character = "";

        for (char i : str.toCharArray()) {

            String current_char = String.valueOf(i);
            int count = str_length - str.replace(current_char, "").length();

            if (count >= max_num) {
                max_num = count;
                character = current_char;
            }
        }

        System.out.println(character);

        return max_num;
    }

    static int optimized2(String str) {

        int max = 0;
        int length = str.length();

        //loop through string length
        for (int i = 0; i < length ; i++) {
            //get current char and replace all occurrences
            String current_char = String.valueOf(str.charAt(i));
            //subtract the original length with the replaced length
            int count = length - str.replace(current_char, "").length();
            //update max if current max is less than existent max
            max = Math.max(count, max);
        }

        return max;
    }

    static int optimized3(String str) {

        Map<Character, Integer> max = new HashMap<>();
        Map.Entry<Character, Integer> max_value = new AbstractMap.SimpleEntry<>('a', 0);

        for (char i : str.toCharArray()) {
            max.computeIfPresent(i, (k, v) -> v + 1);
            max.putIfAbsent(i, 1);
        }

        for (Map.Entry<Character, Integer> entry : max.entrySet()) {
            if (entry.getValue() > max_value.getValue()) {
                max_value = entry;
            }
        }
        return max_value.getValue();
    }

    public static void main(String[] args) {
        String str = "geekkksforgeekks";
//        System.out.println(optimized(str));
        System.out.println(optimized2(str));
        System.out.println(optimized3(str));
    }
}
