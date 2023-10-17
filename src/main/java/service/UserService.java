package service;

import dao.UserDAO;
import dto.UserDTO;
import entity.User;
import exception.ValidationException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import mapper.UserFromDAOMapper;
import mapper.UserToDAOMApper;
import org.apache.commons.io.FileUtils;
import util.PropertiesUtil;
import validator.RegistrationValidator;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    public static UserService getInstance() {
        return INSTANCE;
    }

    private static final UserService INSTANCE = new UserService();
    private final UserToDAOMApper userToDAOmapper = UserToDAOMApper.getInstance();
    private final UserFromDAOMapper userFromDAOMapper = UserFromDAOMapper.getInstance();

    private final UserDAO userDAO = UserDAO.getInstance();
    private static final String BASE_PATH = "user.image.base.path";

    private final RegistrationValidator registrationValidator = RegistrationValidator.getInstance();


    public Optional<UserDTO> login(UserDTO userDTO) {
        return userDAO.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword())
                .map(userFromDAOMapper::map);
    }

    @SneakyThrows
    public BigInteger addNewUser(UserDTO userDTO) {
        var validationResult = registrationValidator.isValid(
                userDAO.findByEmail(userDTO.getEmail()).map(userFromDAOMapper::map));

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var userEntity = userToDAOmapper.map(userDTO);

        User newUser = userDAO.addNewUser(userEntity);

        if (newUser.getId() != null) {
            Files.createDirectories(Path.of(PropertiesUtil.get(BASE_PATH) + userDTO.getEmail()));
        }

        return userEntity.getId();
    }

    public void deleteUser(UserDTO userDTO) {
        if (userDAO.deleteUserById(userDTO.getId())) {
            deleteUserData(userDTO.getEmail());
            //TODO Create delete servlet
        }
    }

    @SneakyThrows
    private void deleteUserData(String email) {
        FileUtils.deleteDirectory(Path.of("..", "users", "images" + email).toFile());
    }
}
