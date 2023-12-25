<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고차 직거래</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
<script type="text/javascript">
window.onload=function(){
	let show_filter = document.getElementById('show_filter');
	let filter = document.getElementById('filter');
	//필터 보이게
	show_filter.onclick=function(){
		if(filter.style.display == 'block'){
			filter.style.display = 'none';
		}else{
			filter.style.display = 'block'
		}
	};
	//필터 안보이게
	let save_btn = document.getElementById('save_btn');
	save_btn.onclick=function(){
		 filter.style.display = 'none';
	};
	//판매중만 보이게
	let carlist_status = document.getElementById('carlist_status');
	carlist_status.onclick = function(){
		let myform = document.getElementById('search_form');
		myform.submit();
	}
	function base() {
		let carlist_status = document.getElementById('carlist_status');
		if(${carlist_status}==0) carlist_status.checked=true;
		let type = document.querySelector('input[value="${car_type}"]');
		if(type!=null) type.checked = true;
		let fuel = document.querySelector('input[value="${car_fuel}"]');
		if(fuel!=null) fuel.checked = true;
		let transmission = document.querySelector('input[value="${car_transmission}"]');
		if(transmission!=null) transmission.checked = true;
		let origin = document.querySelector('input[value="${car_origin}"]');
		if(origin!=null) origin.checked = true;
	}
	base();
	
	//필터 리셋
	let reset_btn = document.getElementById('reset_btn');
	reset_btn.onclick=function(){
		let type = document.querySelector('input[name="car_type"]:checked');
		if(type!=null) type.checked = false;
		let fuel = document.querySelector('input[name="car_fuel"]:checked');
		if(fuel!=null) fuel.checked = false;
		let transmission = document.querySelector('input[name="car_transmission"]:checked');
		if(transmission!=null) transmission.checked = false;
		let origin = document.querySelector('input[name="car_origin"]:checked');
		if(origin!=null) origin.checked = false;
	};
	
	
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<img src="${pageContext.request.contextPath}/images/중고차배너.png" width="900">
	<div class="content-main">
		<h2>등록 매물</h2>
			<form id="search_form" action="list.do" method="get">
			<ul>
			<li>
			<ul class="search">
				<li>
				<input type="button" value="필터"  id = "show_filter">
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" 
					                              value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
				
			</ul>
			</li>
			<li style="position:relative;left:250px;">
				<input type="checkbox" name="carlist_status" id="carlist_status" value="0"><span>판매중인 것만 보기</span>
			</li>
			</ul>	
			<div class="filter" id="filter" style="display:none;">
				<ul>
					<li>
						<label>차종</label>
						<input type="radio" name="car_type" value="경차/소형차">경차/소형차
						<input type="radio" name="car_type" value="준/중형차" >준/중형차
						<input type="radio" name="car_type" value="준/대형차" >준/대형차
						<input type="radio" name="car_type" value="SUV/RV" >SUV/RV
						<input type="radio" name="car_type" value="승합/화물차" >승합/화물차
						<input type="radio" name="car_type" value="그외" >그외
					</li>
					<li>
						<label>연료</label>
						<input type="radio" name="car_fuel" value="가솔린(휘발유)" >가솔린(휘발유)
						<input type="radio" name="car_fuel" value="디젤(경유)" >디젤(경유)
						<input type="radio" name="car_fuel" value="LPG" >LPG
						<input type="radio" name="car_fuel" value="CNG(천연가스)" >CNG(천연가스)
						<input type="radio" name="car_fuel" value="전기" >전기
						<input type="radio" name="car_fuel" value="수소전기" >수소전기
						<input type="radio" name="car_fuel" value="태양광" >태양광
						<input type="radio" name="car_fuel" value="하이브리드" >하이브리드
					</li>
					<li>
						<label>변속기</label>
						<input type="radio" name="car_transmission" value="자동(A/T)" >자동(A/T)
						<input type="radio" name="car_transmission" value="수동" >수동
					</li>
					<li>
						<label>국산차/수입차</label>
						<input type="radio" name="car_origin" value="국산" >국산
						<input type="radio" name="car_origin" value="수입" >수입
					</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="확인" id="save_btn">
					<input type="button" value="초기화" id="reset_btn">
				</div>
				</div>
		</form>	
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count>0}">
		<div class="image-space">
			<c:forEach var="car" items="${carList}">
				<div class="horizontal-area24">
					<div class="horizontal-area">
					<a href="${pageContext.request.contextPath}/car/detailCar.do?carlist_num=${car.carlist_num}">
						<img src="${pageContext.request.contextPath}/upload/${car.car_image}">
					</a>
					</div>
					<div class="list-horizontal">
					<a href="${pageContext.request.contextPath}/car/detailCar.do?carlist_num=${car.carlist_num}">
						<span style="font-size: 14pt;">${car.car_title}</span><br>
						<c:if test="${car.car_distance>=10000}">  <fmt:formatNumber value="${car.car_distance/10000}" pattern="#.#" />만km . </c:if>
						<c:if test="${car.car_distance<10000 }"><fmt:formatNumber value="${car.car_distance}"/>km . </c:if>
						${car.car_model_year}년식
						<br>
						<b>
						<c:if test="${car.car_price%10000==0}"><fmt:formatNumber pattern="###,###,###,###,###,###" value="${car.car_price/10000}"/>만원</c:if>
						<c:if test="${car.car_price%10000!=0}"><fmt:formatNumber pattern="###,###,###,###,###,###" value="${car.car_price/10000}"/>만원+</c:if>
						</b><br>
						관심 ${car.favcount} 조회수 ${car.carlist_hit}<br>
						<c:if test="${car.carlist_status==1}"><span style="background-color: gray;color: white; border-radius: 10px; padding:2px;"><b>판매완료</b></span></c:if>
					</a>
					</div>
				</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
			<div class="align-center">${page}</div>
		</div>
		</c:if>
	</div>
	<div class="align-right">
		<input type="button" value="글쓰기" onclick="location.href='insertCarForm.do'">
	</div>
</div>
</body>
</html>