package user.service;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import user.bean.UserDTO;
import user.dao.UserDAO;
@Service
public class UserInsertService implements UserService {
	@Autowired
	private UserDTO userDTO;
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("[실행결과]");
		System.out.println("이름 입력 :");
		String name = scan.next();
		System.out.println("아이디 입력 :");
		String id = scan.next();
		System.out.println("비밀번호 입력 :");
		String pwd= scan.next();
		
		//userDTO 만들고 //UserDTO에 데이터 담으시오.
		// UserDTO userDTO = new UserDTO(); --하지말고 setter injection 하시오.
		userDTO.setName(name);
		userDTO.setId(id);
		userDTO.setPwd(pwd);
		
		//UserDAO userDAO = new UserDAOImple(); --X , setter injection
		//userDAO.write(userDTO);
		//DB
		userDAO.write(userDTO);
		
		System.out.println(name+"님의 데이터를 저장하였습니다.");
		
	}

}
