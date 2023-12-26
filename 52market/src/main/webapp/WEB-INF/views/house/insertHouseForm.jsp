<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부동산 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSY.css">
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById("insertHouseForm");
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
		<h2>부동산 글쓰기</h2>
		<form action="insertHouse.do" id="insertHouseForm" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<h3><label for="house_title">제목</label></h3>
					<input type="text" id="house_title" name="house_title" maxlength="150">
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
					<input type="text" name="zipcode" id="zipcode" maxlength="5">
					<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기">
				</li>
				<li>
					<h3><label for="address1">주소</label></h3>
					<input type="text" name="house_address1" id="address1" maxlength="30" class="input-check">
				</li>
				<li>
					<h3><label for="address2">상세주소</label></h3>
					<input type="text" name="house_address2" id="address2" maxlength="30" class="input-check">
				</li>
				<li>
					<h3 class="radio-check" id="house_deal_type">거래방식</h3>
					<input type="radio" name="house_deal_type" value="1">전세
					<input type="radio" name="house_deal_type" value="2">매매
					<input type="radio" name="house_deal_type" value="3">월세
					<input type="radio" name="house_deal_type" value="4">단기(1년미만)
				</li>
				<li>
					<h3><label for="house_price">금액</label></h3>
					<input type="number" name="house_price" id="house_price" min="1" max="999999999999">원
				</li>
				<li>
					<h3><label for="house_diposit">보증금</label></h3>
					<input type="number" name="house_diposit" id="house_diposit" min="0" max="999999999999">원
				</li>
				<li>
					<h3><label for="house_cost">관리비</label></h3>
					<input type="number" name="house_cost" id="house_cost" min="0" max="99999999">원
				</li>
				<li>
					<h3><label for="house_space">평수</label></h3>
					<input type="number" name="house_space" id="house_space" min="1" max="999">평
				</li>
				<li>
					<h3><label for="house_floor">층수</label></h3>
					<input type="number" name="house_floor" id="house_floor" min="1" max="999">층
				</li>
				<li>
					<h3 class="radio-check" id="house_move_in">입주시기</h3>
					<input type="radio" name="house_move_in" value="1">즉시입주가능
					<input type="radio" name="house_move_in" value="2">확인필요
				</li>
				<li>
					<h3><label for="house_content">내용</label></h3>
					<textarea rows="5" cols="50" placeholder="내용을 입력해주세요" name="house_content" id="house_content"></textarea>
				</li>
				<li>
					<h3><label for="house_photo1">썸네일사진</label></h3>
					<input type="file" name="house_photo1" id="house_photo1" 
							class="input-check" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<h3><label for="house_photo2">이미지</label></h3>
					<input type="file" name="house_photo2" id="house_photo2" 
							class="input-check" accept="image/gif,image/png,image/jpeg">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="등록">
				<input type="button" value="목록" onclick="location.href='list.do'">
			</div>
		</form>
	</div>
</div>
<!-- 우편번호 검색 시작 -->
	<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
    }

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    //(주의)address1에 참고항목이 보여지도록 수정
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //(수정) document.getElementById("address2").value = extraAddr;
                
                } 
                //(수정) else {
                //(수정)    document.getElementById("address2").value = '';
                //(수정) }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode;
                //(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
                document.getElementById("address1").value = addr + extraAddr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address2").focus();

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 400; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 5; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>
<!-- 우편번호 검색 끝 -->
</body>
</html>