package uz.pdp.entity;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.Location;
import uz.pdp.enums.AccountStep;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Account implements Serializable {

    private Long id;
    private String fullName;
    private String phoneNumber;
    private String password;



    private AccountStep step;

}
