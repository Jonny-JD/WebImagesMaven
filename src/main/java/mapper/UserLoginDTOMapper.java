package mapper;

import dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class UserLoginDTOMapper implements Mapper<HttpServletRequest, UserDTO> {

    private static final UserLoginDTOMapper INSTANCE = new UserLoginDTOMapper();

    public static UserLoginDTOMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDTO map(HttpServletRequest request) {

        return UserDTO.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
    }
}
