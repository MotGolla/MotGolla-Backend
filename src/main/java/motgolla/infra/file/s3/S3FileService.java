package motgolla.infra.file.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import motgolla.infra.file.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3FileService implements FileService {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  // 업로드 가능한 이미지 파일의 확장자와 MIME 타입(형식)을 제한하여 허용 목록만 등록함
  private static final List<String> ALLOWED_EXTENSIONS = List.of(".jpg", ".jpeg", ".png", ".webp");
  private static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/jpeg", "image/png", "image/webp");

  private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

  @Override
  public String upload(MultipartFile file, String folder, String baseFileName) {
    validateFile(file);
    String extension = getExtension(file.getOriginalFilename());
    String key = folder + "/" + baseFileName + "_" + UUID.randomUUID() + extension;

    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.getSize());
      metadata.setContentType(file.getContentType());

      amazonS3.putObject(new PutObjectRequest(bucket, key, file.getInputStream(), metadata));
      return amazonS3.getUrl(bucket, key).toString();

    } catch (IOException e) {
      throw new RuntimeException("S3 파일 업로드 중 오류 발생", e);
    }
  }

  @Override
  public void delete(String key) {
    key = key.substring(key.indexOf(".com/") + 5);
    amazonS3.deleteObject(bucket, key);
  }

  private void validateFile(MultipartFile file) {
    String extension = getExtension(file.getOriginalFilename()).toLowerCase();
    String contentType = file.getContentType();

    if (!ALLOWED_EXTENSIONS.contains(extension) || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
      throw new IllegalArgumentException("허용되지 않은 이미지 형식입니다.");
    }
    if (file.getSize() > MAX_FILE_SIZE) {
      throw new IllegalArgumentException("파일 용량은 5MB 이하만 가능합니다.");
    }
  }

  private String getExtension(String fileName) {
    int idx = fileName.lastIndexOf('.');
    return idx != -1 ? fileName.substring(idx) : "";
  }
}
