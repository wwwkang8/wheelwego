package org.asechs.wheelwego.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.asechs.wheelwego.model.FoodTruckService;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FoodTruckController {
	@Resource
	private FoodTruckService foodTruckService;

	/* 검색 결과 푸드트럭 리스트 */
	@RequestMapping("pagingTruckList.do")
	public ModelAndView pagingTruckList(String name, String pageNo) {
		List<TruckVO> searchTruckList = foodTruckService.searchFoodTruckList(name);
		System.out.println(searchTruckList);
		return new ModelAndView("foodtruck/foodtruck_location_select_list.tiles", "pagingList", foodTruckService.resultFoodTruckList(searchTruckList,pageNo));		
	}

	@RequestMapping("searchFoodTruckList.do")
	public ModelAndView searchFoodTruckList(String name){
		System.out.println("searchFoodTruckList"+name);
		return new ModelAndView("redirect:pagingTruckList.do","name",name);
	}
	
	
	/**
	 * 황윤상 GPS 기반 푸드트럭수동검색
	 * @param name
	 * @return
	 */
	@RequestMapping("searchFoodTruckByGPS.do")
	public ModelAndView searchFoodTruckByGPS(String latitude, String longitude) {
		TruckVO gpsInfo = new TruckVO();
		gpsInfo.setLatitude(Double.parseDouble(latitude));
		gpsInfo.setLongitude(Double.parseDouble(longitude));
		List<TruckVO> searchTruckList = foodTruckService.searchFoodTruckByGPS(gpsInfo);
		
		return new ModelAndView("foodtruck/foodtruck_detail.tiles", "truckList", searchTruckList);
	}	
}
