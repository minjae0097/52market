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
<!-- <script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('search_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		let keyword = document.getElementById('keyword');
		if(keyword.value.trim()==''){
			alert('검색어를 입력하세요!');
			keyword.value = '';
			keyword.focus();
			return false;
		}
	};
};
</script> -->
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>등록 매물</h2>
		<button>필터</button>
			<form id="search_form" action="list.do" method="get">
				<div class="filter">
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
				</div>
		
			<ul class="search">
				<li>
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
					<a href="${pageContext.request.contextPath}/car/detailCar.do?carlist_num=${car.carlist_num}">
						<img src="${pageContext.request.contextPath}/upload/${car.car_image}">
						<span>${car.car_title}</span>
						<b><fmt:formatNumber value="${car.car_price}"/>원</b>
					</a>
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