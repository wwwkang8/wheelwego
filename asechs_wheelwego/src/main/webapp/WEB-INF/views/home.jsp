<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- GPS(위도, 경도) 가져오기 --- by 황윤상 -->
<script type="text/javascript">
function geoFindMe() {
	var name = document.getElementById("name").value;
	  if (!navigator.geolocation){
		 alert("지오로케이션을 지원하지 않습니다!");
		 return;
	  }
	  function success(position) {
	    var latitude  = position.coords.latitude;
	    var longitude = position.coords.longitude;
	    location.href = "${pageContext.request.contextPath}/searchFoodTruckByGPS.do?latitude="+latitude+"&longitude="+longitude+"&name="+name;
	  };

	  function error() {
		  alert("사용자의 위치를 찾을 수 없습니다!");
	  };
	  navigator.geolocation.getCurrentPosition(success, error);
}
</script>

<!-- 다음 우편 API --- by 황윤상 -->
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }
                	
            	var mapInfo = naver.maps.Service.geocode({
            	    address: fullAddr
            	}, function(status, response) {
            	    if (status !== naver.maps.Service.Status.OK) {
            	    	alert('입력한 주소를 찾을 수 없습니다.');
            	    	return;
            	    }
            	    var result = response.result, // 검색 결과의 컨테이너
            	        items = result.items; // 검색 결과의 배열
            	        
            	    var latitude  = items[0].point.y;
            	    var longitude = items[0].point.x;
            	    
            	    location.href = "${pageContext.request.contextPath}/searchFoodTruckByGPS.do?latitude="+latitude+"&longitude="+longitude+"&name="+name;
            	});                
            }
        }).open();
    }
</script>

<!-- Header -->
<header>
   <div class="container" id="maincontent" tabindex="-1">
      <div class="row">
         <div class="col-lg-12">
            <div class="intro-text">
               <h1 class="name">Wheel<img src="${pageContext.request.contextPath}/resources/img/포크.png">  we go!</h1>
               <hr>
               </div>

            <div class="col-lg-3"></div>
            <div class="social col-lg-1">
              <ul>
                <li>
                  <!-- <a href=""><i class="fa fa-map-marker fa-3x"></i></a> -->
                 <a class="dropdown-toggle" href="#" data-toggle="dropdown"><i class="fa fa-map-marker fa-3x"></i></a>
                 <div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
                 
                 <button onclick="sample6_execDaumPostcode()">수동</button>&nbsp;
                 <button onclick="geoFindMe()">자동</button>
                 
<!-- 	              <form accept-charset="UTF-8" onsubmit="geoFindMe()">
					  <input id="user_username" style="margin-bottom: 15px;" type="text" name="user[username]" size="30" />
					  <input id="user_password" style="margin-bottom: 15px;" type="password" name="user[password]" size="30" />
					  <input id="user_remember_me" style="float: left; margin-right: 10px;" type="checkbox" name="user[remember_me]" value="1" />
					  <label class="string optional" for="user_remember_me"> Remember me</label>
					  <input class="btn btn-primary" style="clear: left; width: 100%; height: 32px; font-size: 13px;" type="submit" name="commit" value="TEST" />
				  </form> -->
	            </div>
                </li>
              </ul>
            </div>
               <div class="col-lg-4">
			  <!-- 푸드트럭 검색 폼 -->
<!--              	 <input type="text" value="" placeholder="Search foodtruck!" class="email" id="name" name="name" required="required">
             	 <input type="button" class="subscribe" name="search" value="Search" onclick="geoFindMe()"> --> 	 			  
		    <form class="subscribe_form">
              <input type="text" value="" placeholder="Search foodtruck!" class="email" id="name" name="name" required="required">
              <input type="button" class="subscribe" name="search" value="Search" onclick="geoFindMe()">
            </form>
           </div>
             <div class="col-lg-4"></div>
         </div>
      </div>
   </div>
</header>

<section id="portfolio">
   <div class="container">

         <div class="col-lg-12 text-center">
            <h2 class="page-header">recommend list</h2><br><br><br>
         </div>
         
      <div class="row" >
      <!-- begin, end 값을 설정하여 전체 트럭 목록 중, 6개만 랜덤으로 뽑아낸다 -->
      <c:forEach items="${trucklist}" begin="0" end="8" var="truckVO">
         <div class="col-lg-4 col-sm-6 portfolio-item" >
            <div class="flip-container"
               ontouchstart="this.classList.toggle('hover');" style="margin: 0 auto;">
               <div class="flipper">
                  <a href="#portfolioModal1" class="portfolio-link"
                     data-toggle="modal">
                     <div class="front" >
                        <img class="img-circle  center-block food-img img-responsive"
                           src="${pageContext.request.contextPath}/resources/upload/${truckVO.fileVO.filepath}"
                           alt="" >
                     </div>
                     <div class="back cbx-back-side">
                        <div class="text-center back-single-text">
                           <p>${truckVO.foodtruckName}</p><p class="truck-simple-info"><br><br><br>${truckVO.introduction}<br>위치<br>평점</p>
                        </div>
                     </div>
                  </a>
               </div>
            </div>
         </div>
         </c:forEach>

       </div>
      <!-- row -->
      <hr>
   </div>
   <!-- container -->
</section>