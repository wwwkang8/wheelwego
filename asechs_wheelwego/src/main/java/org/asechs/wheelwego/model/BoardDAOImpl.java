package org.asechs.wheelwego.model;

import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Resource
	SqlSessionTemplate template;
	
	@Override
	public List<BoardVO> getFreeBoardList(PagingBean pagingBean) {
		return template.selectList("board.getFreeBoardList", pagingBean);
	}

	@Override
	public List<BoardVO> getBusinessInfoBoardList(PagingBean pagingBean) {
		return template.selectList("board.getBusinessInfoBoardList", pagingBean);
	}

	@Override
	public int getFreeBoardTotalContentCount() {
		return template.selectOne("board.getFreeBoardTotalContentCount");
	}

	@Override
	public int getBusinessInfoBoardTotalContentCount() {
		return template.selectOne("board.getBusinessInfoBoardTotalContentCount");
	}

	@Override
	public int getQnATotalContentCount() {
		return template.selectOne("board.getQnATotalContentCount");
	}

	@Override
	public List<BoardVO> getQnABoardList(PagingBean pagingBean) {
		return template.selectList("board.getQnABoardList", pagingBean);
	}

	@Override
	public BoardVO getFreeBoardDetail(String no) {
		return (BoardVO) template.selectOne("board.getFreeBoardDetail", no);
	}

	@Override
	public void freeboardDelete(String no) {
		template.delete("board.freeboardDelete", no);
	}

	@Override
	public void updateHits(int hits) {
		template.update("board.updateCount", hits);
	}

	@Override
	public String freeboardWrite(BoardVO bvo) {
		template.insert("board.freeboardWrite",bvo);
		//template 메서드 써서 가져온 게시물 번호와 파일명을 insert바로 하기
		//여기서 bvo.getNo을 int로 넘겨주기
		return bvo.getNo();
	}

	@Override
	public void freeboardWriteFileUpload(BoardVO boardVO) {
		template.insert("board.freeboardWriteFileUpload", boardVO);
		
	}
		
	public void updateBoard(BoardVO vo) {
		template.update("board.updateBoard",vo);
	}

	@Override
	public MemberVO getNameById(BoardVO bvo) {
		return template.selectOne("board.getNameById", bvo);
	}

	@Override
	public void updateHitsBusiness(int hits) {
		template.update("board.updateHitsBusiness", hits);
	}

	@Override
	public BoardVO getBusinessBoardDetail(String no) {
		return template.selectOne("board.getBusinessBoardDetail", no);
	}

	@Override
	public void businessDelete(String no) {
		template.delete("board.businessDelete", no);
	}

	@Override
	public void business_updateBoard(BoardVO vo) {
		template.update("board.business_updateBoard", vo);
	}

	@Override
	public MemberVO business_getNameById(String id) {
		return template.selectOne("board.business_getNameById", id);
	}

	@Override
	public List<FileVO> getFreeBoardFilePath(String no) {
		return template.selectList("board.getFreeBoardFilePath",no);
	}

	



}
