<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네생활 MY페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<h2 class="align-center">[동네생활 회원정보]</h2>
			<hr size="1" width="90%" noshade="noshade">
				<ul class="align-center">
					<li>
						<c:if test="${empty member.mem_photo}">
							<img src="${pageContext.request.contextPath}/images/face.png" width="150" height="150" class="my-photo align-center">
						</c:if>
						<c:if test="${!empty member.mem_photo}">
							<img src="${pageContext.request.contextPath}/upload/${board.mem_photo}" width="150" height="150" class="my-photo align-center">
						</c:if>
					</li>
					<li>
						<div id="photo-choice" style="display:none;">
							<input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br>
							<input type="button" value="전송" id="photo_submit">
							<input type="button" value="취소" id="photo_reset"> 
						</div>
					</li>
				</ul>
				<ul class="align-center">
					<li><h3>${member.mem_nickname}님</h3></li>
					<li>주소 ${member.mem_address1}</li>
					<li>가입일 ${member.mem_regdate}</li>
				</ul>
				<hr size="1" width="100%" noshade="noshade">
				<div class="align-center">
				<h3>[관심 게시물 목록]</h3>
				<table>
					<tr>
						<th>제목</th>
						<th>작성자</th>
						<th>등록일</th>
					</tr>
					<c:forEach var="board" items="${boardFavList}">
					<tr>
						<td><a href="${pageContext.request.contextPath}/board/detail.do?board_num=${board.board_num}" target="_blank">${fn:substring(board.board_title,0,12)}</a></td> 
						<td>${member.mem_nickname}</td>
						<td>${board.board_regdate}</td>
					</tr>
					</c:forEach>
				</table>
				<hr size="1" width="100%" noshade="noshade">
				<h3>[댓글단 글 목록]</h3>
				<table>
					<tr>
						<th>작성자</th>
						<th>내용</th>
						<th>등록일</th>
					</tr>
					<c:forEach var="board" items="${boardReplyList}">
					<tr>
						<td><a href="${pageContext.request.contextPath}/board/detail.do?board_num=${board.board_num}" target="_blank">${board.mem_nickname}</a></td> 
						<td>${board.re_content}</td>
						<td>${board.re_regdate}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			</div>
		</div>
</body>
</html>