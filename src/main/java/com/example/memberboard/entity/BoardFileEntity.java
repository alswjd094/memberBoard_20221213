package com.example.memberboard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "boards_file_table")
public class BoardFileEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName_board;

    @Column
    private String storedFileName_board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static BoardFileEntity toSaveBoardFileEntity(BoardEntity entity,String originalFileName_board,String storedFileName_board){
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName_board(originalFileName_board);
        boardFileEntity.setStoredFileName_board(storedFileName_board);
        boardFileEntity.setBoardEntity(entity);
        return boardFileEntity;
    }
}
