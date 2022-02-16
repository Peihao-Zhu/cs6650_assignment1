package Part1;

import Part2.CsvRecord;
import com.google.gson.Gson;
import javafx.util.Pair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyHttpClient {

    public static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    public static  CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(MyHttpClient.connManager).build();
    static {
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(40);
    }


    public static boolean execute(String url, Map<String, Integer> param, ResultData resultData) throws IOException {
        HttpPost post = new HttpPost(url);
        String requestbody = new Gson().toJson(param);
        post.setEntity(new StringEntity(requestbody));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        try {
            // Execute the method.
            long beforeSending = System.currentTimeMillis();

            int statusCode = -1, failTimes = 0;
            CloseableHttpResponse response;
            do {
                response = httpClient.execute(post);
                EntityUtils.consume(response.getEntity());
                statusCode = response.getStatusLine().getStatusCode();
                failTimes++;
            } while(statusCode != 201  && failTimes <= 5);
            long afterSending = System.currentTimeMillis();
            long latency = afterSending - beforeSending;

            resultData.getResponseTimeList().add(new Pair<>(latency, afterSending));
            resultData.getRecords().add(CsvRecord.builder().requestType("POST").latency(latency).startTime(param.get("startTime"))
                    .responseCode(statusCode).build());

            if (statusCode == 201) {
                // Read the response body.
//                byte[] responseBody = response.getEntity();

                // Deal with the response.
                // Use caution: ensure correct character encoding and is not binary data
                // System.out.println(Thread.currentThread().getName() + " true");
                resultData.addSuccessfulRequestBy1();
                return true;
            }


        }  finally {
        }
        resultData.addUnsuccessfulRequestBy1();
        return false;
    }
}