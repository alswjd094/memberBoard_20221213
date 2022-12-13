package com.example.memberboard.entity;

import com.example.memberboard.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "members_table")
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String memberEmail;
    @Column(length = 20, nullable = false)
    private String memberPassword;
    @Column(length = 20, nullable = false)
    private String memberName;
    @Column(length = 13)
    private String memberMobile;
    @Column(length = 100)
    private String memberProfile;
    @Column
    private int fileAttached_member;

    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberFileEntity> memberFileEntityList = new ArrayList<>();

    public static MemberEntity toSaveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberProfile(memberDTO.getStoredFileName_member());
        memberEntity.setFileAttached_member(0);
        return memberEntity;
    }
    public static MemberEntity toSaveFileEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberProfile(memberDTO.getStoredFileName_member());
        memberEntity.setFileAttached_member(1);
        return memberEntity;
    }
}
