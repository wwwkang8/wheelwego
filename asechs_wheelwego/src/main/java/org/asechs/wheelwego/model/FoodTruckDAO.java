package org.asechs.wheelwego.model;

import java.util.List;

import org.asechs.wheelwego.model.vo.TruckVO;

public interface FoodTruckDAO {

	List<TruckVO> truckList(String string, String id, String trucknumber);

}
