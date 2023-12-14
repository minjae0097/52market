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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/house_fav.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-detail">
		<img src="${pageContext.request.contextPath}/upload/${detail.house_photo2}" width="400">
		<div class="horizontal-area">
			<c:if test="${!empty seller.mem_photo}">
			<img src="${pageContext.request.contextPath}/upload/${seller.mem_photo}" width="30" height="30" class="my-photo">
			</c:if>
			<c:if test="${empty seller.mem_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png" width="30" height="30" class="my-photo">
			</c:if>
			<span>${detail.mem_nickname}</span>
			<button onclick="location.href='updateHouseForm.do?house_num=${detail.house_num}'">수정</button>
			<button>삭제</button>
		</div>
		<hr width="100%" size="1">
		<div>
			<c:if test="${detail.house_seller_type == 1}">세입자</c:if>
			<c:if test="${detail.house_seller_type == 2}">집주인</c:if>
			<c:if test="${detail.house_type == 1}">원룸</c:if>
			<c:if test="${detail.house_type == 2}">빌라</c:if>
			<c:if test="${detail.house_type == 3}">아파트</c:if>
			<c:if test="${detail.house_type == 4}">오피스텔</c:if>
			<c:if test="${detail.house_type == 5}">상가</c:if>
			<c:if test="${detail.house_type == 6}">기타(사무실,주택,토지 등)</c:if>
		</div>
		<div>
			<span>${detail.house_title} . 
			<c:if test="${detail.house_price%10000==0}"><fmt:formatNumber value="${detail.house_price/10000}"/>만원</c:if>
			<c:if test="${detail.house_price%10000!=0}"><fmt:formatNumber value="${detail.house_price}"/>원</c:if><br>
			조회수 : ${detail.hit}
			</span>
		</div>
		<div>
			<h2>정보</h2>
				<ul>
					<li>면적 : ${detail.house_space}평</li>
					<li>층 : ${detail.house_floor}층</li>
					<li>거래방식 : 
					<c:if test="${detail.house_deal_type == 1}">전세</c:if>
					<c:if test="${detail.house_deal_type == 2}">매매</c:if>
					<c:if test="${detail.house_deal_type == 3}">월세</c:if>
					<c:if test="${detail.house_deal_type == 4}">단기</c:if>
					</li>
					<li>가격 : 
					<c:if test="${detail.house_price%10000==0}"><fmt:formatNumber value="${detail.house_price/10000}"/>만원</c:if>
					<c:if test="${detail.house_price%10000!=0}"><fmt:formatNumber value="${detail.house_price}"/>원</c:if>
					</li>
					<li>보증금 : 
					<c:if test="${detail.house_diposit%10000==0}"><fmt:formatNumber value="${detail.house_diposit/10000}"/>만원</c:if>
					<c:if test="${detail.house_diposit%10000!=0}"><fmt:formatNumber value="${detail.house_diposit}"/>원</c:if>
					</li>
					<li>관리비 : 
					<c:if test="${detail.house_cost%10000==0}"><fmt:formatNumber value="${detail.house_cost/10000}"/>만원</c:if>
					<c:if test="${detail.house_cost%10000!=0}"><fmt:formatNumber value="${detail.house_cost}"/>원</c:if>
					</li>
					<li>입주시기 : 
					<c:if test="${detail.house_move_in == 1}">즉시입주가능</c:if>
					<c:if test="${detail.house_move_in == 2}">확인필요</c:if>
					</li>
					<li>주소 : ${detail.house_address1} ${detail.house_address2}</li>
				</ul>
			<h2>소개</h2>
				<ul>
					<li>
					<span>${list.house_content}</span>
					</li>
				</ul>	
		</div>
		<hr width="100%" size="1">
		<div>
			<%-- 좋아요 시작 --%>
				<img id="output_fav" data-num="${list.house_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
				좋아요
				<span id="output_fcount"></span>
			<%-- 좋아요 끝 --%>
		</div>
	</div>
</div>
</body>
</html>