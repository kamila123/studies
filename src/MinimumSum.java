import java.util.*;
import java.util.stream.Collectors;

public class MinimumSum {


    static int solve(List<Integer> num, int k) {

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((one, two) -> two - one);

        for (int i = 0; i < num.size(); i++) {
            priorityQueue.add(num.get(i));
        }

        while (priorityQueue.size() > 0 && k > 0) {

            int max_ele = priorityQueue.poll();
            int division = (int) Math.ceil(max_ele / 2);

            priorityQueue.add(division);
            k--;
        }

        int sum = 0;
        while (priorityQueue.size() > 0)
            sum += priorityQueue.poll();

        return sum;
    }

    static int solve2(List<Integer> num, int k) {
        
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < num.size(); i++) {
            priorityQueue.add(num.get(i));
        }

        while (!priorityQueue.isEmpty() && k > 0) {
            double maxElement = priorityQueue.poll();

            priorityQueue.add((int) Math.ceil(maxElement / 2));

            k--;
        }

        int sum = 0;
        
        for (Integer number : priorityQueue) {
            sum += number;
        }

        return sum;
    }

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        nums.add(2);
        nums.add(2);
        nums.add(3);
        System.out.println(solve2(nums, 1));
    }
}
