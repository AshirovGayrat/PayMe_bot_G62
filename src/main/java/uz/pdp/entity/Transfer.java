package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.enums.TransferStep;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transfer  implements Serializable {

    private String id;

    private String from;
    private String to;

    private BigDecimal amount;

    private LocalDateTime time;

    private TransferStep step;

}
