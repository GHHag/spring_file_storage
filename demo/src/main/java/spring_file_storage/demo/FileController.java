package spring_file_storage.demo;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import spring_file_storage.demo.security.UserObject;

@RestController
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile file, @AuthenticationPrincipal UserObject user) {
        try {
            this.fileService.uploadFile(file, user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getFiles(@AuthenticationPrincipal UserObject user) {
        List<ResponseFile> files = this.fileService.getFilesByUser(user).map(file -> {
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(file.getId())
                    .toUriString();
            return new ResponseFile(downloadUri, file.getName(), file.getFileType(), file.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {
        File file = this.fileService.getFileById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + file.getName() + "\"")
                .body(file.getData());
    }

    // @DeleteMapping("/remove-file")
    // public ResponseEntity<> removeFile() {

    // }

}
