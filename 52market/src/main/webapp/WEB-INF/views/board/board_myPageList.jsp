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
			<h2>동네생활 회원정보</h2>
			<div class="mypage-div">
				<h3>프로필 사진</h3>
				<ul>
					<li>
						<c:if test="${empty member.mem_photo}">
							<img src="${pageContext.request.contextPath}/images/face.png" width="200" height="200" class="my-photo">
						</c:if>
						<c:if test="${!empty member.mem_photo}">
							<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="200" height="200" class="my-photo">
						</c:if>
					</li>
					<li>
						<div class="align-center">
							<input type="button" value="수정" id="photo_btn">
						</div>
						<div id="photo-choice" style="display:none;">
							<input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br>
							<input type="button" value="전송" id="photo_submit">
							<input type="button" value="취소" id="photo_reset"> 
						</div>
					</li>
				</ul>
				<h3>동네생활 회원정보</h3>
				<ul>
					<li><h3>${member.mem_nickname}님</h3></li>
					<li>주소 ${memeber.mem_address1}</li>
					<li>가입일 ${member.mem_regdate}</li>
					<li>
						<div>
							<a href="${pageContext.request.contextPath}/board/board_myPageList.do">작성할 글</a>
						</div>
					</li>
					<li>
						<div>
							<a href="${pageContext.request.contextPath}/board/board_myPageRelpyList.do">댓글단 글</a>
						</div>
					</li>
				</ul>
				<hr size="1" width="100%" noshade="noshade">
				<h3>
					비밀번호 수정
					<input type="button" value="비밀번호 수정" onclick="${pageContext.request.contextPath}/member/myPage.do">
				</h3>
				<h3>
					회원탈퇴
					<input type="button" value="회원탈퇴" onclick="${pageContext.request.contextPath}/member/deleteUserForm.do'">
				</h3>
			</div>
			<div class="mypage-div">
				<h3>작성한 글</h3>
				<table>
					<tr>
						<th>제목</th>
						<th>작성자</th>
						<th>등록일</th>
					</tr>
					<c:forEach var="board" items="${boardList}">
					<tr>
						<td><a href="${pageContext.request.contextPath}/board/detail.do?board_num=${board.board_num}" target="_blank">${fn:substring(board.board_title,0,12)}</a></td>					
						<td>${board.mem_id}</td>
						<td>${board.board_regdate}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>