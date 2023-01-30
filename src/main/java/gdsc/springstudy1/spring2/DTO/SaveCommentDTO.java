package gdsc.springstudy1.spring2.DTO;

import gdsc.springstudy1.spring2.entity.Store;
import gdsc.springstudy1.spring2.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SaveCommentDTO {
    private String message;
    private String writer;
    private User user;
    private Store store;

    @Builder
    public SaveCommentDTO(WriteCommentDTO writeCommentDTO,
                          User user,
                          Store store) {
        this.message=writeCommentDTO.getContent();
        this.writer=user.getName();
        this.user=user;
        this.store=store;
    }
}
