package Part1;

import Part2.CsvWriter;
import Part2.Part2Data;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws ParseException, InterruptedException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Argument argument = ArgumentUtil.parse(args);
        String url = "http://" + argument.getIp() + ":" + argument.getPort() + "/cs6650_lab2_war/skiers/1/seasons/2019/day/1/skier/123";
        int totalThreadNum = argument.getNumThreads() / 4 + argument.getNumThreads() + argument.getNumThreads() / 10;
        ResultData resultData = new ResultData();

        long startTime = System.currentTimeMillis();

        final CountDownLatch totalThreads = new CountDownLatch(totalThreadNum);
        Client client = new Client(argument, url, totalThreads, resultData);
        client.executePhase1();
        client.executePhase2();
        client.executePhase3();
        totalThreads.await();
        long endTime = System.currentTimeMillis();
        long wallTime = endTime - startTime;

        resultData.setTotalRunTime(wallTime);
        resultData.setThroughput((resultData.getSuccessfulRequests() + resultData.getUnsuccessfulRequests()) * 1000 / resultData.getTotalRunTime());

        System.out.println("-------------------------");
        System.out.println(resultData.toString());
        //CsvWriter.write(resultData.getRecords());
        Part2Data.compute(resultData.getResponseTimeList(), wallTime);
        Part2Data.output();
        CsvWriter.writeRequestLatency(resultData.getResponseTimeList());
//
//        Client client = new Client(argument, url, new CountDownLatch(1), resultData);
//        client.testSingleRequestLatency();
    }

}
