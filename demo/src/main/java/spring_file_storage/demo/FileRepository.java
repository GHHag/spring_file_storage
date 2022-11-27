package spring_file_storage.demo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<File, String> {

    public List<File> findAllByUser(User user);

}
