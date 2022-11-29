package spring_file_storage.demo;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import spring_file_storage.demo.security.UserObject;

/**
 * File service class that handles logic refering to files.
 * 
 * Author: Gustav Hagenblad, 2022
 */

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Handles the uploading of a file.
     * 
     * @param file - A MultipartFile object, the file to be stored.
     * @param user - A UserObject with the user uploading the file.
     * @throws IOException
     */
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

    /**
     * Returns all files related to the given user.
     * 
     * @param user - A UserObject with the user requesting to get its files.
     * @return - Returns a Stream of File objects.
     */
    public Stream<File> getFilesByUser(UserObject user) {
        return this.fileRepository.findAllByUser(user.getUser()).stream();
    }

    /**
     * Finds and returns a file with the given id.
     * 
     * @param id - A String with the id of the file to find.
     * @return - Returns a File object.
     */
    public File getFileById(String id) {
        return this.fileRepository.findById(id).get();
    }

    /**
     * Removes the file with given id.
     * 
     * @param id - The id of the file to remove.
     */
    public void removeById(String id) {
        this.fileRepository.deleteById(id);
    }

}
