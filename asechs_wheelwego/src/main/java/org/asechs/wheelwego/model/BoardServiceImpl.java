package org.asechs.wheelwego.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asechs.wheelwego.model.vo.BoardVO;
import org.asechs.wheelwego.model.vo.FileVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardServiceImpl implements BoardService {
	@Resource
	private BoardDAO boardDAO;
	
//////////강정호. 자유게시판 Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public ListVO getFreeBoardList(String pageNo) {
		int totalCount=boardDAO.getFreeBoardTotalContentCount();
		PagingBean pagingBean=null;
		if(pageNo==null)
			pagingBean=new PagingBean(totalCount,1);
		else
			pagingBean=new PagingBean(totalCount,Integer.parseInt(pageNo));		
		/*HashMap<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("startRowNumber",pagingBean.getStartRowNumber());
		paramMap.put("endRowNumber", pagingBean.getEndRowNumber());*/
		return new ListVO((List<BoardVO>) boardDAO.getFreeBoardList(pagingBean),pagingBean);
	}
	
	@Override
	public void freeboardWrite(BoardVO bvo, HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		MemberVO mvo=(MemberVO) session.getAttribute("memberVO");
		bvo.setId(mvo.getId());
		// 글 정보먼저 insert한다.
		String contentNo=boardDAO.freeboardWrite(bvo);
		
		// 강정호. 파일 업로드. 컨트롤러에 넣기에는 너무 길어서 서비스에 넣었습니다.
		// 그 다음 파일 이름을 insert한다
		String uploadPath="C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
		List<MultipartFile> fileList=bvo.getFile();
		//ArrayList<String> filePath=new ArrayList<String>();
		ArrayList<String> nameList=new ArrayList<String>();
		for(int i=0; i<fileList.size(); i++){
			if(fileList.isEmpty()==false){
				BoardVO boardVO=new BoardVO();
				FileVO fileVO=new FileVO();
				String fileName=fileList.get(i).getOriginalFilename();
				if(fileName.equals("")==false){
					try{
						fileList.get(i).transferTo(new File(uploadPath+fileName));
						fileVO.setNo(contentNo);
						fileVO.setFilepath(fileName);
						boardVO.setFileVO(fileVO);
						nameList.add(fileName);
						//filePath.add(uploadPath+fileName);
						boardDAO.freeboardWriteFileUpload(boardVO);
					}catch(IllegalStateException | IOException e){
						e.printStackTrace();
						}
					}
			}
		}
	}
	
	//자유게시판 업데이트
	@Override
	public void updateBoard(BoardVO vo) {
		// 사용자가 수정하고 하는 파일명
		for(int i=0;i<vo.getFile().size();i++){
			System.out.println("controller 에서 보낸 파일 이름 :"+vo.getFile().get(i).getOriginalFilename());
		}
		String contentNo=boardDAO.updateBoard(vo);
		String uploadPath="C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
		List<MultipartFile> fileList=vo.getFile();
	
		for(int i=0; i<fileList.size(); i++){
			if(fileList.isEmpty()==false){
				BoardVO boardVO=new BoardVO();
				FileVO fileVO=new FileVO();
				String fileName=fileList.get(i).getOriginalFilename();
				System.out.println(fileName);
				if(fileName.equals("")==false){
					try{
						fileList.get(i).transferTo(new File(uploadPath+fileName));
						// 기존의 존재하는 파일명 갖고오기
						List<FileVO> fvo= boardDAO.getFreeBoardFilePath(contentNo);
						for(int j=0;j<fvo.size();j++){
							fileVO.setBeforefilepath(fvo.get(j).getFilepath());
						}
						fileVO.setNo(contentNo);
						fileVO.setFilepath(fileName);
						boardVO.setFileVO(fileVO);
						System.out.println(boardVO);
						boardDAO.freeboardUpdateFileUpload(boardVO);
					}catch(IllegalStateException | IOException e){
						e.printStackTrace();
						}
					}
			}
		}
	}

	@Override
	public MemberVO getNameById(BoardVO bvo ) {
		return boardDAO.getNameById(bvo);
	}

	@Override
	public BoardVO getFreeBoardDetail(String no) {
		return boardDAO.getFreeBoardDetail(no);
	}

	@Override
	public void freeboardDelete(String no) {
		boardDAO.freeboardDelete(no);
	}

	//자유게시판 조회수 업데이트
	@Override
	public void updateHits(int hits) {
		boardDAO.updateHits(hits);
	}
	
	@Override
	public List<FileVO> getFreeBoardFilePath(String no) {
		return boardDAO.getFreeBoardFilePath(no);
	}
	
	
