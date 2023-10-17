package service;

import dao.UserDAO;
import dto.UserImagesDTO;
import entity.UserImage;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ImageService {
    private static final ImageService INSTANCE = new ImageService();

    private final UserDAO userDAO = UserDAO.getInstance();

    public static ImageService getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public void addNewImage(Path path, InputStream inputStream, BigInteger id) {
        try (inputStream) {

            Files.write(path, inputStream.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
        userDAO.addNewImage(path.toString(), id);
    }

    @SneakyThrows
    public UserImagesDTO getUserImages(BigInteger id) {
        return UserImagesDTO.builder()
                .userImagesPaths(userDAO.getUserImages(id).stream().map(UserImage::getImagePath).toList())
                .build();
    }

    @SneakyThrows
    public Optional<InputStream> getImage(String imagePath) {
        var imageFullPath = Path.of(imagePath);
        return Files.exists(imageFullPath)
                ? Optional.of(Files.newInputStream(imageFullPath))
                : Optional.empty();

    }
}
