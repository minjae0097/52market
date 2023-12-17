<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
window.onload=function(){
	//지도 검색
	let openmap = document.getElementById('mapopen');
	openmap.onclick = function(){
		 let options = "toolbar=no,scrollbars=no,resizable=yes,status=no,menubar=no,width=1200, height=800, top=0,left=0";

		  window.open("${pageContext.request.contextPath}/map/insertMap.do","_blank", options);
	};
	//수정
	let myForm = document.getElementById('update_form');
	myForm.onsubmit=function(){
		let choice = confirm('수정하시겠습니까?');
		if(choice){
			let items = document.querySelectorAll('input[type="text"],input[type="number"],textarea');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					let label = document.querySelector('label[for="'+items[i].id+'"]');
					alert(label.textContent+' 항목을 입력하세요');
					items[i].value="";
					items[i].focus();
					return false;
				}
			}
		
			let radio_check = document.querySelectorAll('.radio-check');
			for(let i=0;i<radio_check.length;i++){
				let radios = document.querySelectorAll('input[name="'+radio_check[i].id+'"]');
				let radios_value = null;
				for(let j=0;j<radios.length;j++){
					if(radios[j].checked==true){
						radios_value = radios[j].value;
					}
				}
				if(radios_value==null){
					alert(radio_check[i].textContent+( ' 항목을 입력하세요'));
					radio_check[i].focus();
					return false;
				}
			}
		}else{
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
		<h2>거래글 수정</h2>
		<form id="update_form" action="productUpdate.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="product_num" value="${product.product_num}">
			<ul>
				<li>
					<label for="product_image">파일 첨부</label>
					<input type="file" name="product_image" id="product_image" class="input-check" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<h4 class="radio-check" id="product_category">카테고리</h4>
					<input type="radio" name="product_category" value="1">디지털기기
					<input type="radio" name="product_category" value="2">생활/주방
					<input type="radio" name="product_category" value="3">유아동
					<input type="radio" name="product_category" value="4">의류/잡화
					<input type="radio" name="product_category" value="5">티켓/교환권
				</li>
				<li>
					<label for="product_name">상품명</label>
					<input type="text" name="product_name" id="product_name" value="${detail.product_name}" maxlength="50">
				</li>
				<li>
					<label for="product_title">제목</label>
					<input type="text" name="product_title" id="product_title" value="${product.product_title}" maxlength="50">
				</li>
				<li>
					<label for="product_price">가격</label>
					<input type="number" name="product_price" id="product_price" value="${detail.product_price}" min="1" max="99999999">
				</li>
				<li>
					<label for="product_content">자세한 설명</label>
					<textarea name="product_content" id="product_content" rows="5" cols="30">${product.product_content}</textarea>
				</li>
				<li>
					<label for="location">거래희망장소</label>
					<input type="text" id="location" name="location" value="${map.location}">
					<input type="button" name="mapopen" id="mapopen"  value="장소 선택">
					<input type="hidden" name="location_x" id="location_x" value="${map.location_x}">
					<input type="hidden" name="location_y" id="location_y" value="${map.location_y}">
					<input type="hidden" name="road_address_name" id="road_address_name" value="${map.road_address_name}">
					<input type="hidden" name="address_name" id="address_name" value="${map.address_name}">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="취소" onclick="location.href='list.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>