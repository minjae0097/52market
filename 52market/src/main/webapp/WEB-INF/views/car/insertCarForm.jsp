<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>차량 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style type="text/css">
.li-move input{
	margin-left: 10px;
	margin-top: 7px;
}
h3 label{
	margin-bottom: 10px;
}
h3{
	margin-bottom: 10px;
}
input[type="text"], textarea{
    width: 80%;
    border: 1px solid #C4C4C4;
    box-sizing: border-box;
    border-radius: 10px;
    padding: 8px 13px;
    font-style: normal;
    font-weight: 400;
    font-size: 14px;
    line-height: 16px;
}
input[type="number"]{
	width: 20%;
    border: 1px solid #C4C4C4;
    box-sizing: border-box;
    border-radius: 10px;
    padding: 6px 10px;
    font-size: 12px;
}
form{
	border-color: #C4C4C4;
}
</style>
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('insertCarForm');
	//지도 검색
	let openmap = document.getElementById('mapopen');
	openmap.onclick = function(){
		 let options = "toolbar=no,scrollbars=no,resizable=yes,status=no,menubar=no,width=1200, height=800, top=0,left=0";

		  window.open("${pageContext.request.contextPath}/map/insertMap.do","_blank", options);
	};
	//입력폼 확인
	myForm.onsubmit = function(){
		let items = document.querySelectorAll('input[type="text"],input[type="number"],textarea,input[type="file"]');
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
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>차량등록</h2>
		<form action="insertCar.do" id="insertCarForm" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<h3><label for="car_title">모델명</label></h3>
					<input type="text" id="car_title" name="car_title" maxlength="50">
				</li>
				<li class="li-move">
					<h3 class="radio-check" id="car_type">차종</h3>
					<input type="radio" name="car_type" value="경차/소형차" >경차/소형차
					<input type="radio" name="car_type" value="준/중형차" >준/중형차
					<input type="radio" name="car_type" value="준/대형차" >준/대형차
					<input type="radio" name="car_type" value="SUV/RV" >SUV/RV
					<input type="radio" name="car_type" value="승합/화물차" >승합/화물차
					<input type="radio" name="car_type" value="그외" >그외
				</li>
				<li class="li-move">
					<h3 class="radio-check" id="car_fuel">연료</h3>
					<input type="radio" name="car_fuel" value="가솔린(휘발유)" >가솔린(휘발유)
					<input type="radio" name="car_fuel" value="디젤(경유)" >디젤(경유)
					<input type="radio" name="car_fuel" value="LPG" >LPG
					<input type="radio" name="car_fuel" value="CNG(천연가스)" >CNG(천연가스)
					<input type="radio" name="car_fuel" value="전기" >전기
					<input type="radio" name="car_fuel" value="수소전기" >수소전기<p></p>
					<input type="radio" name="car_fuel" value="태양광" >태양광
					<input type="radio" name="car_fuel" value="하이브리드" >하이브리드
				</li>
				<li>
					<h3><label for="car_price">가격</label></h3>
					<input type="number" id="car_price" name="car_price" min="1" max="999999999999"> 원
				</li>
				<li>
					<h3><label for="car_model_year">연식</label></h3>
					<input type="number" id="car_model_year" name="car_model_year" min="1000" max="9999"> 년 <span style="font-size: 10px;color: gray">ex)2023년</span>
				</li>
				<li>
					<h3><label for="car_distance">주행거리</label></h3>
					<input type="number" id="car_distance" name="car_distance" min="0" max="9999999" > km
				</li>
				<li>
					<h3 class="radio-check" id="car_transmission"><label>변속기</label></h3>
					<input type="radio" name="car_transmission" value="자동(A/T)" >자동(A/T)
					<input type="radio" name="car_transmission" value="수동" >수동
				</li>
				<li>
					<h3 class="radio-check" id="car_origin"><label>국산차/수입차</label></h3>
					<input type="radio" name="car_origin" value="국산" >국산
					<input type="radio" name="car_origin" value="수입" >수입
				</li>
				<li>
					<h3><label for="carlist_content">소개</label></h3>
					<textarea rows="4" cols="40" placeholder="내용을 입력해주세요" id="carlist_content" name="carlist_content"></textarea>
				</li>
				<li>
					<h3><label for="car_image">이미지</label></h3>
					<input type="file" name="car_image" id="car_image" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<h3><label for="location">거래장소</label></h3>
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
				<input type="button" value="목록" onclick="location.href='list.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>