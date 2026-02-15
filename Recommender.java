import java.util.HashMap;
import java.util.Map;

public class Recommender {

    public static void main(String[] args) {

        // Store user movie ratings
        Map<String, Map<String, Integer>> users = new HashMap<>();

        users.put("Rahul", Map.of(
                "Inception", 5,
                "Titanic", 3,
                "Avatar", 4
        ));

        users.put("Aryan", Map.of(
                "Inception", 5,
                "Titanic", 2,
                "Interstellar", 5
        ));

        users.put("Sneha", Map.of(
                "Avatar", 5,
                "Titanic", 3,
                "Interstellar", 4
        ));

        // Ratings of current user
        users.put("Anay", Map.of(
                "Inception", 4,
                "Avatar", 5
        ));

        String currentUser = "Anay";

        String result = findRecommendation(users, currentUser);

        System.out.println("Recommended movie for " + currentUser + ": " + result);
    }


    // Method to find best movie recommendation
    public static String findRecommendation(Map<String, Map<String, Integer>> users, String currentUser) {

        Map<String, Integer> currentRatings = users.get(currentUser);

        String suggestedMovie = null;
        int highestScore = -1;

        // Compare with every other user
        for (String otherUser : users.keySet()) {

            if (otherUser.equals(currentUser)) {
                continue; // skip same user
            }

            Map<String, Integer> otherRatings = users.get(otherUser);

            int similarityScore = 0;

            // Check similarity based on common movies
            for (String movie : currentRatings.keySet()) {
                if (otherRatings.containsKey(movie)) {
                    int difference = Math.abs(currentRatings.get(movie) - otherRatings.get(movie));
                    similarityScore += (5 - difference);
                }
            }

            // Find movies the current user has not watched
            for (String movie : otherRatings.keySet()) {
                if (!currentRatings.containsKey(movie)) {

                    int finalScore = similarityScore * otherRatings.get(movie);

                    if (finalScore > highestScore) {
                        highestScore = finalScore;
                        suggestedMovie = movie;
                    }
                }
            }
        }

        if (suggestedMovie == null) {
            return "No recommendation available";
        }

        return suggestedMovie;
    }
}
