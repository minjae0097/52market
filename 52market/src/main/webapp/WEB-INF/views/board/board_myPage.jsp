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
				<ul class="align-center" id="background">
					<li>
						<c:if test="${empty member.mem_photo}">
							<img src="${pageContext.request.contextPath}/images/face.png" width="150" height="150" class="my-photo align-center">
						</c:if>
						<c:if test="${!empty member.mem_photo}">
							<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="150" height="150" class="my-photo align-center">
						</c:if>
					</li>
					<li>
						<div id="photo-choice" style="display:none;">
							<input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br>
							<input type="button" value="전송" id="photo_submit">
							<input type="button" value="취소" id="photo_reset"> 
						</div>
					</li>
					<li>
					<span id="mypage_nickname">${member.mem_nickname} 님</span><br>
					<span>주소 ${member.mem_address1}</span><br>
					<span>가입일 ${member.mem_regdate}</span>
					</li>
				</ul>
			</div>
				<div class="align-center" id="myPage_list">
				<h3>[관심 게시물 목록]</h3>
				<table>
					<tr>
						<th>제목</th>
						<th>닉네임</th>
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
				<br>
				<h3>[작성한 댓글 목록]</h3>
				<table>
					<tr>
						<th>글번호</th>
						<th>댓글내용</th>
						<th>댓글등록일</th>
					</tr>
					<c:forEach var="board" items="${boardReplyList}">
					<tr>
						<td><a href="${pageContext.request.contextPath}/board/detail.do?board_num=${board.board_num}" target="_blank">${board.board_num}</a></td> 
						<td>${board.re_content}</td>
						<td>${board.re_regdate}</td>
					</tr>
					</c:forEach>
				</table>
				<br>
			</div>
			</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>