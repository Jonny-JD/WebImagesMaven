package mapper;

import dto.UserDTO;
import entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserFromDAOMapper implements Mapper <User, UserDTO> {
    private static final UserFromDAOMapper INSTANCE = new UserFromDAOMapper();

    public static UserFromDAOMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public UserDTO map(User object) {
        return UserDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .email(object.getEmail())
                .build();
    }
}
