<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSH.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('write_form');
	//지도 검색
	let openmap = document.getElementById('mapopen');
	openmap.onclick = function(){
		 let options = "toolbar=no,scrollbars=no,resizable=yes,status=no,menubar=no,width=1200, height=800, top=0,left=0";

		  window.open("${pageContext.request.contextPath}/map/insertMap.do","_blank", options);
	};
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<br>
		<h2>내 물건 팔기</h2>
		<br>
		<form action="productWrite.do" id="write_form" method="post" enctype="multipart/form-data">
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
				<input type="text" id="location" name="location" readonly>
				<input type="button" name="mapopen" id="mapopen"  value="장소 선택">
				<input type="hidden" name="location_x" id="location_x">
				<input type="hidden" name="location_y" id="location_y">
				<input type="hidden" name="road_address_name" id="road_address_name">
				<input type="hidden" name="address_name" id="address_name">
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