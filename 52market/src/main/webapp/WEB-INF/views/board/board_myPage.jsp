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
						<c:if test="${empty board.mem_photo}">
							<img src="${pageContext.request.contextPath}/images/face.png" width="150" height="150" class="my-photo align-center">
						</c:if>
						<c:if test="${!empty board.mem_photo}">
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
					<li><h3>${board.mem_nickname}님</h3></li>
					<li>주소 ${board.mem_address1}</li>
					<li>가입일 ${board.mem_regdate}</li>
				</ul>
				<hr size="1" width="90%" noshade="noshade">
				<ul>	
					<li class="align-center">
						<div>
							<a href="${pageContext.request.contextPath}/board/board_myPageList.do"><b>작성할 글</b></a>
						</div>
					</li>
					<li class="align-center">
						<div>
							<a href="${pageContext.request.contextPath}/board/board_myPageRelpyList.do"><b>댓글단 글</b></a>
						</div>
					</li>
				</ul>
				<hr size="1" width="90%" noshade="noshade">
				<ul>
					<li>
						<h3>
							비밀번호 수정
							<input type="button" value="비밀번호 수정" onclick="${pageContext.request.contextPath}/member/myPage.do">
						</h3>
					</li>
					<li>
						<h3>
							회원탈퇴
							<input type="button" value="회원탈퇴" onclick="${pageContext.request.contextPath}/member/deleteUserForm.do'">
						</h3>
					</li>
				</ul>
			</div>
		</div>
</body>
</html>