package eva.pet.upc.evapet.serviceImplements;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    private Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public String upload(MultipartFile file) throws IOException {
        Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", "evapet/users"  // carpeta donde se guardan en Cloudinary
        ));
        return (String) result.get("secure_url");
    }

    public void delete(String imageUrl) throws IOException {
        // extrae el public_id de la URL para poder eliminarla
        String publicId = imageUrl
                .substring(imageUrl.lastIndexOf("/") + 1)
                .replace(".jpg", "")
                .replace(".png", "")
                .replace(".jpeg", "");
        cloudinary.uploader().destroy("evapet/users/" + publicId, ObjectUtils.emptyMap());
    }
}
