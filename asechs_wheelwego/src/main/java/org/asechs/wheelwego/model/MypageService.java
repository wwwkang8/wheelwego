package org.asechs.wheelwego.model;

import java.util.List;

import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.TruckVO;

public interface MypageService {
	void registerFoodtruck(TruckVO tvo);  //푸드트럭 등록하기 -트럭정보 저장 및 파일경로 저장

	TruckVO findtruckInfoByTruckNumber(String truckNumber);  //트럭번호로 트럭정보찾기
	
	String findtruckNumberBySellerId(String sellerId);  //아이디로 트럭번호찾기

	void updateMyfoodtruck(TruckVO truckVO); //푸드트럭 설정 수정하기 - 트럭정보 수정 및 파일경로 수정

	List<FoodVO> showMenuList(String truckNumber); //트럭번호에 해당하는 메뉴 리스트보기

	//메뉴아이디에 따른 메뉴수정으로 바꿔야함, 수정해주고 id로 다시 찾아서 보내야함
	
	void registerMenuList(List<FoodVO> foodList, String truckNumber);  //트럭번호에 해당하는 메뉴리스트 등록하기
}
