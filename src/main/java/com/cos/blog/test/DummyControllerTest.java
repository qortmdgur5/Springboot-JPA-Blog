package com.cos.blog.test;


import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired	//의존성 주입(DI)
	private UserRepository userRepository;
	
	
	// save 함수는  id를 전달하지 않으면 insert를 해주고
	// save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 진행.
	// email, password 수정계획
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
		userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id : " + id;
	}
	
	
	
	
	@Transactional // save 함수를 사용하지 않아도 update 가능, 함수 종료시에 자동 commit
	@PutMapping("/dummy/user/{id}") // update를 진행할 땐 putmapping사용
	public User updateUser(@PathVariable int id, @RequestBody User  requestUser) { //RequestBody는 json데이터를 받을 때 이용
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{	//람다식
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// userRepository.save(user);
		
		// 더티 체킹
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	//한 페이지당 2건에 데이터를 리터받을 계획
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	
	//{id} 주소로 파라메터를 전달 받을 수 있습니다.
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {	//@PathVariable 주소로 전달받을 {id}가 어떤 타입인지
		
		// user/4 를 찾으면 내가 데이터베이스에서 못찾아오면 user가 null이 될 것이고
		// 그럼 return null이 리턴이 될테니 그럼 프로그램에 문제가 될 것이야
		// Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해 
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id :" + id);
			}
		});
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트 자바는 html과 같은것만 이해할 수 있는데 restcontroller 로 data로 보냇으니
		// 변환 ( 웹브라우저가 이해할 수 있는 데이터) -> json
		// 스프링부트= MessageConverter 라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jacson 라이브러리를 호출해
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {	// key = value (약속된 규칙)
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
		
		
	}

}
