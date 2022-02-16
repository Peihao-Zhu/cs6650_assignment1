package Part1;

import com.google.gson.Gson;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class MyThread extends Thread{

    private int startSkierId;
    private int endSkierId;
    private int startTime;
    private int endTime;
    private int requestsPerThread;
    private int liftNum;
    private Random random;
    private String url;
    private CountDownLatch count;
    private CountDownLatch totalThreads;
    private ResultData resultData;
    private int phase;

    public static void main(String[] args) {
        Map<String, Integer> param = new HashMap<>();
        param.put("a", 9);
        System.out.println(new Gson().toJson(param));
    }

    public MyThread(int startSkierId, int endSkierId, int startTime, int endTime,
                    int requestsPerThread, int liftNum, String url,
                    CountDownLatch count, CountDownLatch totalThreads, ResultData resultData, int phase) {
        this.startSkierId = startSkierId;
        this.endSkierId = endSkierId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.requestsPerThread = requestsPerThread;
        this.liftNum = liftNum;
        this.random = new Random();
        this.url = url;
        this.count = count;
        this.totalThreads = totalThreads;
        this.resultData = resultData;
        this.phase = phase;
    }

    @Override
    public void run() {

//        long b = System.currentTimeMillis();
        // the thread need to send requestsPerThread POST requests.
        while(requestsPerThread-- > 0) {
            int liftNumber = -1;

            int randomLiftId = random.nextInt(liftNum);
            int waitTime = random.nextInt(11);
            int skierId = random.nextInt(endSkierId - startSkierId + 1) + startSkierId;;
            int timestamp = random.nextInt(endTime - startTime + 1) + startTime;
            Map<String, Integer> param = new HashMap<>();
            param.put("skierId", skierId);
            param.put("liftNumber", liftNumber);
            param.put("startTime", timestamp);
            param.put("waitTime", waitTime);

            try {
                MyHttpClient.execute(url, param, resultData);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        count.countDown();
        totalThreads.countDown();
//        long e = System.currentTimeMillis();
//        System.out.println(Thread.currentThread().getName() + " "+ phase + " " + (e-b));
//        long wallTime = e - b;
//        double latency = (double)(wallTime * 1.0 / 10000);
//        System.out.println("send 10000 requests will take:" + wallTime + " . So the latency for each request is " + latency + ".  Estimate throughput is " + (64 * 1000) / latency);
//        System.out.println();

    }

}
