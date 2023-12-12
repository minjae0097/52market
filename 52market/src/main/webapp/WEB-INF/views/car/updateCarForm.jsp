<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('updateCarForm');
	//지도 검색
	let openmap = document.getElementById('mapopen');
	openmap.onclick = function(){
		 let options = "toolbar=no,scrollbars=no,resizable=yes,status=no,menubar=no,width=1200, height=800, top=0,left=0";

		  window.open("${pageContext.request.contextPath}/map/insertMap.do","_blank", options);
	};
	//입력폼 확인
	myForm.onsubmit = function(){
		let items = document.querySelectorAll('input[type="text"],input[type="number"],textarea');
		for(let i=0;i<items.length;i++){
			if(items[i].value.trim()==''){
				let label = document.querySelector('label[for="'+items[i].id+'"]');
				alert(label.textContent+'항목을 입력하세요');
				items[i].value="";
				items[i].focus();
				return false;
			}
		}
		//라디오박스 체크
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
		
	};
	function base() {
		let type = document.querySelectorAll('input[name="car_type"]');
		type[${detail.car_type}-1].checked = true;
		let fuel = document.querySelectorAll('input[name="car_fuel"]');
		fuel[${detail.car_fuel}-1].checked = true;
		let transmission = document.querySelectorAll('input[name="car_transmission"]');
		transmission[${detail.car_transmission}-1].checked = true;
		let origin = document.querySelectorAll('input[name="car_origin"]');
		origin[${detail.car_origin}-1].checked = true;
	}
	base();
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>수정</h2>
		<form action="updateCar.do" id="updateCarForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="carlist_num" value="${list.carlist_num}">
			<ul>
				<li>
					<label for="car_title">모델명</label>
					<input type="text" id="car_title" name="car_title" maxlength="50" value="${detail.car_title}">
				</li>
				<li>
					<h3 class="radio-check" id="car_type">차종</h3>
					<input type="radio" name="car_type" value="1" >경차/소형차
					<input type="radio" name="car_type" value="2" >준/중형차
					<input type="radio" name="car_type" value="3" >준/대형차
					<input type="radio" name="car_type" value="4" >SUV/RV
					<input type="radio" name="car_type" value="5" >승합/화물차
					<input type="radio" name="car_type" value="6" >그외
				</li>
				<li>
					<h3 class="radio-check" id="car_fuel">연료</h3>
					<input type="radio" name="car_fuel" value="1" >가솔린(휘발유)
					<input type="radio" name="car_fuel" value="2" >디젤(경유)
					<input type="radio" name="car_fuel" value="3" >LPG
					<input type="radio" name="car_fuel" value="4" >CNG(천연가스)
					<input type="radio" name="car_fuel" value="5" >전기
					<input type="radio" name="car_fuel" value="6" >수소전기
					<input type="radio" name="car_fuel" value="7" >태양광
					<input type="radio" name="car_fuel" value="8" >하이브리드
				</li>
				<li>
					<h3><label for="car_price">가격</label></h3>
					<input type="number" id="car_price" name="car_price" min="1" max="999999999999" value="${detail.car_price}">원
				</li>
				<li>
					<h3><label for="car_model_year">연식</label></h3>
					<input type="number" id="car_model_year" name="car_model_year" min="1000" max="9999" value="${detail.car_model_year}">년
				</li>
				<li>
					<h3><label for="car_distance">주행거리</label></h3>
					<input type="number" id="car_distance" name="car_distance" min="0" max="9999999" value="${detail.car_distance}">km
				</li>
				<li>
					<h3 class="radio-check" id="car_transmission"><label>변속기</label></h3>
					<input type="radio" name="car_transmission" value="1" >자동(A/T)
					<input type="radio" name="car_transmission" value="2" >수동
				</li>
				<li>
					<h3 class="radio-check" id="car_origin"><label>국산차/수입차</label></h3>
					<input type="radio" name="car_origin" value="1" >국산
					<input type="radio" name="car_origin" value="2" >수입
				</li>
				<li>
					<h3>소개</h3>
					<label for="carlist_content">내용</label>
					<textarea rows="4" cols="40" placeholder="내용을 입력해주세요" id="carlist_content" name="carlist_content">${list.carlist_content}</textarea>
				</li>
				<li>
					<label for="car_image">이미지</label>
					<input type="file" name="car_image" id="car_image" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<h3>거래장소</h3>
					<label for="location">위치</label>
					<input type="text" id="location" name="location" value="${map.location}">
					<input type="button" name="mapopen" id="mapopen"  value="장소 선택">
					<input type="hidden" name="location_x" id="location_x" value="${map.location_x}">
					<input type="hidden" name="location_y" id="location_y" value="${map.location_y}">
					<input type="hidden" name="road_address_name" id="road_address_name" value="${map.road_address_name}">
					<input type="hidden" name="address_name" id="address_name" value="${map.address_name}">
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