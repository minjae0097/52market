<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">


</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>내 물건 팔기</h2>
		<form action="productWrite.do" method="post" enctype="multipart/form-data" id="write_form">
		<ul>
			<li>
				<label for="product_image">파일 첨부</label>
				<input type="file" name="product_image" id="product_image" class="input-check" accept="image/gif,image/png,image/jpeg">
			</li>
			<li>
				<label for="product_category">카테고리</label>
				<input type="radio" name="product_category" value="1" id="category1">디지털기기
				<input type="radio" name="product_category" value="2" id="category2">생활/주방
				<input type="radio" name="product_category" value="3" id="category3">유아동
				<input type="radio" name="product_category" value="4" id="category4">의류/잡화
				<input type="radio" name="product_category" value="5" id="category5">티켓/교환권
			</li>
			<li>
				<label for="product_name">상품명</label>
				<input type="text" name="product_name" id="product_name" class="input-check" maxlength="50">
			</li>
			<li>
				<label for="product_title">제목</label>
				<input type="text" name="product_title" id="product_title" class="input-check" maxlength="50">
			</li>
			<li>
				<label for="product_price">가격</label>
				<input type="number" name="product_price" id="product_price" class="input-check" min="1" max="99999999">
			</li>
			<li>
				<label for="product_content">자세한 설명</label>
				<textarea name="product_content" id="product_content" class="input-check" rows="5" cols="30"></textarea>
			</li>
			<li>
				<label for="location">거래희망장소</label>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="취소" onclick="location.href='list.do'">
		</div>
		</form>
	</div>
</div>
</body>
</html>