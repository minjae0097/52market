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
	<h2>${qna.qna_title}</h2>
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
	
	<%-- if --%>
	<c:if test="${qna.ask_content!=null}">
	<h2>${qna.mem_nickname}님, 답변 드립니다.</h2>
	<ul class="detail-info">
		<li>
			<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
		</li>
		<li>
			관리자<br>
		</li>
	</ul>
	<hr size="1" noshade="noshade" width="100%">
		<br>
			<p>
				${qna.ask_content}
			</p>
		<br>
	<hr size="1" noshade="noshade" width="100%">
	</c:if>
	<%-- if 끝 --%>
	
	<ul class="detail-sub">
		<li>
			작성일 : ${qna.question_regdate}
			답변일 : ${qna.ask_regdate}
			<%-- 관리자만 답글 가능 --%>
			<c:if test="${user_auth == 9 && !empty qna.ask_content}">
				<input type="button" value="수정" onclick="location.href='aUpdateForm.do?qna_num=${qna.qna_num}'">
			</c:if>
			<c:if test="${user_auth == 9 && empty qna.ask_content}">
				<input type="button" value="답글달기" onclick="location.href='aUpdateForm.do?qna_num=${qna.qna_num}'">
			</c:if>
			<input type="button" value="삭제" id="delete_btn">
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
			<input type="button" value="목록" onclick="location.href='list.do'">
		</li>
	</ul>
	</div>
</div>
</body>
</html>
