package com.example.memberboard.dto;

import com.example.memberboard.entity.BoardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedDate;
    private int fileAttached_board;

    public BoardDTO(Long id, String boardTitle, String boardWriter, int boardHits, LocalDateTime boardCreatedDate) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardHits = boardHits;
        this.boardCreatedDate = boardCreatedDate;
    }
    public static BoardDTO toSaveBoardDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedDate(boardEntity.getCreatedTime());
        return boardDTO;
    }
}
