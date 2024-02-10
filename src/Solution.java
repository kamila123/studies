import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

/**
 * 2. Question 2
 * Develop a session-based authentication system where each user session generates a new token with a specified time-to-live (TTL) expiration. The
 * TTL expiration is calculated as the current time plus the time-to-live seconds. If a token is renewed, the TTL is extended to the time-to-live
 * seconds after the time of the renewal. There are three types of queries in this system:
 * • "generate <token _id> <current_time>": Creates a new token with the given token_id and current time.
 * • "renew <token id> <current time>": Renews an unexpired token with the given token _id and current time. If there is no unexpired token with
 * the specified token _id, the request is ignored.
 * • "count <current time>": Returns the number of unexpired tokens at the given current_time.
 * Note: If a token expires at a specific time and any action is performed at that same time, the token's expiration occurs before any other actions are 
 * carried out.
 * Example
 * Suppose time_ to live = 5, and queries = Il'"generate aaa 1"], ["renew aaa 2"I, ["count 6"]. ["generate bbb 7"], ["renew aaa 8'), ["renew bbb 10"). ["count 15"I).
 */
class Result {

    public static List<Integer> getUnexpiredTokens(int time_to_live, List<String> queries) {
        Map<String, Integer> tokens_id = new HashMap<>();
        List<Integer> counts = new ArrayList<>();

        for (String query : queries) {
            String[] query_array = query.split(" ");
            String action = query_array[0];

            boolean isGenerate = action.equals("generate");
            boolean isRenew = action.equals("renew");
            boolean isCount = action.equals("count");
            boolean isValidAction = isGenerate || isRenew || isCount;
            int count = 0;

            if (isValidAction) {
                if (isGenerate || isRenew) {
                    String token_id = query_array[1];
                    Integer current_time = Integer.valueOf(query_array[2]);

                    boolean tokenExists = tokens_id.get(token_id) != null;

                    if (tokenExists) {
                        boolean tokenIsExpired = tokens_id.get(token_id) < current_time;
                        //if token expired then remove from map
                        if (tokenIsExpired) {
                            out.println("expired token " + token_id + " removed " + tokens_id.get(token_id));
                            tokens_id.remove(token_id);
                        } else if (isRenew) {
                            //if token exist and not expired renew it
                            Integer calculated_ttl = updateUnexpiredTokens(time_to_live, tokens_id, token_id, current_time);
                            out.println("token " + token_id + " renewed with ttl " + calculated_ttl);
                        }
                    } else if (isGenerate) {
                        // create token with given current_time + ttl
                        updateUnexpiredTokens(time_to_live, tokens_id, token_id, current_time);
                        out.println("generate token " + token_id + " with ttl " + tokens_id.get(token_id));
                    }
                } else {
                    //count the amount of tokens that the ttl is grater then the count ttl
                    Integer current_time = Integer.valueOf(query_array[1]);
                    countUnexpiredTokens(counts, new ArrayList<>(tokens_id.values()), count, current_time);
                }
            }
        }
        return counts;
    }

    private static void countUnexpiredTokens(List<Integer> counts, List<Integer> tokensTTL, int count, Integer current_time) {
        Collections.sort(tokensTTL);

        int right = tokensTTL.size() - 1;
        int left = 0;

        // 1,3,5,6,7,8,9
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (tokensTTL.get(mid) >= current_time) {
                left = mid + 1;
                count++;
            } else {
                right = mid - 1;
            }
        }

        counts.add(count);

        out.println("counted ttl at the time " + current_time + " = " + count);
    }

    private static Integer updateUnexpiredTokens(int time_to_live, Map<String, Integer> tokens_id, String token_id, Integer current_time) {
        Integer calculated_ttl = current_time + time_to_live;
        tokens_id.computeIfPresent(token_id, (k, v) -> calculated_ttl);
        tokens_id.putIfAbsent(token_id, calculated_ttl);
        return calculated_ttl;
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
        }).collect(toList());

        Result.getUnexpiredTokens(time_to_live, queries);

        bufferedReader.close();
    }
}


//                    if (action.equals("generate")) {
//                        // create token with given current_time + ttl
//                        updateUnexpiredTokens(time_to_live, tokens_id, token_id, current_time);
//                        out.println("generate token " + token_id + " with ttl " + tokens_id.get(token_id));
//                    } else {
//                        // if token exist and is not expired (current token ttl > ttl)
//                        if (tokens_id.get(token_id) != null) {
//                            boolean tokenIsExpired = tokens_id.get(token_id) < current_time;
//                            //if token expired then remove from map
//                            if (tokenIsExpired) {
//                                out.println("expired token " + token_id + " removed " + tokens_id.get(token_id));
//                                tokens_id.remove(token_id);
//                            } else {
//                                //if token exist and not expired renew it
//                                Integer calculated_ttl = updateUnexpiredTokens(time_to_live, tokens_id, token_id, current_time);
//                                out.println("token " + token_id + " renewed with ttl " + calculated_ttl);
//                            }
//                        }
//                    }
