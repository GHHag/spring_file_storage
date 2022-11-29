package spring_file_storage.demo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for the File entity.
 * 
 * Author: Gustav Hagenblad, 2022
 */

@Repository
@Transactional
public interface FileRepository extends JpaRepository<File, String> {

    public List<File> findAllByUser(User user);

}
