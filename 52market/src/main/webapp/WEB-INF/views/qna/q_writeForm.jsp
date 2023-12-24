<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSH.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>문의 게시판</h2>
		<form id="q_writeForm" action="qWrite.do" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<label for="qna_title">제목</label>
					<input type="text" name="qna_title" id="qna_title" maxlength="50">
				</li>
				<li>
					<label for="question_content">글 내용</label>
					<textarea rows="5" cols="30" name="question_content" id="question_content"></textarea>
				</li>
				<li>
					<label for="question_filename">파일 첨부</label>
					<input type="file" name="question_filename" id="question_filename" accept="image/gif,image/png,image/jpeg">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="작성">
				<input type="button" value="취소" onclick="location.href='list.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>




