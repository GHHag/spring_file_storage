package spring_file_storage.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseFile {

    private String uri;
    private String name;
    private String fileType;
    private long size;

}
