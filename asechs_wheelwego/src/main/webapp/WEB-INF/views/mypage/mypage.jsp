<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
function checkDelete(){
	if (confirm("정말 탈퇴하시겠습니까??") == true){    //확인
	    return true;
	}else{   //취소
	    return false;
	}
}
</script>

<form action="${pageContext.request.contextPath}/afterLogin_mypage/checkPasswordForm.do" onsubmit="return checkDelete()">
  <input type="submit" value="탈퇴">
  <input type="hidden" name="command" value="deleteAccount">
</form>

<form action="${pageContext.request.contextPath}/afterLogin_mypage/checkPasswordForm.do">
  <input type="submit" value="회원정보수정">
  <input type="hidden" name="command" value="update_form">
</form>

<c:choose>
	<c:when test="${sessionScope.memberVO.memberType=='seller'}">
		<a href="${pageContext.request.contextPath}/afterLogin_mypage/myfoodtruck_page.do">MYTRUCK 설정</a><br>
		<a href="">예약관리</a>
	</c:when>
	<c:otherwise>
		<a href="${pageContext.request.contextPath}/afterLogin_mypage/wishlist.do">단골트럭</a><br>
		<a href="">My Review List</a>
	</c:otherwise>
</c:choose>