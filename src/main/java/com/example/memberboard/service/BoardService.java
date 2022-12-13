package com.example.memberboard.service;

import com.example.memberboard.dto.BoardDTO;
import com.example.memberboard.entity.BoardEntity;
import com.example.memberboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public Long save(BoardDTO boardDTO) {
      BoardEntity boardEntity =  BoardEntity.toSaveBoardEntity(boardDTO);
      return boardRepository.save(boardEntity).getId();
    }
}
