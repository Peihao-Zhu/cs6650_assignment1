package common;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class MyHttpClient {

    public static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    public static  CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(MyHttpClient.connManager).build();
    static {
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(40);
    }


    public static CloseableHttpResponse execute(String url, Map<String, Integer> param) throws IOException {
        HttpPost post = new HttpPost(url);
        String requestbody = new Gson().toJson(param);
        post.setEntity(new StringEntity(requestbody));
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = null;
        try {
            // Execute the method.

            int statusCode = -1, failTimes = 0;

            do {
                response = httpClient.execute(post);
                EntityUtils.consume(response.getEntity());
                statusCode = response.getStatusLine().getStatusCode();
                failTimes++;
            } while(statusCode != 201 && failTimes <= 5);



        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}