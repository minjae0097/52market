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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSH.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('search_form');
	//이벤트 연결
	/*myForm.onsubmit=function(){
		let keyword = document.getElementById('keyword');
		if(keyword.value.trim()==''){
			alert('검색어를 입력하세요!');
			keyword.value = '';
			keyword.focus();
			return false;
		}
	};*/
	
	//판매중만 보이는 필터
	let product_status = document.getElementById('product_status');
	product_status.onclick = function(){
		let myform = document.getElementById('search_form');
		myform.submit();
	}
	function base() {
		let product_status = document.getElementById('product_status');
		if(${product_status}==0) product_status.checked=true;
	}
	base();
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
		<br>
		<h2>중고거래 게시물</h2>
		<form class="align-center" id="search_form" action="list.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield" id="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색"><br>
				</li>
				<li>
					<input type="checkbox" id="product_status" name="product_status" value="0"><span>판매중인 상품만 보기</span>
				</li>
			</ul>
		</form>
		</div>
		<br>
		
		<c:if test="${count == 0}">
			<div class="result-display">
				표시할 게시물이 없습니다.
			</div>
		</c:if>
		
		<c:if test="${count>0}">
		<div class="image-space">
			<c:forEach var="product" items="${productList}">
				<div class="horizontal-area">
					<a href="${pageContext.request.contextPath}/product/productDetail.do?product_num=${product.product_num}">
						<img src="${pageContext.request.contextPath}/upload/${product.product_image}">
						<br>
						<span>${product.product_name}  
						<c:if test="${product.product_status==0}"><span style="background-color: gray;color: white; border-radius: 10px; padding:2px;"><b>판매중</b></span></c:if>
						<c:if test="${product.product_status==1}"><span style="background-color: gray;color: white; border-radius: 10px; padding:2px;"><b>판매완료</b></span></c:if>
						</span>
						<br>
						<span><fmt:formatNumber value="${product.product_price}"/>원</span><br>
						<br>
					</a>
				</div>
			</c:forEach>
			<div class="float-clear">
				<br>
				<hr width="100%" size="1" noshade="noshade">
			</div>
			<div class="align-center">${page}</div>
		</div>	
		</c:if>
	</div>
	<div class="align-right">  
		<input type="button" value="글쓰기" onclick="location.href='productWriteForm.do'">
	</div>
</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>