package com.example.memberboard.service;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.entity.MemberFileEntity;
import com.example.memberboard.repository.MemberFileRepository;
import com.example.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberFileRepository memberFileRepository;
@Transactional
    public Long save(MemberDTO memberDTO) throws IOException {
        if(memberDTO.getMemberFile().isEmpty()){
            System.out.println("파일 없음");
            return memberRepository.save(MemberEntity.toSaveEntity(memberDTO)).getId();
        }else{
            System.out.println("파일 있음");
            MultipartFile memberFile = memberDTO.getMemberFile();
            String originalFileName_member =  memberFile.getOriginalFilename();
            String storedFileName_member = System.currentTimeMillis()+"-"+originalFileName_member;
            String savePath = "C:\\springboot_img\\"+storedFileName_member;
            memberFile.transferTo(new File(savePath));

            //dto->entity 옮겨 담기
            MemberEntity memberEntity = MemberEntity.toSaveFileEntity(memberDTO);
            //회원 저장, id값 추가된 객체 가져옴
            Long savedId = memberRepository.save(memberEntity).getId();
            //회원 조회
            MemberEntity member = memberRepository.findById(savedId).get();
            //MemberFileEntity 변환
            MemberFileEntity memberFileEntity = MemberFileEntity.toSaveMemberFileEntity(memberEntity,originalFileName_member,storedFileName_member);
            //member_file_table에 저장
            memberFileRepository.save(memberFileEntity);
            return savedId;
        }
    }

    public String emailDuplicateCheck(String memberEmail) {
      Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()){
            return"ok";
        }else{
            return null;
        }
    }
    @Transactional
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
@Transactional
    public List<MemberDTO> findAll() {
      List<MemberEntity> memberEntityList = memberRepository.findAll();
      List<MemberDTO> memberDTOList = new ArrayList<>();
      for(MemberEntity memberEntity : memberEntityList){
        MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
        memberDTOList.add(memberDTO);
      }
      return memberDTOList;
    }
@Transactional
    public MemberDTO findById(Long id) {
      Optional<MemberEntity> memberEntity = memberRepository.findById(id);
      if(memberEntity.isPresent()){
          return MemberDTO.toDTO(memberEntity.get());
      }else{
          return null;
      }
    }
    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
