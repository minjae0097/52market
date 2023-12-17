<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>문의게시판</h2>
		<div class="list-space align-right">
			<input type="button" value="글쓰기" onclick="location.href='writeForm.do'"
			    <c:if test="${empty user_num}">disabled="disabled"</c:if> 
			>
			<input type="button" value="메인으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">                      
		</div>
		
		<%-- 조건체크 --%>
		<c:choose>
			<%-- 글 x --%>
			<c:when test="${count == 0}">
				<div class="result-display">
					등록한 게시글이 없습니다.
				</div>
			</c:when>
			
			<%-- 관리자 --%>
			<c:when test="${user_auth == 9}">
				<form id="qna_form" action="list.do" method="get">
					<ul class="search">
						<li>
							<select name="keyfield">
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
					</ul>
				</form>
				<table>
					<tr>
						<th>상태</th>
						<th>제목</th>
						<th>등록일</th>
					</tr>
					<c:forEach var="qna" items="${list}">
					<tr>
						<td>
							<c:if test="${empty qna.ask_content}">답변대기</c:if>
							<c:if test="${!empty qna.ask_content}">답변완료</c:if>
						</td>
						<td><a href="detail.do?qna_num=${qna.qna_num}">${qna.qna_title}</a></td>
						<td>${qna.question_regdate}</td>
					</tr>
					</c:forEach>
				</table>
				
				<div class="align-center">${page}</div>
			</c:when>
			
			<%-- 작성자 일치 --%>
			<c:when test="${qna.mem_num == user_num}">
				<form id="qna_form" action="list.do" method="get">
					<ul class="search">
						<li>
							<select name="keyfield">
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
					</ul>
				</form>
				<table>
					<tr>
						<th>글번호</th>
						<th>제목</th>
						<th>등록일</th>
					</tr>
					<c:forEach var="qna" items="${list}">
					<tr>
						<td>${qna.qna_num}</td>
						<td><a href="detail.do?qna_num=${qna.qna_num}">${qna.qna_title}</a></td>
						<td>${qna.mem_num}</td>
						<td>${qna.question_regdate}</td>
					</tr>
					</c:forEach>
				</table>
				<div class="align-center">${page}</div>
			</c:when>
			
			<c:otherwise>
				<div class="result-display">
					등록한 게시글이 없습니다.
				</div>
			</c:otherwise>
		</c:choose>
		
	</div>
</div>
</body>
</html>