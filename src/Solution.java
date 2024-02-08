import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;
class Result {

    /**
     *2. Question 2
     * Develop a session-based authentication system
     * where each user session generates a new token
     * with a specified time-to-live (TTL) expiration. The
     * TTL expiration is calculated as the current time
     * plus the time-to-live seconds. If a token is
     * renewed, the TTL is extended to the time-to-live
     * seconds after the time of the renewal.
     * There are three types of queries in this system:
     * • "generate <token _id> <current_time>": Creates a
     * new token with the given token_id and
     * current time.
     * • "renew <token id> <current time>": Renews an
     * unexpired token with the given token _id and
     * current time. If there is no unexpired token with
     * the specified token _id, the request is ignored.
     * • "count <current time>": Returns the number of
     * unexpired tokens at the given current_time.
     * Note: If a token expires at a specific time and any
     * action is performed at that same time, the token's
     * expiration occurs before any other actions are
     * carried out.
     * Example
     * Suppose time_ to live = 5, and queries =
     * Il'"generate aaa 1"], ["renew aaa 2"I, ["count
     * 6"]. ["generate bbb 7"], ["renew aaa 8'),
     * ["renew bbb 10"). ["count 15"I).
     */
    
    /*
     * Complete the 'getUnexpiredTokens' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER time_to_live
     *  2. STRING_ARRAY queries
     */
    public static List<Integer> getUnexpiredTokens(int time_to_live, List<String> queries) {
        Map<String, Integer> tokens_id = new HashMap<>();
        List<Integer> counts = new ArrayList<>();

        for (String query : queries) {
            String[] query_array = query.split(" ");
            String action = query_array[0];

            boolean isGenerateOrRenew = action.equals("generate") || action.equals("renew");
            boolean isCount = action.equals("count");
            int count = 0;

            if (isGenerateOrRenew) {
                String token_id = query_array[1];
                Integer current_time = Integer.valueOf(query_array[2]);

                if (action.equals("generate")) {

                    Integer calculated_ttl = current_time + time_to_live;
                    tokens_id.put(token_id, calculated_ttl);
                } else {
                    if (tokens_id.get(token_id) != null) {

                        Integer calculated_ttl = current_time + time_to_live;
                        tokens_id.put(token_id, calculated_ttl);
                    }
                }
            } else if (isCount) {
                Integer current_time = Integer.valueOf(query_array[1]);
                List<Integer> list = new ArrayList<>(tokens_id.values());

                Collections.sort(list);

                int left = 0;
                int right = list.size();

                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    int num = list.get(mid);
                    if (num < current_time) {
                        count++;
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }

                counts.add(count);
                System.out.println("count ttl at the time" + time_to_live + " " + count);
            }
        }
        return counts;
    }

}

//input :
//5
//generate aaa 1
//renew aaa 2
//count 6 

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int time_to_live = Integer.parseInt(bufferedReader.readLine().trim());

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        List<Integer> result = Result.getUnexpiredTokens(time_to_live, queries);
        
        bufferedReader.close();
    }
}
