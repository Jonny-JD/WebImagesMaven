package dto;

import lombok.Builder;
import lombok.Value;

import java.net.URI;
import java.util.List;

@Value
@Builder
public class UserImagesDTO {
    List<URI> userImagesPaths;
}
