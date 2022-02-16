package Part2;

import javafx.util.Pair;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Part2Data {
    private static long meanResponseTime;
    private static long medianResponseTime;
    private static double throughput;
    private static long p99Responsetime;
    private static long minResponseTime;
    private static long maxResponsetime;

    public static void compute(List<Pair<Long, Long>> responseTimeList, long wallTime) {
        int len = responseTimeList.size();
        if(len == 0) return ;
        Collections.sort(responseTimeList, (a,b) -> {
            return (int) (a.getKey() - b.getKey());
        });
        minResponseTime = responseTimeList.get(0).getKey();
        maxResponsetime = responseTimeList.get(len-1).getKey();
        if(len % 2 == 0)
            medianResponseTime = (responseTimeList.get(len / 2).getKey() + responseTimeList.get(len / 2 - 1).getKey()) / 2;
        else
            medianResponseTime = responseTimeList.get(len / 2).getKey();
        long totalResponseTime = responseTimeList.stream().mapToLong(i -> i.getKey()).sum();
        meanResponseTime = totalResponseTime / len;
        throughput = len * 1000 / wallTime;
        int index = (int) Math.ceil(99 / 100.0 * len);
        p99Responsetime = responseTimeList.get(index-1).getKey();
    }

    public static void output() {
       String str = "Client2 Data{" +
                "\n mean response time=" + meanResponseTime + "(millisecs)" +
                "\n median response time=" + medianResponseTime +  "(millisecs)" +
                "\n throughput=" + throughput +
                "\n p99 (99th percentile) response time.=" + p99Responsetime + "(millisecs)" +
                "\n min response time=" + minResponseTime + "(millisecs)" +
                "\n max response time=" + maxResponsetime + "(millisecs)" +
               "\n}";

       System.out.println(str);
    }



}
