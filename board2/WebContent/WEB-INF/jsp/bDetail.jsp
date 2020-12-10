<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>디테일</title>
</head>
<body>

<div>
	<a href="/bList"> 리스트 </a>
	<form action="/bDetail" method="post" onsubmit="return isDel(event);">
		<input type="text" name="typ" value="${typ}">
		<input type="hidden" name="i_board" value="${data.i_board}">
		<input type="submit" value="삭제" >
	</form>
		<a href="/bRegMod?typ=${typ}&i_board=${data.i_board}"> 
		<button>수정</button> 
		</a>
</div>
<div>
		<div> 조회수 : ${data.hits }</div>
		<div>번호 : ${data.i_board}</div>
		<div>제목 : ${data.title }</div>
		<div>내용 : ${data.ctnt }</div>
		<div>작성일 : ${data.r_dt }</div>
		
</div>


</body>

</html>
<script>
	function isDel(e){
		var result = confirm('삭제하시겠습니까?');//confirm 내장함수로 yes나 no 버튼으로 값을 가져온다.
		if(!result){
			e.preventDefault();
		}
	}
	<c:if test="${msg != null}">
		alert('${msg}');
	</c:if>
</script>