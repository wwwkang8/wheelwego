<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form action="${pageContext.request.contextPath}/mypage/checkPasswordForm.do">
  <input type="submit" value="탈퇴">
  <input type="hidden" name="command" value="deleteAccount">
</form>

<form action="${pageContext.request.contextPath}/mypage/checkPasswordForm.do">
  <input type="submit" value="회원정보수정">
  <input type="hidden" name="command" value="update_form">
</form>

