<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알바 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/KJY.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-alba-main">
		<img src="${pageContext.request.contextPath}/upload/${alba.alba_photo}" width="250" height="250" id="alba-photo1">
	<div class="horizontal-area">
		<c:if test="${!empty mem.mem_photo}">
			<img src="${pageContext.request.contextPath}/upload/${mem.mem_photo}" width="30" height="30" class="my-photo">
		</c:if>
		<c:if test="${empty mem.mem_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png" width="30" height="30" class="my-photo">
		<span>${alba.mem_nickname}</span>
		</c:if>
	</div>
	<div class="float-clear">
		<hr width="100%" size="1" noshade="noshade">
	</div>
		<span>${alba.alba_title}</span>
		<div>
		<h3>정보</h3>
		<ul>
			<li>${alba.alba_content1}</li>
		</ul>
		</div>
		<div>
		<h3>상세 정보</h3>
		<ul>
			<li>${alba.alba_content2}</li>
		</ul>
		<h4>위치</h4>
		<ul>
			<li><img src="${pageContext.request.contextPath}/upload/${alba.alba_location}" width="500" height="250"></li>
			<li>${alba.alba_address1}</li>
		</ul>
		<div class="float-clear">
		<hr width="100%" size="1" noshade="noshade">
		<ul class="detail-sub">
		<li></li>
		<li>
		<input type="button" value="수정" onclick="location.href='updateAlbaForm.do?alba_num=${alba.alba_num}'">
		<input type="button" value="삭제" id="delete_btn" onclick="location.href='deleteAlbaForm.do?alba_num=${alba.alba_num}'">
		<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			//이벤트 연결
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('deleteAlba.do?alba_num=${alba.alba_num}');
				}
			};
			</script>
		</li>
		</ul>
		</div>
		</div>
	</div>
</div>
</body>
</html>