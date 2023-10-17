package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserImage {
    URI imagePath;
}
