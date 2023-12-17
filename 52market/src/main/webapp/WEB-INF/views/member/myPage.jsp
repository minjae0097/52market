<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h3>마이페이지</h3>
		<div class="mypage-div align-center">
			<c:if test="${empty member.mem_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png" width="100%" height="100%" class="my-photo">
			</c:if>
			<c:if test="${!empty member.mem_photo}">
			<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="100%" height="100%" class="my-photo">
			</c:if>
			<button>이미지 변경</button>
		</div>
		<div class="mypage-div">
			<span><b>${member.mem_nickname}</b>님</span>
			<button onclick="location.href='modifyNicknameForm.do'">닉네임 변경</button>
			<ul>
				<li>
					<label>위치 : ${member.mem_address1}</label>
				</li>
				<li>
					<label>이메일 : ${member.mem_email}</label>
				</li>	
				<li>	
					<label>연락처 : ${member.mem_phone}</label>
				</li>
				<li>	
					<label>가입일 : ${member.mem_regdate}</label>
				</li>
			</ul>
			<div class="align-center">
				<button onclick="location.href='modifyMemberForm.do'">회원정보 수정</button>
				<button onclick="location.href='modifyPasswordForm.do'">비밀번호 변경</button>
			</div>
		</div>
		<hr size="1" width="100%">
		<div>
		<h3>나의 거래</h3>
		<ul>
			<li><a>관심목록</a></li>
			<li><a>구매내역</a></li>
			<li><a>판매내역</a></li>
		</ul>
		</div>
		<hr size="1" width="100%">
		<div>
		<h3>동네 생활</h3>
		<ul>
			<li><a>동네생활 활동</a></li>
		</ul>
		</div>
	</div>
</div>
</body>
</html>