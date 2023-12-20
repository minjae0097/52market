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
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>CHAT</h2>
		<div class="wrap" style="background-color:gray;overflow: auto;width: 800px; height:800px;">
			<c:forEach var="list" items="${list}">
				<c:if test="${user_num!=list.mem_num}">
				<div class="chat ch1">
					<div class="textbox">
						${list.message}
					</div>
				</div>
				</c:if>
				<c:if test="${user_num==list.mem_num}">
				<div class="chat ch2">
					<div class="textbox">
						${list.message}
					</div>
				</div>
				</c:if>
			</c:forEach>
		</div>
		<div>
			<form action="insertChatCar.do">
				<input type="hidden" value="${chatroom_num}" name="chatroom_num">
				<input type="hidden" value="${user_num}" name="mem_num">
				<textarea rows="10" cols="100" placeholder="내용을 입력해주세요" name="message"></textarea>
				<input type="submit" value="입력">
				<input type="button" value="목록" onclick="${pageContext.request.contextPath}/chatting/chattingListForBuyerCar.do">
			</form>
		</div>
	</div>
</div>
</body>
</html>