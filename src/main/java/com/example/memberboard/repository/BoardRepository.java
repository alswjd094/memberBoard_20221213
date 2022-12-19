package com.example.memberboard.repository;

import com.example.memberboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits (@Param("id")Long id);

    List<BoardEntity> findByBoardWriterContainingOrderByIdDesc(String q);
    List<BoardEntity> findByBoardTitleContainingOrderByIdDesc(String q);
    List<BoardEntity> findByBoardWriterContainingOrBoardTitleContainingOrderByIdDesc(String writer,String title);

    Page<BoardEntity> findByBoardWriterContainingOrderByIdDesc(String q, Pageable pageable);
    Page<BoardEntity> findByBoardTitleContainingOrderByIdDesc(String q, Pageable pageable);
    Page<BoardEntity> findByBoardWriterContainingOrBoardTitleContainingOrderByIdDesc(String writer,String title,Pageable pageable);

}
