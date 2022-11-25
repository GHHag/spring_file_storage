package spring_file_storage.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import spring_file_storage.demo.security.UserObject;

@RestController
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload") // ta emot path variabel för user ris hear?
    // public ResponseEntity<String> upload(@RequestBody MultipartFile file,
    // @PathVariable String userId)
    public ResponseEntity<String> upload(@RequestBody MultipartFile file, @AuthenticationPrincipal UserObject user)
            throws IOException {
        // this.fileStorageService.uploadFile(file, userId);
        this.fileStorageService.uploadFile(file, user);

        return ResponseEntity.ok(file.getName());
    }

}
