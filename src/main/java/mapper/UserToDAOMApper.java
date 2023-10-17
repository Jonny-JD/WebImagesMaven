package mapper;

import dto.UserDTO;
import entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserToDAOMApper implements Mapper<UserDTO, User> {

    private static final UserToDAOMApper INSTANCE = new UserToDAOMApper();

    @Override
    public User map(UserDTO object) {
        return User.builder()
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }

    public static UserToDAOMApper getInstance() {
        return INSTANCE;
    }
}
