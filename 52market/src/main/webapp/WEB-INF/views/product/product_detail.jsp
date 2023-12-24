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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSH.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//좋아요 선택 여부와 선택한 총 개수 읽기
	function selectFav(){
		$.ajax({
			url:'getProductFav.do',
			type:'post',
			data:{product_num:$('#output_fav').attr('data-num')},
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
			url:'writeProductFav.do',
			type:'post',
			data:{product_num:$(this).attr('data-num')},
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
		if(param.status=='yesFav'){ //좋아요 선택
			output = '../images/fav02.gif';
		}else{ //좋아요 미선택
			output = '../images/fav01.gif';
		}
		//문서 객체에 설정
		$('#output_fav').attr('src',output);
		$('#output_fcount').text(param.count);
	}
	//초기 데이터 호출
	if(user_num!=${detail.product_seller}){
		selectFav();
	}
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${product.product_image1}" class="detail-img">
	</div>
	<br>
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
		</ul>    
		<br>
		<hr size="1" noshade="noshade" width="100%">
		<br>
		
		<div>
			<ul>
				<li>
				<c:if test="${product.product_status == 0}"><span><b>판매중</b></span></c:if> 
				<c:if test="${product.product_status == 1}"><span><b>판매완료</b></span></c:if>
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
					<span>가격 : <fmt:formatNumber value="${detail.product_price}"/>원</span><br>
					<span>${product.product_content}</span>
				</li>
			</ul>
			<br>
			<br>
			<br>
			<ul>
				<h3>위치</h3>
					<jsp:include page="/map/showMap.jsp"/>
			</ul>	
		</div>
		<br>
		<br>
		<div class="etc">
		<ul class="align-left">
			<li>
				조회 ${product.product_hit}
				관심 <span id="output_fcount"></span>
				채팅 <!-- 채팅 개수 추가 --><br>
				작성일 : ${product.product_reg_date}
				<c:if test="${!empty product.product_modify_date}">
					/ 최근 수정일 : ${product.product_modify_date}
				</c:if>
			</li>
		</ul>	
			<hr size="1" noshade="noshade" width="100%">
			
			
		<%-- 타인 로그인 시 관심물품 / 본인글이면 판매상태 변경 버튼 --%>
		<ul class="algin-left">
			<c:if test="${user_num != product.product_mem}">
				<li>
					<img id="output_fav" data-num="${product.product_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
					관심글 등록
					<span id="output_fcount"></span>                                               
				</li>
			</c:if>
		</ul>
		<%-- 좋아요 끝 --%>
			
		<%-- 판매상태 변경 버튼 --%>
		<ul class="align-left">
			<c:if test="${user_num == product.product_mem}">
				<form action="updateStatus.do" id="update_form" method="post" style="border:0 solid black;">
				<input type="hidden" name="product_num" value="${product.product_num}">
				<select name="product_status" onchange="this.form.submit()">
					<option value="0" <c:if test="${product.product_status==0}">selected</c:if>>판매중</option>
					<option value="1" <c:if test="${product.product_status==1}">selected</c:if>>판매완료</option> 
				</select>
				</form>
			</c:if>
		</ul>
		
		
		<ul class="align-right">
			<%-- 본인글 아닐때 채팅 --%>	
			<li>
			<c:if test="${user_num != product.product_mem}">
				<input type="button" value="채팅하기" onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForBuyerProduct.do?product_num=${product.product_num}'">
			</c:if>
			<%-- 채팅끝 --%>	
				<input type="button" value="목록" onclick="location.href='list.do'"> 
			</li>
		</ul>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>