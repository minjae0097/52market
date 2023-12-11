<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>동네생활 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<h2>동네생활 글쓰기</h2>
			<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
				<ul>
					<li>
						<label for="board_title">제목</label>
						<input type="text" name="board_title" id="board_title" maxlength="50">
					</li>
					<li>
						<label for="board_content">내용</label>
						<textarea rows="5" cols="30" name="board_content" id="board_content"></textarea>
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