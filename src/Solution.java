import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
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

//5
//generate aaa 1
//renew aaa 2
//count 6 

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

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

//        bufferedWriter.write(
//                result.stream()
//                        .map(Object::toString)
//                        .collect(joining("\n"))
//                        + "\n"
//        );

        bufferedReader.close();
//        bufferedWriter.close();


//        int num = 1200;
//        
//        String num_str = String.valueOf(num);
//        String result = "";
//        for (Character a :num_str.toCharArray()) {
//            if(a.equals("0")){
//                result = "";
//            }else{
//                result = "9";
//            }
//        }

    }


}
