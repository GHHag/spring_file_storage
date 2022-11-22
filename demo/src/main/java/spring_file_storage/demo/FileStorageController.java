package spring_file_storage.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@RestController
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody UploadFile file) throws IOException {
        System.out.println(file.getUser().getId());
        System.out.println(file.getFile().getOriginalFilename());
        this.fileStorageService.uploadFile(
                // uploadFile.getName(),
                // uploadFile.getFileType(),
                // uploadFile.getData(),
                // uploadFile.getUser();
                file.getFile(), file.getUser());

        return ResponseEntity.ok(file.getFile().getName());
    }

    @Getter
    @Setter
    public static class UploadFile {
        private MultipartFile file;
        private User user;
    }

}
