<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSH.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('search_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		let keyword = document.getElementById('keyword');
		if(keyword.value.trim()==''){
			alert('검색어를 입력하세요');
			keyword.value = '';
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
		<h2>문의게시판</h2>
		
		<%-- 조건체크 --%>
		<c:if test="${count == 0}">
			<div class="result-display">
					등록한 게시글이 없습니다.
			</div>
		</c:if>
		
		<c:if test="${count > 0}">
			<form id="search_form" action="list.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield" id="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
					<br>
				</ul>
				</form>
				<table>
					<br>
					<tr>
						<th>글번호</th>
						<th>상태</th>
						<th>제목</th>
						<th>등록일</th>
					</tr>
					<c:forEach var="qna" items="${list}">
					<tr>
						<td><a href="detail.do?qna_num=${qna.qna_num}">${qna.qna_num}</a></td>
						<td>
							<c:if test="${qna.ask_content==null}">답변대기</c:if>
							<c:if test="${qna.ask_content!=null}">답변완료</c:if>
						</td>
						<td><a href="detail.do?qna_num=${qna.qna_num}">${qna.qna_title}</a></td>
						<td>${qna.question_regdate}</td>
					</tr>
					</c:forEach>
				</table>
			<div class="list-space align-right">
				<br>
				<input type="button" value="글쓰기" onclick="location.href='writeForm.do'"
			    	<c:if test="${empty user_num}">disabled="disabled"</c:if> 
					>
				<input type="button" value="메인으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">                      
			</div>
				
				<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>