//////////강정호. 창업게시판 Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*-------------------창업게시판-------------------------------------------------*/
	
	@Override
	public ListVO getBusinessInfoBoardList(String pageNo) {
		int totalCount=boardDAO.getBusinessInfoBoardTotalContentCount();
		PagingBean pagingBean=null;
		if(pageNo==null)
			pagingBean=new PagingBean(totalCount,1);
		else
			pagingBean=new PagingBean(totalCount,Integer.parseInt(pageNo));		
		/*HashMap<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("startRowNumber",pagingBean.getStartRowNumber());
		paramMap.put("endRowNumber", pagingBean.getEndRowNumber());*/
		return new ListVO((List<BoardVO>) boardDAO.getBusinessInfoBoardList(pagingBean),pagingBean);
	}
	
	@Override
	public void businessWrite(BoardVO bvo, HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		MemberVO mvo=(MemberVO) session.getAttribute("memberVO");
		bvo.setId(mvo.getId());
		// 글 정보먼저 insert한다.
		String contentNo=boardDAO.businessWrite(bvo);
		
		// 강정호. 파일 업로드. 컨트롤러에 넣기에는 너무 길어서 서비스에 넣었습니다.
		// 그 다음 파일 이름을 insert한다
		String uploadPath="C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
		List<MultipartFile> fileList=bvo.getFile();
		//ArrayList<String> filePath=new ArrayList<String>();
		ArrayList<String> nameList=new ArrayList<String>();
		for(int i=0; i<fileList.size(); i++){
			if(fileList.isEmpty()==false){
				BoardVO boardVO=new BoardVO();
				FileVO fileVO=new FileVO();
				String fileName=fileList.get(i).getOriginalFilename();
				if(fileName.equals("")==false){
					try{
						fileList.get(i).transferTo(new File(uploadPath+fileName));
						fileVO.setNo(contentNo);
						fileVO.setFilepath(fileName);
						boardVO.setFileVO(fileVO);
						nameList.add(fileName);
						//filePath.add(uploadPath+fileName);
						boardDAO.businessWriteFileUpload(boardVO);
					}catch(IllegalStateException | IOException e){
						e.printStackTrace();
						}
					}
			}
		}
		
	}
	
	@Override
	public void updateHitsBusiness(int hits) {
		boardDAO.updateHitsBusiness(hits);
	}

	@Override
	public BoardVO getBusinessBoardDetail(String no) {
		return boardDAO.getBusinessBoardDetail(no);
	}

	@Override
	public void businessDelete(String no) {
		boardDAO.businessDelete(no);
	}

	@Override
	public void business_updateBoard(BoardVO vo) {
		boardDAO.business_updateBoard(vo);
		
	}

	
	@Override
	public MemberVO business_getNameById(BoardVO bvo) {
		return boardDAO.business_getNameById(bvo);
	}
	
	@Override
	public List<FileVO> getBusinessFilePath(String no) {
		return boardDAO.getBusinessFilePath(no);
	}


//////////강정호. Q&A게시판 Service/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public ListVO getQnABoardList(String pageNo) {
		int totalCount=boardDAO.getQnATotalContentCount();
		PagingBean pagingBean=null;
		if(pageNo==null)
			pagingBean=new PagingBean(totalCount,1);
		else
			pagingBean=new PagingBean(totalCount,Integer.parseInt(pageNo));		
		/*HashMap<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("startRowNumber",pagingBean.getStartRowNumber());
		paramMap.put("endRowNumber", pagingBean.getEndRowNumber());*/
		return new ListVO((List<BoardVO>) boardDAO.getQnABoardList(pagingBean),pagingBean);
	}
	
	//강정호. Q&A 글 등록
		@Override
		public void qnaWrite(BoardVO bvo, HttpServletRequest request) {
			HttpSession session=request.getSession(false);
			MemberVO mvo=(MemberVO) session.getAttribute("memberVO");
			bvo.setId(mvo.getId());
			// 글 정보먼저 insert한다.
			String contentNo=boardDAO.qnaWrite(bvo);
			
			// 강정호. 파일 업로드. 컨트롤러에 넣기에는 너무 길어서 서비스에 넣었습니다.
			// 그 다음 파일 이름을 insert한다
			String uploadPath="C:\\Users\\KOSTA\\git\\wheelwego\\asechs_wheelwego\\src\\main\\webapp\\resources\\img\\";
			List<MultipartFile> fileList=bvo.getFile();
			//ArrayList<String> filePath=new ArrayList<String>();
			ArrayList<String> nameList=new ArrayList<String>();
			for(int i=0; i<fileList.size(); i++){
				if(fileList.isEmpty()==false){
					BoardVO boardVO=new BoardVO();
					FileVO fileVO=new FileVO();
					String fileName=fileList.get(i).getOriginalFilename();
					if(fileName.equals("")==false){
						try{
							fileList.get(i).transferTo(new File(uploadPath+fileName));
							fileVO.setNo(contentNo);
							fileVO.setFilepath(fileName);
							boardVO.setFileVO(fileVO);
							nameList.add(fileName);
							//filePath.add(uploadPath+fileName);
							boardDAO.qnaWriteFileUpload(boardVO);
						}catch(IllegalStateException | IOException e){
							e.printStackTrace();
							}
						}
				}
			}
			
		}
		
		@Override
		public BoardVO getqnaBoardDetail(String no) {
			return boardDAO.getqnaBoardDetail(no);
		}

	
	
	@Override
	public List<FileVO> getqnaFilePath(String no) {
		return boardDAO.getqnaFilePath(no);
	}
	
	

	@Override
	public MemberVO qna_getNameById(BoardVO bvo) {
		return boardDAO.qna_getNameById(bvo);
	}

	



}















