package gdsc.springstudy1.spring2.repository;

import gdsc.springstudy1.spring2.DTO.SaveCommentDTO;
import gdsc.springstudy1.spring2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
