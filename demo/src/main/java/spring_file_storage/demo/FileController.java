package spring_file_storage.demo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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

/**
 * File controller class that defines endpoints for managing files.
 * 
 * Author: Gustav Hagenblad, 2022
 */

@RestController
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * POST request endpoint for uploading a file.
     * 
     * @param file - An object of the MultipartFile class.
     * @param user - AuthenticationPrincipal for the currently logged in user.
     * @return - ResponseEntity containing a String.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile file, @AuthenticationPrincipal UserObject user) {
        if (file == null || file.getSize() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            this.fileService.uploadFile(file, user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET request endpoint for getting files related to a user.
     * 
     * @param user - AuthenticationPrincipal for the currently logged in user.
     * @return - ResponseEntity with a List of the users' files represented as
     *         ResponseFile objects.
     */
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getFiles(@AuthenticationPrincipal UserObject user) {
        List<ResponseFile> files = this.fileService.getFilesByUser(user).map(file -> {
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(file.getId())
                    .toUriString();
            return new ResponseFile(downloadUri, file.getName(), file.getFileType(), file.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    /**
     * GET request endpoint for downloading a file.
     * 
     * @param user - AuthenticationPrincipal for the currently logged in user.
     * @param id   - The id of the file to be downloaded.
     * @return - An array of bytes containing the stored file.
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> downloadFile(@AuthenticationPrincipal UserObject user, @PathVariable String id) {
        var userId = user.getUser().getId();

        Optional<File> file = this.fileService.getFileById(id);
        if (!file.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var fileOwner = this.fileService.getFileById(id).get().getUser().getId();
        if (userId.toString().equals(fileOwner.toString())) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.get().getName() + "\"")
                    .body(file.get().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE request endpoint for deleting a file.
     * 
     * @param user - AuthenticationPrincipal for the currently logged in user.
     * @param id   - The id of the file to be deleted.
     * @return - ResponseEntity containing a String.
     */
    @DeleteMapping("files/remove/{id}")
    public ResponseEntity<String> removeFile(@AuthenticationPrincipal UserObject user, @PathVariable String id) {
        var userId = user.getUser().getId();

        Optional<File> file = this.fileService.getFileById(id);
        if (!file.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var fileOwner = this.fileService.getFileById(id).get().getUser().getId();
        if (userId.toString().equals(fileOwner.toString())) {
            this.fileService.removeById(id);

            return ResponseEntity.ok("File deleted");
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to delete file");
        }
    }

}
