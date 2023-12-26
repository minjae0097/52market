<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CHAT</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/carChat.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>CHAT</h2>
		<div class="wrap chatList" id="chatList">
			
		</div>
		<div>
			<form id="chat_form">
				<input type="hidden" value="${chatroom_num}" name="chatroom_num">
				<input type="hidden" value="${user_num}" name="mem_num">
				<textarea rows="8" cols="100" placeholder="내용을 입력해주세요" name="message" id="message" style="height: 105px;"></textarea>
				<div id="re_first" style="float: right;width: 80px;">
				<span class="letter-count">300/300</span>
				</div>
				<div  style="float: right;width: 110px; position:relative; bottom:80px;">
				<c:if test="${user_num==seller_num}"><input type="button" value="판매하기" id="sell" data-sell="${chatroom_num}"></c:if>
				<input type="submit" value="입력" > 
				<input type="button" value="뒤로" onclick="history.go(-1)">
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>