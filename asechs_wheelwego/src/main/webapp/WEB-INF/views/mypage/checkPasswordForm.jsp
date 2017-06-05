<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<script type="text/javascript">
$(document).ready(function(){
	$("#password").keypress(function(event) { 
		if (event.which == 13) {
			alert("click 버튼을 눌러주세요!");
			return false;
		} 
	});
	$("#inputForm").click(function(){
		  var id= "${sessionScope.memberVO.id}";
		  var password=$("#password").val();
		  
	      $.ajax({
	          type:"POST",
	          url:"${pageContext.request.contextPath}/getMemberPasswordAjax.do",  	          
	          data : {id: id, password: password} , 
	          success:function(data){
	        	  if (data == "ok"){
	        		  if ("${param.command}" == "deleteAccount")
	        		  {
	        			  alert("회원 탈퇴가 완료되었습니다!");
	        			  $("#checkPwForm").submit();
	        		  }
	        		  else
	        		  {
	        			  $("#checkPwForm").submit();  
	        		  }
	        	  }

	        	  else
	        	  {
	        		  alert("비밀번호가 일치하지 않습니다! 다시 입력해주세요.");
	        		  $("#password").val("");
	        		  $("#password").focus();
	        		  return;  
	        	  }
	          },
	          error:function(request,status,error){
	              alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	          }
	       });	
	});
});
</script>

<form method="post" action="${pageContext.request.contextPath}/afterLogin_mypage/${param.command}.do" id="checkPwForm">
   패스워드 확인 <input type="password" name="password" id="password" size="10" required="required"><br>
   <input type="hidden" value="${param.command}">
   <input type="button" value="확인" id = "inputForm">
 <div class="row">
 <div class="col-xs-5"></div>
 <div class="col-xs-2">
   Check Password : <input type="password" name="password" id="password" size="10" required="required" class="form-control col-xs-2" autofocus><br>
   <input type="hidden" value="${param.command}"><br><br><br>
   <span><input type="button" value="click!" id="inputForm" class="btn" style="float: right;"></span>
   </div>
    <div class="col-xs-5"></div>
</div>
</form>