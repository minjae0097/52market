<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
<script type="text/javascript">
window.onload=function(){
	/* let myForm = document.getElementById('search_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		let keyword = document.getElementById('keyword');
		if(keyword.value.trim()==''){
			alert('검색어를 입력하세요!');
			keyword.value = '';
			keyword.focus();
			return false;
		}
	}; */
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
	function base() {
		/* let keyword = document.getElementById('keyword');
		if(${param.keyword}!=null) keyword.value=${param.keyword} */
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
	<img src="${pageContext.request.contextPath}/images/house_title.png" width="960">
	<div class="content-main">
		<h4>부동산 직거래 게시물</h4>
		<form id="search_form" action="list.do" method="get">		
			<ul>
				<li>
					<ul class="search">		
						<li>
							<input type="button" value="필터" id = "show_filter">
							<select name="keyfield">
								<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
								<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
								<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
							</select>
						</li>
						<li>
							<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
						</li>
						<li>
							<input type="submit" value="검색"><br>
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
						<label>판매자 타입</label>
						<input type="radio" name="house_seller_type" value="1">세입자
						<input type="radio" name="house_seller_type" value="2">집주인
					</li>
					<li>
						<label>매물종류</label>
						<input type="radio" name="house_type" value="1">원룸
						<input type="radio" name="house_type" value="2">빌라(투룸이상)
						<input type="radio" name="house_type" value="3">아파트
						<input type="radio" name="house_type" value="4">오피스텔
						<input type="radio" name="house_type" value="5">상가
						<input type="radio" name="house_type" value="6">기타(사무실,주택,토지 등)
					</li>
					<li>
						<label>거래방식</label>
						<input type="radio" name="house_deal_type" value="1">전세
						<input type="radio" name="house_deal_type" value="2">매매
						<input type="radio" name="house_deal_type" value="3">월세
						<input type="radio" name="house_deal_type" value="4">단기
					</li>
					<li>
						<label>입주시기</label>
						<input type="radio" name="house_move_in" value="1">즉시입주가능
						<input type="radio" name="house_move_in" value="2">확인필요
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
			<div class="content-main">
				<div class="image-space">
					<c:forEach var="house" items="${houseList}">
						<div class="horizontal-area">
							<a href="${pageContext.request.contextPath}/house/detailHouse.do?house_num=${house.house_num}">
								<img src="${pageContext.request.contextPath}/upload/${house.house_photo1}">
								<span>${house.house_title}</span>
								<br>
								${house.mem_nickname}<b><fmt:formatNumber value="${house.house_price}"/>원</b><br>
								
							</a>
						</div>
					</c:forEach>
					<div class="float-clear">
						<hr width="100%" size="1" noshade="noshade">
					</div>
				</div>
			</div>
			</c:if>
			<div class="align-center">${page}</div>
			<div class="align-right">
			<input type="button" value="글쓰기" onclick="location.href='insertHouseForm.do'"
					<c:if test="${empty user_num}">disabled="disabled"</c:if>
				><!-- 로그인이 되어있지 않은면 비활성화 됨(disabled="disabled") -->
			</div>
		
	</div>
</div>
</body>
</html>