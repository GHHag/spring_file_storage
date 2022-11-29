package spring_file_storage.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A class containing metadata for the file entity.
 * 
 * Author: Gustav Hagenblad, 2022
 */

@Getter
@AllArgsConstructor
public class ResponseFile {

    private String uri;
    private String name;
    private String fileType;
    private long size;

}
