<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네생활 게시글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/PIH.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.reply.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<ul class="detail-info">
				<li>
					<c:if test="${!empty member.mem_photo}">
						<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="40" height="40" class="my-photo" onclick="location.href='${pageContext.request.contextPath}/board/board_myPage.do'">
					</c:if>
					<c:if test="${empty member.mem_photo}">
						<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo" onclick="location.href='${pageContext.request.contextPath}/board/board_myPage.do'">
					</c:if>
				</li>
				<li>
					${board.mem_nickname}<br>
					조회 ${board.board_hit}
				</li>
			</ul>
			<br>
			<h2>${board.board_title}</h2>
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
			<br>
			<ul class="detail-sub">
				<li>
					<%-- 좋아요 --%>
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
						<script type="text/javascript">
							let delete_btn = document.getElementById('delete_btn');
							//이벤트 연결
							delete_btn.onclick = function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									alert('게시글이 삭제되었습니다');
									location.replace('delete.do?board_num=${board.board_num}');
								}
							};
						</script>
					</c:if>
					<c:if test="${user_num == 3}">
						<input type="button" value="삭제" id="delete_btn">
						<script type="text/javascript">
							let delete_btn = document.getElementById('delete_btn');
							//이벤트 연결
							delete_btn.onclick = function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									alert('게시글이 삭제되었습니다');
									location.replace('delete.do?board_num=${board.board_num}');
								}
							};
						</script>
					</c:if>
				</li>
			</ul>
			<br>
			<!-- 댓글 시작 -->
			<div id="reply_div">
				<span class="re_title">댓글 달기</span>
				<form id="re_form">
					<input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
					<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content" <c:if test="${empty user_num}">disabled="disabled"</c:if>><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다</c:if></textarea>
					<c:if test="${!empty user_num}">
					<div id="re_first">
						<span class="letter-count">300/300</span>
					</div>
					<div class="re_second" class="align-right">
						<input type="submit" value="전송">
					</div>
					</c:if>
				</form>
			</div>
			<div id="output"></div>
			<div class="paging-button" style="display:none;">
				<input type="button" value="다음글 보기">
			</div>
			<div id="loading" style="display:none;">
				<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
			</div>
			<!-- 댓글 끝 -->
		</div>
	</div>
</body>
</html>