package com.example.memberboard.service;

import com.example.memberboard.dto.BoardDTO;
import com.example.memberboard.entity.BoardEntity;
import com.example.memberboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public Long save(BoardDTO boardDTO) {
      BoardEntity boardEntity =  BoardEntity.toSaveBoardEntity(boardDTO);
      return boardRepository.save(boardEntity).getId();
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber()-1;
        final int pageLimit = 5;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        Page<BoardDTO> boardDTOPage = boardEntities.map(
                board->new BoardDTO(
                        board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()));
        return boardDTOPage;
    }

}
