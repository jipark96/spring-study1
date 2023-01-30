package gdsc.springstudy1.spring2.service;

import gdsc.springstudy1.spring2.DTO.LoadCommentDTO;
import gdsc.springstudy1.spring2.DTO.SaveCommentDTO;
import gdsc.springstudy1.spring2.DTO.WriteCommentDTO;
import gdsc.springstudy1.spring2.entity.Comment;
import gdsc.springstudy1.spring2.entity.Store;
import gdsc.springstudy1.spring2.entity.User;
import gdsc.springstudy1.spring2.repository.CommentRepository;
import gdsc.springstudy1.spring2.repository.StoreRepository;
import gdsc.springstudy1.spring2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public LoadCommentDTO load(Long storeId) {
        return LoadCommentDTO
                .builder()
                .store(storeRepository.findById(storeId)
                        .orElseThrow(()-> new IllegalArgumentException("ID에 해당하는 가게 정보가 없습니다. ID = "+storeId)))
                .build();
    }

    @Transactional
    public LoadCommentDTO write(Long storeId, WriteCommentDTO writeCommentDTO) {
        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 가게 정보가 없습니다. ID = "+storeId));

        User user = userRepository
                .findByUserId(writeCommentDTO.getUserId());

        commentRepository.save(Comment.builder()
                        .saveCommentDTO(SaveCommentDTO.builder()
                                .writeCommentDTO(writeCommentDTO)
                                .user(user)
                                .store(store).build())
                .build());

        return LoadCommentDTO
                .builder()
                .store(storeRepository.findById(storeId)
                        .orElseThrow(()-> new IllegalArgumentException("ID에 해당하는 가게 정보가 없습니다. ID = "+storeId)))
                .build();
    }
}
