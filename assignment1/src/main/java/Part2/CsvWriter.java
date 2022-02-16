package Part2;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import javafx.util.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {

    public static void main(String[] args) throws IOException {
        List<Pair<Long, Long>> latencies = new ArrayList<>();
        latencies.add(new Pair(1,2));
        writeRequestLatency(latencies);
    }

    public static void write(List<CsvRecord> records) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter("./output.csv");
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(records);
        writer.close();
    }
    public static void writeRequestLatency(List<Pair<Long, Long>> latencies) throws IOException {
        Writer writer = new FileWriter("./latency.csv");
        String[] headerRecord = {"timestamp", "latency"};
        // create a csv writer
        CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR);
        csvWriter.writeNext(headerRecord);
        for(Pair<Long, Long> pair : latencies) {
            csvWriter.writeNext(new String[]{String.valueOf(pair.getKey()), String.valueOf(pair.getValue())});
        }
        writer.close();
    }
}
