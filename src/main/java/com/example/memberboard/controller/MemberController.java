package com.example.memberboard.controller;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String saveForm(){
        return "memberPages/memberSave";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
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
         return "memberPages/memberMain";
     }else{
         return"memberPages/memberLogin";
     }

    }

}
