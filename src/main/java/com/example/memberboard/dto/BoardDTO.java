package com.example.memberboard.dto;

import com.example.memberboard.entity.BoardEntity;
import com.example.memberboard.entity.BoardFileEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private List<MultipartFile> boardFile;
    private List<MultipartFile> boardFileUpdate;
    private List<String> originalFileName_board;
    private List<String> storedFileName_board;

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
        //파일 관련 내용 추가
        if(boardEntity.getFileAttached_board()==1){
            boardDTO.setFileAttached_board(boardEntity.getFileAttached_board());
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            for(BoardFileEntity boardFileEntity:boardEntity.getBoardFileEntityList()){
                originalFileNameList.add(boardFileEntity.getOriginalFileName_board());
                storedFileNameList.add(boardFileEntity.getStoredFileName_board());
            }
            boardDTO.setOriginalFileName_board(originalFileNameList);
            boardDTO.setStoredFileName_board(storedFileNameList);
        }else{
            boardDTO.setFileAttached_board(boardEntity.getFileAttached_board());
        }
        return boardDTO;
    }
}
