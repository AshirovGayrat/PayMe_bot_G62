package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Card implements Serializable {

    private String id;
    private String fullName;
    private String number;
    private BigDecimal balance;

    private String phoneNumber;

    private LocalDate expDate;

}
