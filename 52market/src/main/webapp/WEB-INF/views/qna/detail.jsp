<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2>${qna_title}</h2>
	<ul class="detail-info">
		<li>
			<c:if test="${!empty qna.mem_photo}">
			<img src="${pageContext.request.contextPath}/upload/${qna.mem_photo}" width="40" height="40" class="my-photo">
			</c:if>
			<c:if test="${empty qna.mem_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
			</c:if>
		</li>
		<li>
			${qna.mem_nickname}<br>
		</li>
	</ul>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${!empty qna.question_filename}">
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/upload/${qna.question_filename}" class="detail-img">
	</div>
	</c:if>
	<p>
		${qna.question_content}
	</p>
	<hr size="1" noshade="noshade" width="100%">
	<ul class="detail-sub">
		<li>
			작성일 : ${qna.question_regdate}
			<c:if test="${user_num == qna.mem_num}">
			<input type="button" value="삭제" id="delete_btn">
			<input type="button" value="목록" onclick="location.href='list.do'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				//이벤트 연결
				delete_btn.onclick=function(){
					let choice = confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('delete.do?qna_num=${qna.qna_num}');
					}
				};
			</script>
			</c:if>
		</li>
	</ul>
	</div>
</div>
</body>
</html>
