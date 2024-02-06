import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindDuplicated {

    static int findRepeating(List<Integer> numbers) {

        int total = 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.size(); i++) {
            if (map.containsKey(numbers.get(i))) {
                map.put(numbers.get(i), numbers.get(i) + 1);
            } else {
                map.put(numbers.get(i), 1);
            }
        }


        for (Map.Entry<Integer, Integer> entry : map.entrySet())
            if (entry.getValue() > 1)
                total++;

        return total;
    }

    static int findRepeating(int[] numbers) {

        int total = 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(numbers[i])) {
                map.put(numbers[i], map.get(numbers[i]) + 1);
                total++;
            } else {
                map.put(numbers[i], 1);
            }
        }


        for (Map.Entry<Integer, Integer> entry : map.entrySet())
            if (entry.getValue() > 1)
                total++;

        return total;
    }

    static int findRepeatingList(int[] numbers) {
        
        List<Integer> integers = new ArrayList<>();

        for (int i = 0; i < numbers.length; i++) {
            if (integers.contains(numbers[i])) {
                return numbers[i];
            }
            integers.add(numbers[i]);
        }

        return 0;
    }

    static int findRepeatingNumber(int[] numbers) {


        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(numbers[i])) {
                return numbers[i];
            } else {
                map.put(numbers[i], 1);
            }
        }


        return 0;
    }

    // Driver Code 
    public static void main(String[] args) {
        int[] arr = {4, 4, 5, 5, 6};
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(4);
        list.add(5);
        list.add(5);
        list.add(6);

        int arr_size = arr.length;
        int frequency = findRepeating(list);

        System.out.println("Below is the frequency" +
                frequency + "of repeated elements -");
    }


}
