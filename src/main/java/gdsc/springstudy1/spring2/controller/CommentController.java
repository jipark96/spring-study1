package gdsc.springstudy1.spring2.controller;

import gdsc.springstudy1.spring2.DTO.LoadCommentDTO;
import gdsc.springstudy1.spring2.DTO.WriteCommentDTO;
import gdsc.springstudy1.spring2.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/store/{storeId}/comment")
    public LoadCommentDTO load(@PathVariable Long storeId) {
        return commentService.load(storeId);
    }

    @PostMapping("/store/{storeId}/comment")
    public LoadCommentDTO write(@PathVariable Long storeId,
                                @RequestBody WriteCommentDTO writeCommentDTO) {
        return commentService.write(storeId,writeCommentDTO);
    }
}
