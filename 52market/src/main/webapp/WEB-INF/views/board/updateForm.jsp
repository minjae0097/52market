<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네생활 게시판 글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/PIH.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#update_form').submit(function(){
			if($('#title').val.trim()==''){
				alert('제목을 입력하세요');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요');
				$('#content').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
				<input type="hidden" name="board_num" value="${board.board_num}">
				<ul>
					<li>
						<label for="board_title">제목</label>
						<input type="text" name="board_title" id="board_title" value="${board.board_title}" maxlength="50">
					</li>
					<li>
						<label for="board_content">내용</label>
						<textarea rows="5" cols="30" name="board_content" id="board_content">${board.board_content}</textarea>
					</li>
					<li>
						<label for="board_filename">파일</label>
						<input type="file" name="board_filename" id="board_filename" accept="image/gif,image/png,image/jpeg">
						<c:if test="${!empty board.board_filename}">
							<div id="board_file_detail">
								(${board.board_filename})파일이 등록되어 있습니다.
								<input type="button" value="파일삭제" id="board_file_del">
								<script type="text/javascript">
									$(function(){
										$('#board_file_del').click(function(){
											let choice = confirm('삭제하시겠습니까?');
											if(choice){
												$.ajax({
													url: 'deleteFile.do',
													type: 'post',
													data: {board_num:${board.board_num}},
													dataType:'json',
													success:function(param){
														if(param.result == 'logout'){
															alert('로그인 후 사용하세요');
														}else if(param.result == 'success'){
															$('#board_file_detail').hide();
														}else if(param.result =='wrongAccess'){
															alert('잘못된 접속입니다');
														}else{
															alert('파일 삭제 오류 발생');
														}
													},
													error:function(){
														alert('네트워트 오류 발생');
													}
												});
											}
										});
									});
								</script>
							</div>
						</c:if>
					</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="수정">
					<input type="button" value="목록" onclick="location.href='list.do'">
				</div>
			</form>
		</div>
	</div>
</body>
</html>