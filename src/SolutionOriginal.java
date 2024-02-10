import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


class Result2 {

    // for time O(N) where N is the number of queries 
    // count search - while is time O(M) where M is the number of unique tokens
    // total time is O(NˆM) N power of M     
    // space O(M+N) where M is the number of unique tokens and N is the number of count requests and that does grow proportionally to the size of the query list 
    //we can do better
    public static List<Integer> getUnexpiredTokens(int time_to_live, List<String> queries) {
        Map<String, Integer> tokens_id = new HashMap<>();
        List<Integer> counts = new ArrayList<>();

        for (String query : queries) {
            String[] query_array = query.split(" ");
            String action = query_array[0];

            boolean isGenerateOrRenew = action.equals("generate") || action.equals("renew");
            boolean isCount = action.equals("count");

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
                int count = 0;
                Integer current_time = Integer.valueOf(query_array[1]);
                Iterator<Map.Entry<String, Integer>> iterator = tokens_id.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, Integer> entry = iterator.next();
                    if (entry.getValue() > current_time) {
                        count++;
                    }
                }

                counts.add(count);
                System.out.println("count ttl at the time" + time_to_live + " " + count);
            }
        }
        return counts;
    }
}

public class SolutionOriginal {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<String> queries = Arrays.asList("generate aaa 1", "renew aaa 2", "count 6", "generate bbb 7", "renew aaa 8", "renew bbb 10", "count 15");

        Result2.getUnexpiredTokens(5, queries);

        bufferedReader.close();
    }

}
