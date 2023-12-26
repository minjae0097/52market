<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//좋아요 선택 여부와 선택한 총 개수 읽기
	function selectFav(){
		$.ajax({
			url:'getCarFav.do',
			type:'post',
			data:{carlist_num:$('#output_fav').attr('data-num')},
			dataType:'json',
			success:function(param){
				displayFav(param);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	//좋아요 등록(및 삭제) 이벤트 연결
	$('#output_fav').click(function(){
		$.ajax({
			url:'writeCarFav.do',
			type:'post',
			data:{carlist_num:$(this).attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 좋아요를 눌러주세요');
				}else if(param.result == 'success'){
					displayFav(param);
				}else{
					alert('좋아요 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	//좋아요 표시
	function displayFav(param){
		let output;
		if(param.status=='yesFav'){//좋아요 선택
			output = '../images/fav02.gif';
		}else{//좋아요 미선택
			output = '../images/fav01.gif';
		}
		
		//문서 객체에 설정
		$('#output_fav').attr('src',output);
		$('#output_fcount').text(param.count);
	}
	//초기 데이터 호출
	<c:if test="${user_num==null||user_num!=detail.car_seller}">
		selectFav();
	</c:if>
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-detail">
		<img src="${pageContext.request.contextPath}/upload/${detail.car_image}" width="600px" style="margin-left: 75px;">
	<div class="content-detail">
		<c:if test="${!empty seller.mem_photo}">
		<img src="${pageContext.request.contextPath}/upload/${seller.mem_photo}" width="30px" height="30px" class="my-photo">
		</c:if>
		<c:if test="${empty seller.mem_photo}">
		<img src="${pageContext.request.contextPath}/images/face.png" width="30px" height="30px" class="my-photo">
		</c:if>
		<span>${seller.mem_nickname}</span>
		<c:if test="${user_num==detail.car_seller||user_auth==9}">
		<button onclick="location.href='updateCarForm.do?carlist_num=${list.carlist_num}'">수정</button>
		<button id="delete_btn">삭제</button>
		<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.href='deleteCar.do?carlist_num=${list.carlist_num}';
				}
			}
		</script>
		</c:if>
		
	<hr style="margin: 10px;" width="100%" size="1">
	</div>
	<div class="content-detail">
		<span>
		<c:if test="${list.carlist_status==0}"><span><b>판매중</b></span> . </c:if>
		<c:if test="${list.carlist_status==1}"><span><b>판매완료</b></span> . </c:if>
		${detail.car_title}<br>
		</span>
		<span style="font-size: 17pt;">
		<b>
		<c:if test="${detail.car_price%10000==0}"><fmt:formatNumber pattern="###,###,###,###,###,###" value="${detail.car_price/10000}"/>만원</c:if>
		<c:if test="${detail.car_price%10000!=0}"><fmt:formatNumber pattern="###,###,###,###,###,###" value="${detail.car_price/10000}"/>만원+</c:if> . 
		<c:if test="${detail.car_distance>=10000}">  <fmt:formatNumber value="${detail.car_distance/10000}" pattern="#.#" />만km</c:if>
		<c:if test="${detail.car_distance<10000 }"><fmt:formatNumber value="${detail.car_distance}"/>km</c:if>
		</b>
		</span>
		<br>
		<br>
		<span style="font-size: 11pt;color: gray; margin-top: 10px;">
		관심 ${favcount} . 조회수 ${list.carlist_hit}
		</span>
	</div>
	<div class="content-detail">
	<h1>정보</h1>
		<ul>
			<li>차종 : ${detail.car_type}</li>
			<li>연식 : ${detail.car_model_year}</li>
			<li>주행거리 : <fmt:formatNumber value="${detail.car_distance}"/>km</li>
			<li>연료 : ${detail.car_fuel}</li>
			<li>자동/수동 : ${detail.car_transmission}</li>
		</ul>
	<hr style="margin: 10px;" width="100%" size="1">
	<h1>소개</h1>
		<ul>
			<li>
			<span>${list.carlist_content}</span>
			</li>
		</ul>
	<hr style="margin: 10px;" width="100%" size="1">
	<h1>위치</h1>
	<jsp:include page="/map/showMap.jsp"/>
	<hr width="100%" size="1">
	</div>
		<div  class="content-detail">
			<c:if test="${user_num!=detail.car_seller}">
			<%-- 좋아요 시작 --%>
				<img id="output_fav" data-num="${list.carlist_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
				좋아요
				<span id="output_fcount"></span>
				
			<%-- 좋아요 끝 --%>
			<button class="chat-btn" onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForBuyerCar.do?carlist_num=${list.carlist_num}'">채팅하기</button>
			</c:if>
			<c:if test="${user_num==detail.car_seller}">
				<form action="updateStatus.do" id="status_form" method="post">
				<input type="hidden" name="carlist_num" value="${detail.carlist_num}">
				<select name="carlist_status" onchange="this.form.submit()">
					<option value="0" <c:if test="${list.carlist_status==0}">selected</c:if>>판매중</option>
					<option value="1" <c:if test="${list.carlist_status==1}">selected</c:if>>판매완료</option>
				</select>
				</form>
			</c:if>
		</div>
	</div>
</div>
 <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>