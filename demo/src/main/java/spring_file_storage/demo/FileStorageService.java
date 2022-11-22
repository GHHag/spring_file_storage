package spring_file_storage.demo;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    @Autowired
    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    public void uploadFile(MultipartFile file, User user) throws IOException {
        String name = file.getOriginalFilename();
        File fileToUpload = new File(
                UUID.randomUUID().toString(),
                name, file.getContentType(),
                file.getBytes(),
                user);
        this.fileStorageRepository.save(fileToUpload);
    }

    public Optional<File> getFileById(String id) {
        return this.fileStorageRepository.findById(id);
    }

}
