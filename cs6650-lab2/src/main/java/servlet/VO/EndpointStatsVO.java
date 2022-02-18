package servlet.VO;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class EndpointStatsVO {


    private List<EndpointsStat> endpointStats;

}
