package edu.megalab.news.utility;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import edu.megalab.news.dto.FileDto;
import edu.megalab.news.exception.FileException;
import edu.megalab.news.exception.FileWriteException;
import edu.megalab.news.exception.GCPFileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

@Component
public class CloudStorageUtil {
    @Value("${gcp.config.file}")
    private String gopConfigFile;

    @Value("${gcp.project.id}")
    private String gopProjectId;

    @Value("${gcp.bucket.id}")
    private String gopBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    public FileDto uploadFile(MultipartFile file) throws FileException, FileWriteException, GCPFileUploadException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new FileException("File name is null");
        try {
            String contentType = file.getContentType();
            byte[] fileData = file.getBytes();
            return uploadToStorage(fileName, fileData, contentType);
        } catch (IOException e) {
            throw new GCPFileUploadException("An error occurred while storing data to GCS");
        }
    }

    public boolean isImageFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Set<String> extensions = Set.of("jpg", "jpeg", "img", "png", "svg");
        return extensions.contains(extension);
    }

    private FileDto uploadToStorage(String fileName, byte[] fileData, String contentType) throws IOException, GCPFileUploadException {
        InputStream inputStream = new ClassPathResource(gopConfigFile).getInputStream();
        StorageOptions options = StorageOptions.newBuilder().setProjectId(gopProjectId)
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
        Storage storage = options.getService();
        Bucket bucket = storage.get(gopBucketId, Storage.BucketGetOption.fields());
        String randomId = UUID.randomUUID().toString();
        Blob blob = bucket.create(gcpDirectoryName + "/" +
                randomId + "." + FilenameUtils.getExtension(fileName), fileData, contentType);
        if (blob != null) {
            return new FileDto(storage.getOptions().getHost() + "/" +
                    blob.getBucket() + "/" +
                    blob.getName(), blob.getMediaLink());
        }
        throw new GCPFileUploadException("An error occurred while storing data to GCS");
    }
}

