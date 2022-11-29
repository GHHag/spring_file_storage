package spring_file_storage.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A class representing the file entity.
 * 
 * Author: Gustav Hagenblad, 2022
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    private String id;

    private String name;
    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne
    private User user;

    public File(String id, String name, String fileType, byte[] data) {
        this.id = id;
        this.name = name;
        this.fileType = fileType;
        this.data = data;
    }

}
