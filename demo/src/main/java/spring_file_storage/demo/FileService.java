package spring_file_storage.demo;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import spring_file_storage.demo.security.UserObject;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
    }

    public void uploadFile(MultipartFile file, UserObject user) throws IOException {
        String name = file.getOriginalFilename();
        File fileToUpload = new File(
                UUID.randomUUID().toString(),
                name,
                file.getContentType(),
                file.getBytes(),
                user.getUser());
        this.fileRepository.save(fileToUpload);
    }

    public Stream<File> getFilesByUser(UserObject user) {
        return this.fileRepository.findAllByUser(user.getUser()).stream();
    }

    public File getFileById(String id) {
        return this.fileRepository.findById(id).get();
    }

    public void removeById(String id) {
        this.fileRepository.deleteById(id);
    }

}
