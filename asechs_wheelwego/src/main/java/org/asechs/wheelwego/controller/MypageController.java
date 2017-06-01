	package org.asechs.wheelwego.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asechs.wheelwego.model.MypageService;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MypageController {
	@Resource
	private MypageService mypageService;

	@RequestMapping("afterLogin_mypage/wishlist.do")
	public ModelAndView showWishList(){
		return new ModelAndView();
	}
	
	/**
	 * 판매자가
	 * 마이페이지로 이동할때
	 * 판매자 아이디에 해당하는 푸드트럭이  테이블에 존재하는지 검사하여
	 * 존재한다면 푸드트럭 정보를 같이 보내준다.
	 * @return
	 */
	@RequestMapping("afterLogin_mypage/mypage.do")
	public ModelAndView showMyTruckpage(HttpServletRequest request){
		HttpSession session= request.getSession(false);
		ModelAndView mv=new ModelAndView("");
			MemberVO memberVO=(MemberVO)session.getAttribute("memberVO");
			String truckNumber=mypageService.findtruckNumberBySellerId(memberVO.getId());
			mv.addObject("truckNumber", truckNumber);
		mv.setViewName("mypage/mypage.tiles");
		return mv;
	}
	/**
	 * 판매자가 트럭을 등록하는 경우
	 * 파일 경로와 함께 트럭정보를 저장한다.
	 * @param truckVO
	 * @param request
	 * @return
	 */
	@RequestMapping("afterLogin_mypage/registerFoodtruck.do")
	public String registerFoodtruck(TruckVO truckVO, HttpServletRequest request){
		MemberVO memberVO=(MemberVO)request.getSession(false).getAttribute("memberVO");
		truckVO.setSellerId(memberVO.getId());
		mypageService.registerFoodtruck(truckVO);
		return "mypage/registerMyfoodtruck_result.tiles";
	}
	/**
	 * 나의 푸드트럭 설정페이지이동
	 * 아이디에 일치하는 푸드트럭을 찾아서 정보를 함께 보낸다.
	 */
	@RequestMapping("afterLogin_mypage/myfoodtruck_page.do")
	public ModelAndView showMyFoodtruck(HttpServletRequest request){
		MemberVO memberVO=(MemberVO)request.getSession(false).getAttribute("memberVO");
		String truckNumber=mypageService.findtruckNumberBySellerId(memberVO.getId());
		TruckVO truckVO=mypageService.findtruckInfoByTruckNumber(truckNumber);
		return new ModelAndView("mypage/myfoodtruck_page.tiles","truckVO",truckVO);
	}
	/**
	 * 푸드트럭 정보를 업데이트
	 * @return
	 */
	@RequestMapping("afterLogin_mypage/updateMyfoodtruck.do")
	public String updateMyfoodtruck(TruckVO truckVO, HttpServletRequest request){
		mypageService.updateMyfoodtruck(truckVO);
		return "redirect:/afterLogin_mypage/myfoodtruck_page.do";
	}
	
	@RequestMapping("afterLogin_mypage/myfoodtruck_menuList.do")
	public ModelAndView showMenuList(HttpServletRequest request){
		MemberVO memberVO=(MemberVO)request.getSession(false).getAttribute("memberVO");
		String truckNumber=mypageService.findtruckNumberBySellerId(memberVO.getId());
		List<FoodVO> menuList=mypageService.showMenuList(truckNumber);
		System.out.println("menuList : "+menuList);
		return new ModelAndView("mypage/myfoodtruck_menuList.tiles","menuList",menuList);
	}
	@RequestMapping(method=RequestMethod.POST,value="afterLogin_mypage/registerMenuList.do")
	public String updateMenuList(HttpServletRequest request, TruckVO truckVO){
		System.out.println(truckVO);
		MemberVO memberVO=(MemberVO)request.getSession(false).getAttribute("memberVO");
		String truckNumber=mypageService.findtruckNumberBySellerId(memberVO.getId());
		mypageService.registerMenuList(truckVO.getFoodList(),truckNumber);
		return"";
	}
}