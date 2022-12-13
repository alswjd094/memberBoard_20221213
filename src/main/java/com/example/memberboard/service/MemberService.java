package com.example.memberboard.service;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public MemberDTO login(MemberDTO memberDTO) {
      Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
      if(optionalMember.isPresent()){
         MemberEntity memberEntity = optionalMember.get();
         if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
          MemberDTO memberDTO1 = MemberDTO.toDTO(memberEntity);
          return memberDTO1;
         }else{
             return null;
         }
      }else{
          return null;
      }
    }

    public List<MemberDTO> findAll() {
      List<MemberEntity> memberEntityList = memberRepository.findAll();
      List<MemberDTO> memberDTOList = new ArrayList<>();
      for(MemberEntity memberEntity : memberEntityList){
        MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
        memberDTOList.add(memberDTO);
      }
      return memberDTOList;
    }

    public MemberDTO findById(Long id) {
      Optional<MemberEntity> memberEntity = memberRepository.findById(id);
      if(memberEntity.isPresent()){
          return MemberDTO.toDTO(memberEntity.get());
      }else{
          return null;
      }
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
