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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SSY.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script> --%>
<script type="text/javascript">
$(function(){
	//좋아요 선택 여부와 선택한 총 개수 읽기
	function selectFav(){
		$.ajax({
			url:'getHouseFav.do',
			type:'post',
			data:{house_num:$('#output_fav').attr('data-num')},
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
			url:'writeHouseFav.do',
			type:'post',
			data:{house_num:$(this).attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 좋아요를 눌러주세요.');
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
	//좋아요 표시 함수
	function displayFav(param){
		let output;
		if(param.status == 'yesFav'){//좋아요 선택
			output = '../images/fav02.gif';
		}else{//좋아요 미선택
			output = '../images/fav01.gif';
		}
		
		//문서 객체에 설정
		$('#output_fav').attr('src',output);
		$('#output_fcount').text(param.count);
	}
	//초기 데이터 호출
	<c:if test="${user_num!=detail.mem_num}">selectFav();</c:if>
});
</script>
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
			<button onclick="location.href='updateHouseForm.do?house_num=${list.house_num}'">수정</button>
			<button id="delete_btn">삭제</button>
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					let choice = confirm('삭제하시겠습니까?');
					if(choice){
						location.href='deleteHouse.do?house_num=${list.house_num}';
					}
				}
			</script>
		</div>
		<hr width="100%" size="1">
		<div>
			<c:if test="${detail.house_seller_type == 1}">세입자</c:if>
			<c:if test="${detail.house_seller_type == 2}">집주인</c:if>/
			<c:if test="${detail.house_type == 1}">원룸</c:if>
			<c:if test="${detail.house_type == 2}">빌라</c:if>
			<c:if test="${detail.house_type == 3}">아파트</c:if>
			<c:if test="${detail.house_type == 4}">오피스텔</c:if>
			<c:if test="${detail.house_type == 5}">상가</c:if>
			<c:if test="${detail.house_type == 6}">기타(사무실,주택,토지 등)</c:if>
		</div>
		<div>
			<span>
			<c:if test="${list.house_status == 0}"><span style="color:#f7b165"><b>판매중</b></span></c:if>
			<c:if test="${list.house_status == 1}"><span style="color:#ff0000"><b>판매완료</b></span></c:if>  
			<c:if test="${detail.house_deal_type == 1}">전세</c:if>
			<c:if test="${detail.house_deal_type == 2}">매매</c:if>
			<c:if test="${detail.house_deal_type == 3}">월세</c:if>
			<c:if test="${detail.house_deal_type == 4}">단기</c:if>
			<c:if test="${detail.house_price%10000==0}"><fmt:formatNumber value="${detail.house_price/10000}"/>만원</c:if>
			<c:if test="${detail.house_price%10000!=0}"><fmt:formatNumber value="${detail.house_price}"/>원</c:if><br>
			조회수 : ${list.hit}
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
			<c:if test="${user_num != detail.mem_num}">
			<%-- 좋아요 시작 --%>
				<img id="output_fav" data-num="${list.house_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
				관심글 등록
				<span id="output_fcount"></span>
			<%-- 좋아요 끝 --%>
			
			<button onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForBuyerCar.do?carlist_num=${list.carlist_num}'">채팅하기</button>
			</c:if>
			<%-- 판매 변경 --%>
			<c:if test="${user_num==detail.mem_num}">
			<form action="updateStatus.do" id="update_form" method="post">
			<input type="hidden" name="house_num" value="${detail.house_num}">
			<select name="house_status" onchange="this.form.submit()">
				<option value="0" <c:if test="${list.house_status==0}">selected</c:if>>판매중</option>
				<option value="1" <c:if test="${list.house_status==1}">selected</c:if>>판매완료</option>
			</select>
			</form>
			</c:if>
			<div class="align-right">
			<input type="button" value="목록" onclick="location.href='list.do'">
			</div>
		</div>
	</div>
</div>
</body>
</html>