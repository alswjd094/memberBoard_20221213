package com.example.memberboard.service;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
     Long savedId = memberRepository.save(MemberEntity.toSaveEntity(memberDTO)).getId();
     return savedId;
    }

    public String emailDuplicateCheck(String memberEmail) {
      Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()){
            return"ok";
        }else{
            return null;
        }
    }
}
