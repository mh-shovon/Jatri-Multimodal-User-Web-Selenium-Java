package user.co.jatri.pages.auth;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

public class FindOtp {
    private final Dotenv dotenv = Dotenv.load();

    private final String mongoUrl = dotenv.get("MONGO_URL");
    private final String dbName = dotenv.get("MONGO_DATABASE_NAME");
    private final String collectionName = dotenv.get("MONGO_COLLECTION_NAME");

    public String fetchMostRecentOtp(String userMobileNumber) {
        if (userMobileNumber == null || userMobileNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("User mobile number is required to fetch OTP.");
        }

        String code = null;

        try (MongoClient mongoClient = MongoClients.create(mongoUrl)) {
            System.out.println("Connecting to MongoDB...");
            MongoDatabase database = mongoClient.getDatabase(dbName);
            System.out.println("Connected to Database: " + dbName);


            MongoCollection<Document> collection = database.getCollection(collectionName);
            System.out.println("Connected to Collection: " + collectionName);

            Document result = collection
                    .find(Filters.eq("phone", userMobileNumber))
                    .sort(Sorts.descending("createdAt"))
                    .limit(1)
                    .first();

            if (result != null && result.get("code") != null) {
                code = result.getString("code");
                System.out.println("OTP fetched from MongoDB: " + code);
            } else {
                System.out.println("No OTP found for this phone number.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch OTP: " + e.getMessage());
        }
        return code;
    }
}
