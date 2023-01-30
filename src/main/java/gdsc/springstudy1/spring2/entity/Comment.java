package gdsc.springstudy1.spring2.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import gdsc.springstudy1.spring2.DTO.SaveCommentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@Table
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @Column(name = "message")
    private String message;
    @Column(name = "createdTime")
    @CreatedDate
    private LocalDateTime createdTime;
    @Column(name = "writer")
    private String writer;

    @JsonIgnore
    @JoinColumn(name = "user_id_fk")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @JsonIgnore
    @JoinColumn(name = "store_id_fk")
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @Builder
    public Comment(SaveCommentDTO saveCommentDTO) {
        this.message = saveCommentDTO.getMessage();
        this.writer = saveCommentDTO.getWriter();
        this.user = saveCommentDTO.getUser();
        this.store = saveCommentDTO.getStore();
    }
}
