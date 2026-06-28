package uz.pdp.entity;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.enums.LoginStep;

@Getter
@Setter
public class LoginAccount {

    private String phoneNumber;
    private String password;

    private LoginStep step;

}
