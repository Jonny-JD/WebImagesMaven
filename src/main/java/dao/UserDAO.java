package dao;

import entity.User;
import entity.UserImage;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import util.ConnectionManager;
import util.PasswordEncoder;

import java.math.BigInteger;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDAO {

    private static final String FIND_BY_EMAIL = """
            SELECT id, name, email FROM users
            WHERE email =?
            """;
    private static final String FIND_BY_EMAIL_AND_PASSWORD = FIND_BY_EMAIL + " " + "AND password = ?";
    private static final String ADD_NEW_USER = """
            INSERT INTO users (name, email, password)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_USER_BY_ID = """
            DELETE FROM users WHERE id = ?
            """;

    private static final String ADD_SALT = """
                INSERT INTO salt (id, salt)
                VALUES (?, ?)
            """;
    private static final String GET_SALT = """
            SELECT s.salt FROM salt
            JOIN users u ON u.email = ?
                JOIN salt s ON u.id = s.id
            """;
    private static final String ADD_NEW_IMAGE = """
            INSERT INTO user_images (id, uri)
            VALUES (?, ?)
            """;
    private static final String GET_USER_IMAGES = """
            SELECT uri FROM user_images
            WHERE id = ?
            """;
    private static final UserDAO INSTANCE = new UserDAO();

    public static UserDAO getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public Optional<User> findByEmail(String email) {
        User user = null;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_EMAIL)) {
            preparedStatement.setString(1, email);

            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
        }
        return Optional.ofNullable(user);
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        User user = null;
        try (var connection = ConnectionManager.get();
             var preparedStatementSalt = connection.prepareStatement(GET_SALT);
             var preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD)) {

            preparedStatementSalt.setString(1, email);
            var resultSetSalt = preparedStatementSalt.executeQuery();

            if (resultSetSalt.next()) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, PasswordEncoder.generatePassword(password, resultSetSalt.getString("salt")));

                var resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    user = buildUserEntity(resultSet);
                }
            }

            return Optional.ofNullable(user);
        }

    }

    @SneakyThrows
    public User addNewUser(User entity) {
        var salt = PasswordEncoder.generateSalt();

        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(ADD_NEW_USER, RETURN_GENERATED_KEYS);
             var preparedStatementSalt = connection.prepareStatement(ADD_SALT)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, PasswordEncoder.generatePassword(entity.getPassword(), salt));

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();

            generatedKeys.next();
            preparedStatementSalt.setLong(1, generatedKeys.getObject("id", Long.class));
            preparedStatementSalt.setString(2, salt);

            preparedStatementSalt.executeUpdate();

            entity.setId(generatedKeys.getObject("id", BigInteger.class));

            return entity;
        }
    }

    @SneakyThrows
    public boolean deleteUserById(BigInteger id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            preparedStatement.setInt(1, id.intValue());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    public void addNewImage(String path, BigInteger id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(ADD_NEW_IMAGE)) {
            preparedStatement.setInt(1, id.intValue());
            preparedStatement.setString(2, path);
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public List<UserImage> getUserImages(BigInteger id) {
        List<UserImage> userImages = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_USER_IMAGES)) {
            preparedStatement.setInt(1, id.intValue());
            var resultSet = preparedStatement.executeQuery();
            UserImage userImage;
            while (resultSet.next()) {
                userImage = UserImage.builder()
                        .imagePath(Paths.get((resultSet.getString("uri"))).normalize().toUri())
                        .build();
                userImages.add(userImage);
            }
        }
        return userImages;
    }

    @SneakyThrows
    private static User buildUserEntity(ResultSet resultSet) {
        return User.builder()
                .id(resultSet.getObject("id", BigInteger.class))
                .name(resultSet.getObject("name", String.class))
                .email(resultSet.getObject("email", String.class))
                .build();
    }
}
