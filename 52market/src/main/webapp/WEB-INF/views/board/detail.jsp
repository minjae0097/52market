<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네생활 게시글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.reply.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<h2>${board.board_title}</h2>
			<ul class="detail-info">
				<li>
					<c:if test="${!empty user_photo}">
						<img src="${pageContext.request.contextPath}/upload/${board.board_photo}" width="40" height="40" class="my-photo">
					</c:if>
					<c:if test="${empty user_photo}">
						<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
					</c:if>
				</li>
				<li>
					${board.mem_id}<br>
					조회 ${board.board_hit}
				</li>
			</ul>
			<hr size="1" noshade="noshade" width="100%">
			<c:if test="${!empty board.board_filename}">
				<div class="align-center">
					<img src="${pageContext.request.contextPath}/upload/${board.board_filename}" class="detail-img">
				</div>
			</c:if>
			<p>
				${board.board_content}
			</p>
			<hr size="1" noshade="noshade" width="100%">
			<ul class="detail-sub">
				<li>
					<img id="output_fav" data-num="${board.board_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
					좋아요
					<span id="output_fcount"></span>
				</li>
				<li>
					<c:if test="${!empty board.board_modifydate}">
						최근 수정일 : ${board.board_modifydate}
					</c:if>
					작성일 : ${board.board_regdate}
					<c:if test="${user_num == board.mem_num}">
						<input type="button" value="수정" onclick="location.href='updateForm.do?board_num=${board.board_num}'">
						<input type="button" value="삭제" id="delete_btn">
						<script type="text/javascript"> <%-- 버튼과 스크립트가 같이 보여지게 하기 위해 안에다 만듬 --%>
							let delete_btn = document.getElementById('delete_btn');
							//이벤트 연결
							delete_btn.onclick = function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									location.replace('delete.do?board_num=${board.board_num}');
								}
							};
						</script>
					</c:if>
				</li>
			</ul>
			<!-- 댓글 시작 -->
			<!-- 댓글 끝 -->
		</div>
	</div>
</body>
</html>