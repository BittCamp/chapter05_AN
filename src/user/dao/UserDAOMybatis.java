package user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import user.bean.UserDTO;

@Repository //디비와 연결하고 있다고 표시
@Transactional // 이렇게 하면 이 클래스 안에서 커밋 클로즈 자동으로 해준다.
public class UserDAOMybatis implements UserDAO {
	@Autowired //Spring configuration 참고하는데 그중에 SqlSession있으면 다연결해라라는 뜻.
	private SqlSession sqlSession;
	
	//@Transactional 여기 써도되고
	@Override
	public void write(UserDTO userDTO) {
		sqlSession.insert("userSQL.write",userDTO); // 이렇게 쓰기만 해도 커밋 클로즈 다해준다.@Transactional 이 기능이 있기때문.
	}

	@Override
	public List<UserDTO> getUserList() {
		return sqlSession.selectList("userSQL.getUserList");
	}

	@Override
	public UserDTO getUser(String id) {
		return sqlSession.selectOne("userSQL.getUser",id);
	}

	@Override
	public void userUpdate(Map<String, String> map) {
		sqlSession.update("userSQL.update",map);
	}

	@Override
	public void delete(String id) {
		sqlSession.delete("userSQL.delete",id);
	}
	
}
