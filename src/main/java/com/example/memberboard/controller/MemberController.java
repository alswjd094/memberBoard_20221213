package com.example.memberboard.controller;

import com.example.memberboard.dto.BoardDTO;
import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String saveForm(){
        return "memberPages/memberSave";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "memberPages/memberLogin";
    }

    @PostMapping("/member/dup-check")
    public ResponseEntity emailDuplicateCheck(@RequestParam("inputEmail")String memberEmail){
      String inputEmailResult = memberService.emailDuplicateCheck(memberEmail);
      if(inputEmailResult != null){
          return new ResponseEntity<>("사용할 수 있는 이메일입니다.", HttpStatus.OK);
      }else{
          return new ResponseEntity<>("이미 사용 중인 이메일입니다.",HttpStatus.CONFLICT);
      }
    }

    @GetMapping("/member/login")
    public String loginForm(){
        return"memberPages/memberLogin";
    }
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
     MemberDTO loginResult = memberService.login(memberDTO);
     if(loginResult != null){
         session.setAttribute("loginEmail",memberDTO.getMemberEmail());
         return "redirect:/board/paging";
     }else{
         return"memberPages/memberLogin";
     }
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

    @GetMapping("/member/admin")
    public String adminForm(){
        return "memberPages/admin";
    }

    @GetMapping("/member/members")
    public String members(Model model){
      List<MemberDTO> memberDTOList = memberService.findAll();
      model.addAttribute("members",memberDTOList);
      return "memberPages/memberList";
    }

    @GetMapping("/member/{id}")
    public String member(@PathVariable Long id, Model model){
       MemberDTO memberDTO = memberService.findById(id);
       model.addAttribute("member",memberDTO);
       return "memberPages/memberDetail";
    }

    @GetMapping("/member/delete/{id}")
    public String delete(@PathVariable Long id){
        memberService.delete(id);
        return "redirect:/member/members";
    }

    @GetMapping("/member/myPage")
    public String myPage(Model model,HttpSession session){
        //마이페이지에 프로필 사진 보이기
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member",memberDTO);
        return "memberPages/myPage";
    }

    @GetMapping("/member/update")
    public String updateForm(Model model,HttpSession session){
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member",memberDTO);
        return "memberPages/memberUpdate";
    }
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) throws IOException{
        memberService.update(memberDTO);
        return "redirect:/member/myPage";
    }

}
