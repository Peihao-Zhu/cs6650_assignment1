package servlet.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorVO {
    String message;
}
