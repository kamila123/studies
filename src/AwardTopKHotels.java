import java.util.*;
import java.util.stream.Collectors;

public class AwardTopKHotels {

    public static List<Integer> awardTopKHotels(String positiveKeywords, String negativeKeywords,
                                                List<Integer> hotelIds,
                                                List<String> reviews, int k) {
        String space = " ";

        reviews = reviews.stream().map(String::toLowerCase).collect(Collectors.toList());
        Set<String> positiveWords = Arrays.stream(positiveKeywords.split(space)).map(String::toLowerCase).collect(Collectors.toSet());
        Set<String> negativeWords = Arrays.stream(negativeKeywords.split(space)).map(String::toLowerCase).collect(Collectors.toSet());

        Map<Integer, Integer> hotelScore = new HashMap<>();
        
        for (int i = 0; i < reviews.size(); i++) {
            int hotel = hotelIds.get(i);
            int score = hotelScore.getOrDefault(hotel, 0);

            int positive = 0, negative = 0;
            for (String word : reviews.get(i).split(space)) {
                if (word.charAt(word.length() - 1) == '.' || word.charAt(word.length() - 1) == ',') {
                    word = word.substring(0, word.length() - 1);
                }
                if (positiveWords.contains(word)) {
                    positive++;
                }
                if (negativeWords.contains(word)) {
                    negative++;
                }
            }
            hotelScore.put(hotel, score + 3 * positive - negative);
        }

//        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> Objects.equals(a.getValue(), b.getValue()) ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue());
//
//        for (Map.Entry<Integer, Integer> entry : hotelScore.entrySet()) {
//            pq.offer(entry);
//            if (pq.size() > k) {
//                pq.poll();
//            }
//        }
//
//        List<Integer> result = new ArrayList<>();
//       
//        while (!pq.isEmpty()) {
//            result.add(0, pq.poll().getKey());
//        }

        Map<Integer, Integer> treeMap = new TreeMap<>((o1, o2) -> o2.compareTo(o1));

	    /* For Java 8, try this lambda
		Map<Integer, String> treeMap = new TreeMap<>(
		                (Comparator<Integer>) (o1, o2) -> o2.compareTo(o1)
		        );
		*/
        treeMap.putAll(hotelScore);
        
        return new ArrayList<>(treeMap.keySet());
    }

    public static List<Integer> awardTopKHotelsFunc(String positiveKeywords, String negativeKeywords,
                                                List<Integer> hotelIds,
                                                List<String> reviews, int k){
        Set<String> positiveWords;
        Set<String> negativeWords;
        String space = " ";

        reviews = reviews.stream().map(String::toLowerCase).collect(Collectors.toList());
        positiveWords = Arrays.stream(positiveKeywords.split(space)).map(String::toLowerCase).collect(Collectors.toSet());
        negativeWords = Arrays.stream(negativeKeywords.split(space)).map(String::toLowerCase).collect(Collectors.toSet());

        Map<Integer, Integer> hotelScore = new HashMap<>();
        
        for (int i = 0; i < reviews.size(); i++) {
            int hotel = hotelIds.get(i);
            int score = hotelScore.getOrDefault(hotel, 0);

            int positive = 0, negative = 0;
            for (String word : reviews.get(i).split(space)) {
                if (word.charAt(word.length() - 1) == '.' || word.charAt(word.length() - 1) == ',') {
                    word = word.substring(0, word.length() - 1);
                }
                if (positiveWords.contains(word)) {
                    positive++;
                }
                if (negativeWords.contains(word)) {
                    negative++;
                }
            }
            hotelScore.put(hotel, score + 3 * positive - negative);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> Objects.equals(a.getValue(), b.getValue()) ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue());

        for (Map.Entry<Integer, Integer> entry : hotelScore.entrySet()) {
            pq.offer(entry);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        List<Integer> result = new ArrayList<>();

        while (!pq.isEmpty()) {
            result.add(0, pq.poll().getKey());
        }

        return result;
    }
}
