package servlet.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resort {
    private String resortName;
    private int resortID;
}
