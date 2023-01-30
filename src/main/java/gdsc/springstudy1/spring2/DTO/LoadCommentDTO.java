package gdsc.springstudy1.spring2.DTO;

import gdsc.springstudy1.spring2.entity.Comment;
import gdsc.springstudy1.spring2.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LoadCommentDTO {
    private List<Comment> comments;

    @Builder
    public LoadCommentDTO(Store store) {
        this.comments = store.getComments();
    }

}
