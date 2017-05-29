package org.asechs.wheelwego.model;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	public MemberVO login(MemberVO vo){
		return sqlSessionTemplate.selectOne("member.login",vo);			
	}
	  @Override
	   public String findMemberId(MemberVO vo) {
	      return sqlSessionTemplate.selectOne("member.findMemberId", vo);
	   }
	   @Override
	   public String findMemberPassword(MemberVO vo) {
	      return sqlSessionTemplate.selectOne("member.findMemberPassword", vo);
	   }
}
