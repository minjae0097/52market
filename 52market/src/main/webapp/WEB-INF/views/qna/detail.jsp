<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSH.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<br>
	<br>
	<br>
	<br>
	<div class="align-left">
	<h2>&lt;&lt; ${qna.qna_title} &gt;&gt;</h2>
	</div>
	
	<div class="align-right">
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
	</div>
	
	<br>
	<hr size="1" noshade="noshade" width="100%">
	<br>
	<c:if test="${!empty qna.question_filename}">
	<br>
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/upload/${qna.question_filename}" class="detail-img">
	</div>
	</c:if>
	<p>
		${qna.question_content}
	</p>
	<br>
	<hr size="1" noshade="noshade" width="100%">
	<ul class="align-left">
	<li>
		작성일 : ${qna.question_regdate}
	</li>
	</ul>
	<%-- 답변이 안되어 있을 때만 바로 아래에 표시 --%>
	<c:if test="${qna.ask_content==null}">
	<ul class="align-right">
		<li>
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
	</c:if>
<!-- 	<hr size="1" noshade="noshade" width="100%"> -->

	<%-- if --%>
	<c:if test="${qna.ask_content!=null}">
	<br>
	<br>
	<br>
	<br>
	<h2>&lt;&lt; ${qna.mem_nickname}님, 답변 드립니다. &gt;&gt;</h2>
	
	<div class="align-right">
	<ul class="detail-info">
		<li>
			관리자
		</li>
	</ul>
	</div>
	<br>
	<hr size="1" noshade="noshade" width="100%">
		<br>
			<p>
				${qna.ask_content}
			</p>
		<br>
	<hr size="1" noshade="noshade" width="100%">
	</c:if>
	<%-- if 끝 --%>
	<ul class="align-left">
		<c:if test="${!empty qna.ask_content}">
			<li>답변일 : ${qna.ask_regdate}</li>
		</c:if>
	</ul>
	
	<c:if test="${qna.ask_content!=null}">
	<ul class="align-right">
		<li>
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
	</c:if>
	
</div>
</body>
</html>
