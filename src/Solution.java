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
