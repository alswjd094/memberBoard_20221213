package com.example.memberboard.service;

import com.example.memberboard.dto.BoardDTO;
import com.example.memberboard.entity.BoardEntity;
import com.example.memberboard.entity.BoardFileEntity;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.repository.BoardFileRepository;
import com.example.memberboard.repository.BoardRepository;
import com.example.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardFileRepository boardFileRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter()).get();
        if(boardDTO.getBoardFile().get(0).isEmpty()){
            System.out.println("파일 없음");
            BoardEntity boardEntity =  BoardEntity.toSaveBoardEntity(boardDTO,memberEntity);
            return boardRepository.save(boardEntity).getId();
        } else{
            System.out.println("파일 있음");
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO,memberEntity);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity entity = boardRepository.findById(savedId).get();
            for(MultipartFile boardFile: boardDTO.getBoardFile()){
                String originalFileName_board = boardFile.getOriginalFilename();
                String storedFileName_board = System.currentTimeMillis()+"-"+originalFileName_board;
                String savePathBoard = "C:\\springboot_img\\"+storedFileName_board;
                boardFile.transferTo(new File(savePathBoard));
                BoardFileEntity boardFileEntity = BoardFileEntity.toSaveBoardFileEntity(entity,originalFileName_board,storedFileName_board);
                boardFileRepository.save(boardFileEntity);
            }
            return savedId;
        }
    }
@Transactional
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
@Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }
@Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        if(boardEntityOptional.isPresent()){
          BoardEntity boardEntity = boardEntityOptional.get();
          BoardDTO boardDTO= BoardDTO.toSaveBoardDTO(boardEntity);
          return boardDTO;
        }else{
            return null;
        }
    }

    public void update(BoardDTO boardDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter()).get();
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO,memberEntity);
        boardRepository.save(boardEntity);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
@Transactional
    public Page<BoardDTO> search(String type, String q, Pageable pageable) {
    int page = pageable.getPageNumber()-1;
    final int pageLimit = 5;
   Page<BoardEntity> boardEntities = null;
//           boardRepository.searchBy(PageRequest.of(page, pageLimit,Sort.by(Sort.Direction.DESC,"id")));
    if(type.equals("boardWriter")){
        boardEntities = boardRepository.findByBoardWriterContainingOrderByIdDesc(q,PageRequest.of(page, pageLimit,Sort.by(Sort.Direction.DESC,"id")));
    }else if(type.equals("boardTitle")){
        boardEntities = boardRepository.findByBoardTitleContainingOrderByIdDesc(q,PageRequest.of(page, pageLimit,Sort.by(Sort.Direction.DESC,"id")));
    }else{
        boardEntities = boardRepository.findByBoardWriterContainingOrBoardTitleContainingOrderByIdDesc(q,q,PageRequest.of(page, pageLimit,Sort.by(Sort.Direction.DESC,"id")));
    }
    Page<BoardDTO> boardDTOPage = boardEntities.map(
            board->new BoardDTO(
                    board.getId(),
                    board.getBoardTitle(),
                    board.getBoardWriter(),
                    board.getBoardHits(),
                    board.getCreatedTime()));

//        List<BoardDTO> boardDTOList = new ArrayList<>();
//        for(BoardEntity boardEntity : boardEntities){
//            BoardDTO boardDTO = BoardDTO.toSaveBoardDTO(boardEntity);
//            boardDTOList.add(boardDTO);
//        }
        return boardDTOPage;
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntityList){
            boardDTOList.add(BoardDTO.toSaveBoardDTO(boardEntity));
        }
        return  boardDTOList;
    }
}
