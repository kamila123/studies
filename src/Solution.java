import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.out;

/**
 * 2. Question 2
 * Develop a session-based authentication system where each user session generates a new token with a specified time-to-live (TTL) expiration. The
 * TTL expiration is calculated as the current time plus the time-to-live seconds. If a token is renewed, the TTL is extended to the time-to-live
 * seconds ** after the time of the renewal **. There are three types of queries in this system:
 * • "generate <token _id> <current_time>": Creates a new token with the given token_id and current time.
 * • "renew <token id> <current time>": Renews an unexpired token with the given token _id and current time. If there is no unexpired token with the specified token _id, the request is ignored.
 * • "count <current time>": Returns the number of unexpired tokens at the given current_time.
 * Note: If a token expires at a specific time and any action is performed at that same time, the token's expiration occurs before any other actions are carried out.
 * Example
 * Suppose time_ to live = 5, and queries = Il'"generate aaa 1"], ["renew aaa 2", ["count 6"], ["generate bbb 7"], ["renew aaa 8'), ["renew bbb 10"). ["count 15"I).
 */
class Result {

    public static List<Integer> getUnexpiredTokens(int time_to_live, List<String> queries) {
        Map<String, Integer> mapTokenTTL = new HashMap<>();
        List<Integer> counts = new ArrayList<>();

        // for time O(N) where N is the number of queries 
        // Arrays.sort is O(nlogN) where N is the number of unique tokens 
        // binary search is time O(logN) where N is the number of unique tokens 
        // total complexity is O(nlogNˆ2)
        // space O(M+N) where M is the number of unique tokens and N is the number of count requests, map and list it does grow proportionally to the values of the input  
        for (String query : queries) {
            String[] queryArray = query.split(" ");
            String action = queryArray[0];

            int count = 0;
            boolean isGenerate = action.equals("generate");
            boolean isRenew = action.equals("renew");
            boolean isCount = action.equals("count");
            boolean isValidAction = isGenerate || isRenew || isCount;

            if (isValidAction) {
                if (isGenerate || isRenew) {
                    String tokenId = queryArray[1];
                    Integer currentTime = Integer.valueOf(queryArray[2]);
                    boolean tokenExists = mapTokenTTL.get(tokenId) != null;

                    //if token exist and is renewed then update ttl
                    if (tokenExists && isRenew) {
                        // unexpired token
                        Integer calculatedTtl = updateUnexpiredTokens(time_to_live, mapTokenTTL, tokenId, currentTime);
                        out.println("token " + tokenId + " renewed with ttl " + calculatedTtl);
                    } else if (isGenerate) {
                        // create token with given currentTime + ttl
                        updateUnexpiredTokens(time_to_live, mapTokenTTL, tokenId, currentTime);
                        out.println("generate token " + tokenId + " with ttl " + mapTokenTTL.get(tokenId));
                    }
                } else {
                    //count the amount of tokens that the ttl is grater than the count ttl
                    Integer currentTime = Integer.valueOf(queryArray[1]);
                    countUnexpiredTokensWithBinarySearch(counts, new ArrayList<>(mapTokenTTL.values()), count, currentTime);
//                    countUnexpiredTokens(counts, new ArrayList<>(mapTokenTTL.values()), count, currentTime);
                }
            }
        }

        out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return counts;
    }

    private static void countUnexpiredTokensWithBinarySearch(List<Integer> counts, List<Integer> tokensTTL, int count, Integer current_time) {
        //O(NlogN)
        Collections.sort(tokensTTL);

        int right = tokensTTL.size() - 1;
        int left = 0;

        //O(logN)
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

    private static void countUnexpiredTokens(List<Integer> counts, List<Integer> tokensTTL, int count, Integer current_time) {
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
//generate bbb 7
//renew aaa 8
//renew bbb 10
//count 15
public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<String> queries = Arrays.asList("generate aaa 1", "renew aaa 2", "count 6", "generate bbb 7", "renew aaa 8", "renew bbb 10", "count 15");

        Result.getUnexpiredTokens(5, queries);

        List<String> queriesWithExpired = Arrays.asList("generate ac 1", "renew ac 2", "count 12", "generate ac 1", "renew ac 6", "count 10");

        Result.getUnexpiredTokens(3, queriesWithExpired);

        bufferedReader.close();
    }
}
