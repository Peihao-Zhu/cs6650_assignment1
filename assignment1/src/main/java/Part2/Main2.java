package Part2;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import common.Argument;
import common.ArgumentUtil;
import common.ResultData;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Main2 {
    public static void main(String[] args) throws ParseException, InterruptedException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Argument argument = ArgumentUtil.parse(args);
        String url = "http://" + argument.getIp() + ":" + argument.getPort() + "/cs6650_lab2_war/skiers/1/seasons/2019/day/1/skier/123";
        int totalThreadNum = argument.getNumThreads() / 4 + argument.getNumThreads() + argument.getNumThreads() / 10;
        ResultData resultData = new ResultData();

        long startTime = System.currentTimeMillis();
        final CountDownLatch totalThreads = new CountDownLatch(totalThreadNum);
        Client2 client = new Client2(argument, url, totalThreads, resultData);
        System.out.println("--------- phase 1 start -----");
        client.executePhase1();
        System.out.println("--------- phase 2 start -----");
        client.executePhase2();
        System.out.println("--------- phase 3 start -----");
        client.executePhase3();
        totalThreads.await();

        long endTime = System.currentTimeMillis();
        long wallTime = endTime - startTime;

        System.out.println("-------------------------");
        CsvWriter.write(resultData.getRecords());
        Part2Data.compute(resultData.getResponseTimeList(), wallTime);
        Part2Data.output();
        CsvWriter.writeRequestLatency(resultData.getResponseTimeList());
    }

}
