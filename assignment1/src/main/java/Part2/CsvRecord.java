package Part2;


import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CsvRecord {
    @CsvBindByName(column = "start time")
    private int startTime;

    @CsvBindByName(column = "request type")
    private String requestType;

    @CsvBindByName(column = "latency")
    private long latency;

    @CsvBindByName(column = "response code")
    private int responseCode;

}
