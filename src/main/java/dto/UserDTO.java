package dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
public class UserDTO {
    BigInteger id;
    String name;
    String email;
    String password;
}
