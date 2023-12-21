<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네생활 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
	window.onload = function(){
		let myForm = document.getElementById('write_form');
		//이벤트 연결
		myForm.onsubmit = function(){
			let title = document.getElementById('board_title');
			if(board_title.value.trim()==''){
				alert('제목을 입력하세요');
				board_title.value='';
				board_title.focus();
				return false;
			}
			let content = document.getElementById('board_content');
			if(board_content.value.trim()==''){
				alert('내용을 입력하세요');
				board_content.value='';
				board_content.focus();
				return false;
			}
		};
	};
</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<br>
			<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
				<ul>
					<li>
						<label for="board_category">카테고리</label>
						<select name="board_category" id="board_category">
							<option value="1">동네생활</option>
							<option value="2">동네백과</option> 
							<option value="3">동네맛집</option> 
							<option value="4">동네소식</option> 
						</select>
					</li>
					<li>
						<label for="board_title">제목</label>
						<input type="text" name="board_title" id="board_title" maxlength="50">
					</li>
					<li>
						<label for="board_content">내용</label>
						<textarea rows="8" cols="45" name="board_content" id="board_content" wrap="hard" placeholder="  동네에 관련된 질문이나 이야기를 해보세요"></textarea>
					</li>
					<li>
						<label for="board_filename">파일</label>
						<input type="file" name="board_filename" id="board_filename" accept="image/gif,image/png,image/jpeg">
					</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="등록">
					<input type="button" value="목록" onclick="location.href='list.do'">
				</div>
			</form>
		</div>
	</div>
</body>
</html>