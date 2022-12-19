package com.example.memberboard;

import com.example.memberboard.dto.BoardDTO;
import com.example.memberboard.entity.BoardEntity;
import com.example.memberboard.repository.BoardRepository;
import com.example.memberboard.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest

public class memberBoardTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("검색페이징 테스트")
    public void searchPagingTest(){
        int page = 0;
        final int pageLimit = 5;
        String q = "a";

        Page<BoardEntity> boardEntities = null;
         boardEntities = boardRepository.findByBoardWriterContainingOrderByIdDesc(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        Page<BoardDTO> boardDTOPage = boardEntities.map(
                board->new BoardDTO(
                        board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()));

    }

}
