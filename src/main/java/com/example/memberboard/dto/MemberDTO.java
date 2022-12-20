package com.example.memberboard.dto;

import com.example.memberboard.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class MemberDTO {

    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberMobile;
    private String memberProfile;
    private LocalDateTime memberCreatedDate;

    private MultipartFile memberFile;
    private MultipartFile memberFileUpdate;
    private int fileAttached_member;
    private String originalFileName_member;
    private String storedFileName_member;

    public static MemberDTO toDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberProfile(memberEntity.getMemberProfile());
        memberDTO.setMemberCreatedDate(memberEntity.getCreatedTime());
        //파일 관련 내용 추가
        if(memberEntity.getFileAttached_member()==1){
            memberDTO.setFileAttached_member(memberEntity.getFileAttached_member());
            memberDTO.setOriginalFileName_member(memberEntity.getMemberFileEntityList().get(0).getOriginalFileName_member());
            memberDTO.setStoredFileName_member(memberEntity.getMemberFileEntityList().get(0).getStoredFileName_member());
            memberDTO.setMemberProfile(memberDTO.getStoredFileName_member());
        }else{
            memberDTO.setFileAttached_member(memberEntity.getFileAttached_member());
        }
        return memberDTO;
    }

}
