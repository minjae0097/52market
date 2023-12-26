<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부동산 직거래</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSY.css">
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
	
	//판매중만 보이게
	let house_status = document.getElementById('save_btn');
	house_status.onclick=function(){
		let myform = documenet.getElementById('search_form');
		myform.submit();
	}
	/*
	function base() {
		let house_status = document.getElementById('house_status');
		if(${house_status} == 0) house_status.checked=true;
		let house_seller_type = document.querySelector('input[value="${house_seller_type}"]');
		if(house_seller_type!=null) house_seller_type.checked = true;
		let house_type = document.querySelector('input[value="${house_type}"]');
		if(house_type!=null) house_type.checked = true;
		let house_deal_type = document.querySelector('input[value="${house_deal_type}"]');
		if(house_deal_type!=null) house_deal_type.checked = true;
		let house_move_in = document.querySelector('input[value="${house_move_in}"]');
		if(house_move_in!=null) house_move_in.checked = true;
	}
	base();
	*/
	//필터 리셋
	let reset_btn = document.getElementById('reset_btn');
	reset_btn.onclick=function(){
		let house_seller_type = document.querySelector('input[name="house_seller_type"]:checked');
		if(house_seller_type!=null) house_seller_type.checked = false;
		let house_type = document.querySelector('input[name="house_type"]:checked');
		if(house_type!=null) house_type.checked = false;
		let house_deal_type = document.querySelector('input[name="house_deal_type"]:checked');
		if(house_deal_type!=null) house_deal_type.checked = false;
		let house_move_in = document.querySelector('input[name="house_move_in"]:checked');
		if(house_move_in!=null) house_move_in.checked = false;
	};
	
	//필터
	<c:if test="${!empty param.house_seller_type}">
		let house_seller_type = document.getElementsByName('house_seller_type');
		<c:if test="${param.house_seller_type == 1}">
			house_seller_type[0].checked = true;
		</c:if>
		<c:if test="${param.house_seller_type == 2}">
			house_seller_type[1].checked = true;
		</c:if>
	</c:if>
	
	<c:if test="${!empty param.house_type}">
		let house_type = document.getElementsByName('house_type');
		<c:if test="${param.house_type == 1}">
			house_type[0].checked = true;
		</c:if>
		<c:if test="${param.house_type == 2}">
			house_type[1].checked = true;
		</c:if>
		<c:if test="${param.house_type == 3}">
			house_type[2].checked = true;
		</c:if>
		<c:if test="${param.house_type == 4}">
			house_type[3].checked = true;
		</c:if>
		<c:if test="${param.house_type == 5}">
			house_type[4].checked = true;
		</c:if>
		<c:if test="${param.house_type == 6}">
			house_type[5].checked = true;
		</c:if>
	</c:if>
	
	<c:if test="${!empty param.house_deal_type}">
		let house_deal_type = document.getElementsByName('house_deal_type');
		<c:if test="${param.house_deal_type == 1}">
			house_deal_type[0].checked = true;
		</c:if>
		<c:if test="${param.house_deal_type == 2}">
			house_deal_type[1].checked = true;
		</c:if>
		<c:if test="${param.house_deal_type == 3}">
			house_deal_type[2].checked = true;
		</c:if>
		<c:if test="${param.house_deal_type == 4}">
			house_deal_type[3].checked = true;
		</c:if>
	</c:if>
	
	<c:if test="${!empty param.house_move_in}">
		let house_move_in = document.getElementsByName('house_move_in');
		<c:if test="${param.house_move_in == 1}">
			house_move_in[0].checked = true;
		</c:if>
		<c:if test="${param.house_move_in == 2}">
			house_move_in[1].checked = true;
		</c:if>
	</c:if>
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<img src="${pageContext.request.contextPath}/images/부동산배너.png" width="900">
	<div class="content-main">
		<h2>부동산 등록 매물</h2>
		<form id="search_form" action="list.do" method="get">		
			<ul>
				<li>
					<ul class="search">		
						<li>
							<input type="button" value="필터" id="show_filter">
							<select name="keyfield" id="keyfield">
								<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
								<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
								<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
							</select>
						</li>
						<li>
							<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" placeholder="제목을 입력하세요">
						</li>
						<li>
							<input type="submit" value="검색"><br>
						</li>
					</ul>
				</li>
				<li style="position:relative;left:250px;">
					<input type="checkbox" name="house_status" id="house_status" value="0"><span>판매중인 것만 보기</span>
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
				<div class="image-space">
					<c:forEach var="house" items="${houseList}">
						<div class="horizontal-area24">
							<div class="horizontal-area">
							<a href="${pageContext.request.contextPath}/house/detailHouse.do?house_num=${house.house_num}">
								<img src="${pageContext.request.contextPath}/upload/${house.house_photo1}">
							</a>
							</div>
							<div class="list-horizontal">
							<a href="${pageContext.request.contextPath}/house/detailHouse.do?house_num=${house.house_num}">
								<span>${house.house_title}</span>
								<br>
								<b>
								<c:if test="${house.house_status == 0}"><span style="color:#f7b165"><b>판매중</b></span></c:if>
								<c:if test="${house.house_status == 1}"><span style="color:#ff0000"><b>판매완료</b></span></c:if> 
								<c:if test="${house.house_deal_type == 1}">전세</c:if>
								<c:if test="${house.house_deal_type == 2}">매매</c:if>
								<c:if test="${house.house_deal_type == 3}">월세</c:if>
								<c:if test="${house.house_deal_type == 4}">단기</c:if>
								<c:if test="${house.house_price%10000==0}"><fmt:formatNumber pattern="###,###,###,###,###,###" value="${house.house_price/10000}"/>만원</c:if>
								<c:if test="${house.house_price%10000!=0}"><fmt:formatNumber pattern="###,###,###,###,###,###" value="${house.house_price/10000}"/>만원+</c:if></b><br>
								관심 : ${house.favcount} 조회수 : ${house.hit}
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
		<input type="button" value="글쓰기" onclick="location.href='insertHouseForm.do'">			
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>