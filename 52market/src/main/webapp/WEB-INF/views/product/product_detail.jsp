<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래글 상세 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/product.fav.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${product.product_title}</h2>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty product.mem_photo}">
				<img src="${pageContext.request.contextPath}/upload/${product.mem_photo}" width="30" height="30" class="my-photo">	
				</c:if>
				<c:if test="${empty product.mem_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" width="30" height="30" class="my-photo">	
				</c:if>
			</li>
			<li>
				${product.mem_nickname}
			</li>
			
			<li>
				<%-- 관심물품 --%>
				<img id="output_fav" data-num="${product.product_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
				관심글 등록
				<span id="output_fcount"></span>                                               
			</li>
			
			
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${product.product_image}" class="detail-img">
		</div>
		
		<div>
			<h3>상품정보</h3>
			<ul>
				<li>상품명 : ${detail.product_name}</li>
				<li>가격 : ${detail.product_price}</li>
			</ul>
			<h3>상품글</h3>
			<ul>
				<li>
					<span>${product.product_content}</span>
				</li>
			</ul>
			<h3>거래희망장소</h3>
			<jsp:include page="/map/showMap.jsp"/>
		</div>
		<ul class="detail-sub">
			<li>
				조회 : ${product.product_hit}<br>
				관심 등록수 : <span id="output_fcount"></span><br>
			</li>
			<li>
				<c:if test="${!empty product.product_modify_date}">
					최근 수정일 : ${product.product_modify_date}
				</c:if>
				작성일 : ${product.product_reg_date}
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<c:if test="${user_num == product.product_mem}">
				<input type="button" value="수정" onclick="location.href='productUpdateForm.do?product_num=${product.product_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('productDelete.do?product_num=${product.product_num}');
						}
					};
				</script>
				</c:if>     
			</li>
		</ul>
	</div>
</div>
</body>
</html>