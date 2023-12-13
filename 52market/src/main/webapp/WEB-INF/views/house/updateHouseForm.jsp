<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정폼</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('updateHouseForm');
	
	//입력폼 확인
	myForm.onsubmit = function(){
		let choice = confirm('등록하시겠습니까?');
		if(choice){
			
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
		}else{
			return false;
		}
	};
	function base() {
		let house_seller_type = document.querySelectorAll('input[name="house_seller_type"]');
		house_seller_type[${detail.house_seller_type}-1].checked = true;
		let house_type = document.querySelectorAll('input[name="house_type"]');
		house_type[${detail.house_type}-1].checked = true;
		let house_deal_type = document.querySelectorAll('input[name="house_deal_type"]');
		house_deal_type[${detail.house_deal_type}-1].checked = true;
		let house_move_in = document.querySelectorAll('input[name="house_move_in"]');
		house_move_in[${detail.house_move_in}-1].checked = true;
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
		<form action="updateHouse.do" id="updateHouseForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="house_num" value="${detail.house_num}">
			<ul>
				<li>
					<label for="house_title">제목</label>
					<input type="text" id="house_title" name="house_title" maxlength="150" value="${detail.house_title}">
				</li>
				<li>
					<h3 class="radio-check" id="house_seller_type">작성자</h3>
					<input type="radio" name="house_seller_type" value="1">세입자
					<input type="radio" name="house_seller_type" value="2">집주인
				</li>
				<li>
					<h3 class="radio-check" id="house_type">매물종류</h3>
					<input type="radio" name="house_type" value="1">원룸
					<input type="radio" name="house_type" value="2">빌라(투룸이상)
					<input type="radio" name="house_type" value="3">아파트
					<input type="radio" name="house_type" value="4">오피스텔
					<input type="radio" name="house_type" value="5">상가
					<input type="radio" name="house_type" value="6">기타(사무실,주택,토지 등)
				</li>
				<li>
					<h3><label for="zipcode">우편번호</label></h3>
					<input type="text" name="zipcode" id="zipcode" maxlength="5" value="${detail.zipcode}">
					<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기">
				</li>
				<li>
					<h3><label for="address1">주소</label></h3>
					<input type="text" name="house_address1" id="address1" maxlength="30" value="${detail.house_address1}">
				</li>
				<li>
					<h3><label for="address2">상세주소</label></h3>
					<input type="text" name="house_address2" id="address2" maxlength="30" value="${detail.house_address2}">
				</li>
				<li>
					<h3 class="radio-check" id="house_deal_type"><label>거래방식</label></h3>
					<input type="radio" name="house_deal_type" value="1">전세
					<input type="radio" name="house_deal_type" value="2">매매
					<input type="radio" name="house_deal_type" value="3">월세
					<input type="radio" name="house_deal_type" value="4">단기(1년미만)
				</li>
				<li>
					<h3><label for="house_price">금액</label></h3>
					<input type="number" name="house_price" id="house_price" min="1" max="9999999999" value="${detail.house_price}">원
				</li>
				<li>
					<h3><label for="house_diposit">보증금</label></h3>
					<input type="number" name="house_diposit" id="house_diposit" min="1" max="99999999" value="${detail.house_diposit}">원
				</li>
				<li>
					<h3><label for="house_cost">관리비</label></h3>
					<input type="number" name="house_cost" id="house_cost" min="1" max="99999999" value="${detail.house_cost}">원
				</li>
				<li>
					<h3><label for="house_space">평수</label></h3>
					<input type="number" name="house_space" id="house_space" min="1" max="999" value="${detail.house_space}">평
				</li>
				<li>
					<h3><label for="house_floor">층수</label></h3>
					<input type="number" name="house_floor" id="house_floor" min="1" max="999" value="${detail.house_floor}">층
				</li>
				<li>
					<h3 class="radio-check" id="house_move_in">입주시기</h3>
					<input type="radio" name="house_move_in" value="1">즉시입주가능
					<input type="radio" name="house_move_in" value="2">확인필요
				</li>
				<li>
					<h3><label for="house_content">내용</label></h3>
					<textarea rows="5" cols="50" placeholder="내용을 입력해주세요" name="house_content" id="house_content">${list.house_content}</textarea>
				</li>
				<li>
					<h3><label for="house_photo1">썸네일사진</label></h3>
					<input type="file" name="house_photo1" id="house_photo1" 
							class="input-check" accept="image/gif,image/png,image/jpeg" value="${detail.house_photo1}">
				</li>
				<li>
					<h3><label for="house_photo2">이미지</label></h3>
					<input type="file" name="house_photo2" id="house_photo2" 
							class="input-check" accept="image/gif,image/png,image/jpeg" value="${detail.house_photo2}">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="등록" >
				
				<input type="button" value="목록" onclick="location.href='list.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>