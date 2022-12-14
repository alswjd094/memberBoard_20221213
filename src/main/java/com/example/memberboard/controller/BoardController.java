package com.example.memberboard.controller;

import com.example.memberboard.dto.BoardDTO;
import com.example.memberboard.dto.CommentDTO;
import com.example.memberboard.service.BoardService;
import com.example.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/board/save")
    public String saveForm(){
        return "boardPages/boardSave";
    }
    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);
        return"redirect:/board/paging";
    }
    @GetMapping("/board/paging")
    public String paging(@PageableDefault(page = 1)Pageable pageable, Model model){
        Page<BoardDTO> boardDTOPage = boardService.paging(pageable);
        model.addAttribute("boardList",boardDTOPage);

        int blockLimit = 3;
        //시작 페이지 값 계산
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        //끝 페이지 값 계산(3, 6, 9, 12---)
        //endPage 값이 totalPage값보다 크다면 endPage값을 totalPage값으로 덮어쓴다.
        int endPage = ((startPage + blockLimit - 1) < boardDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOPage.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardPages/boardPaging";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model){
        boardService.updateHits(id);
       BoardDTO boardDTO = boardService.findById(id);
       model.addAttribute("board",boardDTO);
       List<CommentDTO> commentDTOList = commentService.findAll(id);
       if(commentDTOList.size()>0){
           model.addAttribute("commentList",commentDTOList);
       }else{
           model.addAttribute("commentList","empty");
       }
       return "boardPages/boardDetail";
    }

    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board",boardDTO);
        return "boardPages/boardUpdate";
    }

    @PostMapping("/board/update")
    public String update (@ModelAttribute BoardDTO boardDTO,Model model) throws IOException{
        boardService.update(boardDTO);
       BoardDTO boardDTO1 = boardService.findById(boardDTO.getId());
        model.addAttribute("board",boardDTO1);
        return "boardPages/boardDetail";
    }

    @GetMapping("/board/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return"redirect:/board/paging";
    }

    @GetMapping("/board/")
    public String findAll(Model model){
      List<BoardDTO> boardDTOList = boardService.findAll();
      return "boardPages/boardList";
    }
//    @GetMapping("/board/search")
//    public String search(@RequestParam("type") String type, @RequestParam("q") String q, Model model){
//       List<BoardDTO> boardDTOList = boardService.search(type,q);
//        System.out.println("boardDTOList = " + boardDTOList);
//        model.addAttribute("boardList",boardDTOList);
//        return "boardPages/boardList";
//    }

    @GetMapping("/board/search")
    public String search(@PageableDefault(page = 1)Pageable pageable, @RequestParam("type") String type, @RequestParam("q") String q, Model model){
        Page<BoardDTO> boardDTOList = boardService.search(type,q,pageable);
        System.out.println("boardDTOList = " + boardDTOList);

        int blockLimit = 3;
        //시작 페이지 값 계산
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        //끝 페이지 값 계산(3, 6, 9, 12---)
        //endPage 값이 totalPage값보다 크다면 endPage값을 totalPage값으로 덮어쓴다.
        int endPage = ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();
        model.addAttribute("boardList",boardDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type",type);
        model.addAttribute("q",q);
        return "boardPages/boardPaging";
    }
}
