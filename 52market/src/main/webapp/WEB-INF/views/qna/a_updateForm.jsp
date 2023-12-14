<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 답변글 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>답변 작성</h2>
		<form id="aUpdate_form" action="aUpdate.do" method="post">
			<input type="hidden" name="qna_num" value="${qna.qna_num}">
			<ul>
				<li>
					<label for="question_content">문의글</label>
					<textarea rows="5" cols="30" readonly="readonly">${qna.question_content}</textarea>
				</li>
				<li>
					<label for="ask_content">답변</label>
					<textarea rows="5" cols="30" name="ask_content" id="ask_content"></textarea>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="답변등록">
				<input type="button" value="목록" onclick="location.herf='list.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>