import java.util.HashMap;
import java.util.Map;

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
        for (int i = 0; i < length - 1; i++) {
            //get current char and replace all occurrences
            String current_char = String.valueOf(str.charAt(i));
            //subtract the original length with the replaced length
            int count = length - str.replace(current_char, "").length();
            //update max if current max is less then existent max
            max = Math.max(count, max);
        }

        return max;
    }

    public static void main(String[] args) {
        String str = "geekkksforgeekks";
        System.out.println(optimized(str));
        System.out.println(optimized2(str));
    }
}
