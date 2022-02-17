package common;

import Part2.CsvRecord;
import javafx.util.Pair;
import lombok.Data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class ResultData {
    private int successfulRequests;
    private int unsuccessfulRequests;
    private long totalRunTime;
    private double throughput;
    private List<Pair<Long, Long>> responseTimeList;
    private List<CsvRecord> records;

    public ResultData() {
        successfulRequests = 0;
        unsuccessfulRequests = 0;
        totalRunTime = 0;
        throughput = 0;
        responseTimeList = new CopyOnWriteArrayList<>();
        records = new CopyOnWriteArrayList<>();
    }

    public synchronized void addSuccessfulRequestBy1() {
        successfulRequests++;
    }

    public synchronized void addUnsuccessfulRequestBy1() {
        unsuccessfulRequests++;
    }

    @Override
    public String toString() {
        return "Client1 Data{" +
                "\n number of successful requests sent :" + successfulRequests +
                "\n number of unsuccessful requests : " + unsuccessfulRequests +
                "\n the total run time :" + totalRunTime +  "(millisecs)" +
                "\n the total throughput in requests per second : " + throughput +
                "\n}";

    }
}
