package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data			//getter setter
@NoArgsConstructor	//	빈 생성자
@AllArgsConstructor	//전체 생성자
@Builder
@Entity		//User 클래스가 MySQL 에 테이블이 생성된다.
// @DynamicInsert	//insert할 때 null인 필드를 제외해서 insert
public class User {
	
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략
	private int id;	//시퀀스
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100)	//123456 =>해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;

	//@ColumnDefault("user")
	//DB는 RoleType이라는 게 없다.
	@Enumerated(EnumType.STRING) // 따라서 해당 enum은 String 타입인 것을 알려주어야 한다.
	private RoleType role;	//Enum을 쓰는게 좋다.  관리자 역할 구분 // ADMIN, USER
	
	@CreationTimestamp	//시간이 자동 입력
	private Timestamp createDate;

}
