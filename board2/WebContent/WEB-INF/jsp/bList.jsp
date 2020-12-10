<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>
</head>
<body>
	<div>
		<a href="/bRegMod?typ=${typ}"><button>글쓰기</button></a>
	</div>

	<div>
		<table>
			<tr>
				<td>번호</td>
				<td>제목</td>
				<td>조회수</td>
				<td>작성일</td>
			</tr>

			<c:forEach items="${data}" var="item">
				<tr class="pointer" onclick="clkItem(${typ}, ${item.i_board})">
					<td>${item.i_board}</td>
					<td>${item.title}</td>
					<td>${item.hits}</td>
					<td>${item.r_dt}</td>
				</tr>
			</c:forEach>


		</table>
	</div>
	


</body>
</html>

<script> 
	function clkItem(typ, i_board){
		//console.log('typ' + typ, 'i_board' + i_board);
		//var url ='/bDetial?typ=' + typ + '&i_board' + i_board;
		var url = `/bDetail?typ=\${typ}&i_board=\${i_board}`;
		//$가 자바 스크립트의 예약어라서 jsp에서 값을 끌어오려면 앞에 \를 붙혀라
		console.log('url : '+url);
		location.href =url;// 주소값이동
	}

</script>
