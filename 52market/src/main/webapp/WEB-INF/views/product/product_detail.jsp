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
<script type="text/javascript">
<%--$('#product_status').change(function(){
	if($(this).val()=="0"){//판매중
		$('#text_test').text("판매중");
	}else if($(this).val()=="1"){//판매완료
		$('#text_test').text("판매완료");
	}
});--%>
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${product.product_image1}" class="detail-img">
	</div>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty product.mem_photo}">
				<img src="${pageContext.request.contextPath}/upload/${product.mem_photo}" width="30" height="30" class="my-photo">	
				</c:if>
				<c:if test="${empty product.mem_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" width="30" height="30" class="my-photo">	
				</c:if>
			</li>
			<li>${product.mem_nickname}</li>
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
			<hr size="1" noshade="noshade" width="100%">
		</ul>
		<div>
			<ul>
				<li>
				<c:if test="${product.product_status == 0}">판매중</c:if> <%-- span, text로 값 바꾸기 --%>
				<c:if test="${product.product_status == 1}">판매완료</c:if>
				${product.product_title}</li>
				<li>
				<c:if test="${detail.product_category == 1}">디지털기기</c:if>
				<c:if test="${detail.product_category == 2}">생활/주방</c:if>
				<c:if test="${detail.product_category == 3}">유아동</c:if>
				<c:if test="${detail.product_category == 4}">의류/잡화</c:if>
				<c:if test="${detail.product_category == 5}">티켓/교환권</c:if>
				</li>
				<!-- 끌올 시간 추가하기 -->
			</ul>
			<ul>
				<li>
					<span>가격 : ${detail.product_price}</span><br>
					<span>${product.product_content}</span>
				</li>
			</ul>
			<ul>
				<h3>위치</h3>
					<jsp:include page="/map/showMap.jsp"/>
			</ul>	
		</div>
		<div class="etc">
		<ul class="detail-sub">
			<li>
				조회 ${product.product_hit}
				관심 <span id="output_fcount"></span>
				채팅 <!-- 채팅 개수 추가 --><br>
				<c:if test="${!empty product.product_modify_date}">
					최근 수정일 : ${product.product_modify_date}
				</c:if>
				작성일 : ${product.product_reg_date}
			</li>
			
			<hr size="1" noshade="noshade" width="100%">
			
			
			<%-- 타인 로그인 시 관심물품 / 본인글이면 판매상태 변경 버튼 --%>
			<c:if test="${user_num != product.product_mem}">
				<li>
					<img id="output_fav" data-num="${product.product_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
					관심글 등록
					<span id="output_fcount"></span>                                               
				</li>
			</c:if>
			
			<c:if test="${user_num == product.product_mem}">
				<li>
					<select name="product_status" id="product_status"> <%--change 이벤트 *id설정해서 선택한 밸류 0-판매중 / onchange--%>
						<option value="0">판매중</option>
						<option value="1">판매완료</option> 
					</select>	
				</li>
			</c:if>
			<li>
				<input type="button" value="채팅하기">
				<input type="button" value="목록" onclick="location.href='list.do'"> 
			</li>
		</ul>
		</div>
	</div>
</div>
</body>
</html>