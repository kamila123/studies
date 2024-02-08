import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

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
class Result {

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
                    // create token with given current_time + ttl
                    Integer calculated_ttl = current_time + time_to_live;
                    tokens_id.put(token_id, calculated_ttl);
                    out.println(" generate token " + token_id + " with ttl " + tokens_id.get(token_id));
                } else {
                    // if token exist and is not expired (current token ttl > ttl)
                    boolean tokenIsExpired = tokens_id.get(token_id) < current_time;
                    //if token expired then remove from map
                    if (tokenIsExpired) {
                        out.println("expired token " + token_id + " removed " + tokens_id.get(token_id));
                        tokens_id.remove(token_id);
                    }
                    //if token exist and not expired renew it
                    if (tokens_id.get(token_id) != null && !tokenIsExpired) {
                        Integer calculated_ttl = current_time + time_to_live;
                        tokens_id.put(token_id, calculated_ttl);
                        out.println(" token " + token_id + " renewed " + calculated_ttl);
                    }
                }
            } else if (isCount) {
                Integer current_time = Integer.valueOf(query_array[1]);
                //count the amount of tokens that the ttl is grater then the count ttl
                for (Integer i : tokens_id.values()) {
                    if (i > current_time) {
                        count++;
                    }
                }
                counts.add(count);
                out.println("count ttl at the time " + time_to_live + " " + count);
            }
        }
        return counts;
    }
}

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

        Result.getUnexpiredTokens(time_to_live, queries);

        bufferedReader.close();
    }
}
