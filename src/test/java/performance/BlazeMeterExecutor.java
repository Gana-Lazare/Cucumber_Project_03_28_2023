package performance;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

//Need to work more on that topic as for now it still throwing 401
public class BlazeMeterExecutor {
        public String testId = "12584787";
        public String apikey = "2c0efbe3c64b6c60d864aea9";
        public String apisec="9abe9a20bedd45063a41097c05e01abbf38674ea26abe5dfa281f55f79b5d4c62278a29d";

    public void executeBlazeMeterTest(String testId, String apiKey, String secretKey) throws Exception {
            testId = this.testId;
            apiKey=this.apikey;
            secretKey = this.apisec;
        String url = "https://a.blazemeter.com/api/v4/tests/" + testId + "/start";

        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        String authHeaderValue = "ApiKey " + apiKey + ":" + secretKey;
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

        HttpResponse response = client.execute(httpPost);

        // Handle the response as needed
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        // Process the response accordingly
        if (statusCode == 200) {
            System.out.println("Test execution started successfully");
        } else {
            System.err.println("Failed to start the test. Response: " + responseBody);
        }
    }

    public static void main(String[] args) {
        String testId = "your_test_id";
        String apiKey = "your_api_key";
        String secretKey = "your_secret_key";

        BlazeMeterExecutor executor = new BlazeMeterExecutor();
        try {
            executor.executeBlazeMeterTest(testId, apiKey, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
