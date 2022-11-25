package spring_file_storage.demo;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import spring_file_storage.demo.security.UserObject;

@Service
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    // private final UserRepository userRepository;

    @Autowired
    public FileStorageService(FileStorageRepository fileStorageRepository, UserRepository userRepository) {
        this.fileStorageRepository = fileStorageRepository;
        // this.userRepository = userRepository;
    }

    // public void uploadFile(MultipartFile file, String userId) throws IOException
    // {
    public void uploadFile(MultipartFile file, UserObject user) throws IOException {
        // User user = this.userRepository.findById(userId).orElseThrow();
        String name = file.getOriginalFilename();
        System.out.println(user.getUsername());
        System.out.println(user.getAuthorities());
        File fileToUpload = new File(
                UUID.randomUUID().toString(),
                name,
                file.getContentType(),
                file.getBytes(),
                user.getUser());
        // UserPayload.fromUser(user.getUser()));
        this.fileStorageRepository.save(fileToUpload);
    }

    public Optional<File> getFileById(String id) {
        return this.fileStorageRepository.findById(id);
    }

}
