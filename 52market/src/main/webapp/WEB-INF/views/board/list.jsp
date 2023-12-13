<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BoardList</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
	window.onload = function(){
		let myForm = document.getElementById('search_form');
		//이벤트 연결
		myForm.onsubmit = function(){
			let keyword = document.getElementById('keyword');
			if(keyword.value.trim()==''){
				alert('검색어를 입력하세요');
				keyword.value='';
				keyword.focus();
				return false;
			}
		};
	};
</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<div class="align-center">
				<img src="${pageContext.request.contextPath}/images/동네생활.png" width="900">
			</div>
			<form class="align-right" id="search_form" action="list.do" method="get">
				<ul>
					<li>
						<select name="category">
							<option>---필터---</option>
							<option>동네생활</option>
							<option>동네백과</option>
							<option>동네맛집</option>
							<option>동네소식</option>
							<option>취미생활</option>
							<option>해주세요</option>
							<option>동네질문</option>
							<option>생활정보</option>
						</select>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option> 
							<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option> 
						</select>	
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
						<input type="submit" value="검색">
					</li>
					<li>
					</li>
				</ul>
			</form>
			<div>
				<input type="button" value="마이페이지" onclick="location.href='board_myPage.do'">
			</div>
			<c:if test="${count==0}">
				<div class="result-display">
					표시할 게시물이 없습니다.
				</div>
			</c:if>
			<c:if test="${count > 0}">
				<table>
					<tr>
						<th>글번호</th>
						<th>제목</th>
						<th>내용</th>
						<th>위치</th>
						<th>조회수</th>
					</tr>
					<c:forEach var="board" items="${list}">
						<tr>
							<td>${board.board_num}</td>
							<td><a href="detail.do?board_num=${board.board_num}">${board.board_title}</a></td>
							<td>${board.board_content}</td>
							<td></td>
							<%-- <td>${board.mem_address1}</td> --%>
							<td>${board.board_hit}</td>
						</tr>
					</c:forEach>
				</table>
				<div class="align-center">${page}</div>
			</c:if>
			<div class="list-space align-right">
				<input type="button" value="글쓰기" onclick="location.href='writeForm.do'"
					<c:if test="${empty user_num}">disabled="disabled"</c:if>
				>
			</div>
		</div>
	</div>
</body>
</html>