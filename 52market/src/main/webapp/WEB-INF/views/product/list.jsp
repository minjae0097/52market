<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
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
	
	//판매중만 보이는 필터
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<div class="align-center">
			<a href="${pageContext.request.contextPath}/product/list.do">
			<img src="${pageContext.request.contextPath}/images/중고거래배너.png" width="900">
			</a>
		</div>
		<div>
		<h3>중고거래 게시물</h3>
		<form id="search_form" action="list.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
		<input type='checkbox' name='product_status' value='0' />판매중인 상품만 보기
		</div>
		<br>
		<div class="image-space">
			<c:forEach var="detail" items="${detail}">
				<div class="horizontal-area">
					<a href="${pageContext.request.contextPath}/product/productDetail.do?product_num=${detail.product_num}">
						<img src="${pageContext.request.contextPath}/upload/${detail.product_image}">
						<span>${detail.product_name}</span>
						<br>
						<span><fmt:formatNumber value="${detail.product_price}"/>원</span>
						<br>
					</a>
				</div>
			</c:forEach>
			<div class="float-clear">
				<br>
				<hr width="100%" size="1" noshade="noshade">
			</div>
		</div>
	</div>
	<div class="align-right">
		<input type="button" value="글쓰기" onclick="location.href='productWriteForm.do'">
	</div>
</div>
</body>