package com.example.memberboard.service;

import com.example.memberboard.dto.CommentDTO;
import com.example.memberboard.entity.BoardEntity;
import com.example.memberboard.entity.CommentEntity;
import com.example.memberboard.repository.BoardRepository;
import com.example.memberboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    @Transactional
    public Long save(CommentDTO commentDTO) {
        BoardEntity entity = boardRepository.findById(commentDTO.getBoardId()).get();
        CommentEntity commentEntity = CommentEntity.toCommentEntity(entity, commentDTO);
        Long id = commentRepository.save(commentEntity).getId();
        return id;
    }
@Transactional
    public List<CommentDTO> findAll(Long boardId) {
    BoardEntity boardEntity = boardRepository.findById(boardId).get();
    List<CommentEntity> commentEntities = boardEntity.getCommentEntityList();
    List<CommentDTO> commentDTOList = new ArrayList<>();
    for(CommentEntity commentEntity : commentEntities){
        commentDTOList.add(CommentDTO.toCommentDTO(commentEntity));
    }
    return commentDTOList;
    }
}
