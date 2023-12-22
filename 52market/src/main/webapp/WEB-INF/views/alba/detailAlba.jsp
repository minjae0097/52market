<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알바 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/KJY.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
window.onload=function(){
	//좋아요 선택 여부와 선택한 총 개수 읽기
	let element_layer = document.getElementById('layer');
	let element_layer2 = document.getElementById('layer2');
	
	function selectFav(){
		$.ajax({
			url:'getAlbaFav.do',
			type:'post',
			data:{alba_num:$('#output_fav').attr('data-num')},
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
			url:'writeAlbaFav.do',
			type:'post',
			data:{alba_num:$(this).attr('data-num')},
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
		if(param.status=='yesFav'){//좋아요 선택
			output = '../images/fav02.gif';
		}else{//좋아요 미선택
			output = '../images/fav01.gif';
		}
		
		//문서 객체에 설정
		$('#output_fav').attr('src',output);
		$('.output_fcount').text(param.count);
	}
	//초기 데이터 호출
	
	<c:if test="${user_auth == 1 || user_auth==2}">selectFav();
	
	let call_btn = document.getElementById('call_btn');
	//이벤트 연결
	call_btn.onclick=function(){
	    
		openlayer();
	};
	
	function closelayer() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
    }
    function openlayer() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'block';
        initLayerPosition();
    }
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 100; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 3; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
	let close_btn = document.getElementById('close_btn');
	close_btn.onclick=function(){
		closelayer();
	}
	
	
	
	let apply_btn = document.getElementById('apply_btn');
	//이벤트 연결
	apply_btn.onclick=function(){
	    
		openlayer2();
	};
	
	function closelayer2() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer2.style.display = 'none';
    }
    function openlayer2() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer2.style.display = 'block';
        initLayerPosition2();
    }
    function initLayerPosition2(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 150; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 3; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer2.style.width = width + 'px';
        element_layer2.style.height = height + 'px';
        element_layer2.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer2.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer2.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
	let close_btn2 = document.getElementById('close_btn2');
	close_btn2.onclick=function(){
		closelayer2();
	}
	</c:if>
	
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-alba-main">
		<img src="${pageContext.request.contextPath}/upload/${alba.alba_photo}" width="250" height="250" id="alba-photo1">
	<div class="horizontal-area">
		<c:if test="${!empty mem.mem_photo}">
			<img src="${pageContext.request.contextPath}/upload/${mem.mem_photo}" width="30" height="30" class="my-photo">
		</c:if>
		<c:if test="${empty mem.mem_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png" width="30" height="30" class="my-photo">
		<span>${alba.mem_nickname}</span><br>
		조회수 ${alba.alba_hit}
		관심<span id="output_fcount" class="output_fcount"></span>
		지원자수 ${alba.apcount} 
		</c:if>
	</div>
	<div class="float-clear">
		<hr width="100%" size="1" noshade="noshade">
	</div>
		<span>${alba.alba_title}</span>
		<div>
		<h3>정보</h3>
		<ul>
			<li>${alba.alba_content1}</li>
		</ul>
		</div>
		<div>
		<h3>상세 정보</h3>
		<ul>
			<li>${alba.alba_content2}</li>
		</ul>
		<h4>위치</h4>
		<ul>
			<li><img src="${pageContext.request.contextPath}/upload/${alba.alba_location}" width="500" height="250"></li>
			<li>${alba.alba_address1}</li>
			<li>${alba.alba_address2}</li>
		</ul>
		<div class="float-clear">
		<hr width="100%" size="1" noshade="noshade">
		<ul class="detail-sub">
		<li>
		<%-- 좋아요 시작 --%>
		<c:if test="${user_auth != 3 && user_auth!=9}">
			<img id="output_fav" data-num="${alba.alba_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
					관심
			<span  class="output_fcount"></span>
		<%-- 좋아요 끝 --%>
		</c:if>
		</li>
		<li>
		<c:if test="${user_auth != 3 && user_auth!=9}">
		<input type="button" value="문의하기" id="call_btn">
		<input type="button" value="지원하기" id="apply_btn">
		</c:if>
		</li>
		</ul>
		</div>
		<div class="align-right">
		<ul>
		<li>
		<c:if test="${user_auth==3}">
		<input type="button" value="수정" onclick="location.href='updateAlbaForm.do?alba_num=${alba.alba_num}'">
		</c:if>
		<c:if test="${user_auth==9||user_auth==3}">
		<input type="button" value="삭제" id="delete_btn" onclick="location.href='deleteAlbaForm.do?alba_num=${alba.alba_num}'">
		<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			//이벤트 연결
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('deleteAlba.do?alba_num=${alba.alba_num}');
				}
			};
			</script>
		</c:if>
		</li>
		</ul>
		</div>
		</div>
		</div>
	</div>

<form id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;background-color:#BDBDBD;">
<ul>
	<li>전화번호: ${db_member.mem_phone}</li>
</ul>
	<div class="align-center">
	<input id="close_btn" type="button" value="닫기">
	</div>
</form>
<form action="apList.do?alba_num=${alba.alba_num}" method="post" enctype="multipart/form-data" id="layer2" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;background-color:#BDBDBD;">
<ul>
	<li>
		<label for="filename">지원서 파일 등록</label>
		<input type="hidden" name="alba_title" value="${alba.alba_title}">
		<input type="file" name="alba_filename" id="filename" accept="image/gif,image/png,image/jpeg">
	</li>
</ul>
	<div class="align-center">
		<input type="submit" value="등록" >   
		<input id="close_btn2" type="button" value="취소">
	</div>
</form>
</body>
</html>


