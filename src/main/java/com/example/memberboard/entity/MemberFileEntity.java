package com.example.memberboard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="member_file_table")
public class MemberFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName_member;
    @Column
    private String storedFileName_member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static MemberFileEntity toSaveMemberFileEntity(MemberEntity memberEntity,String originalFileName_member,String storedFileName_member){
        MemberFileEntity memberFileEntity = new MemberFileEntity();
        memberFileEntity.setMemberEntity(memberEntity);
        memberFileEntity.setOriginalFileName_member(originalFileName_member);
        memberFileEntity.setStoredFileName_member(storedFileName_member);
        return memberFileEntity;
    }
}
