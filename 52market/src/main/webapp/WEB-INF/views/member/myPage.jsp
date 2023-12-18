<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#photo_btn').click(function(){
		$('#photo_choice').show();
		$(this).hide();//수정 버튼 감추기
	});
	
	//이미지 미리보기
	//처음 보여지는 이미지 읽기
	let photo_path = $('.my-photo').attr('src');
	$('#photo').change(function(){
		let my_photo = this.files[0];
		if(!my_photo){
			//선택을 취소하면 원래 처음 화면으로 되돌림
			$('.my-photo').attr('src',photo_path);
			return;
		}
		if(my_photo.size > 1024 * 1024){
			alert(Math.round(my_photo.size/1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
			$('.my-photo').attr('src',photo_path);
			$(this).val('');//선택한 파일 정보 지우기
			return;
		}
		
		//화면에서 이미지 미리보기
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);
		
		reader.onload=function(){
			$('.my-photo').attr('src',reader.result);
		};
		
	});//end of change
	
	//이미지 전송
	$('#photo_submit').click(function(){
		if($('photo').val()==''){
			alert('파일을 선택하세요!');
			$('#photo').focus();
			return;
		}
		//파일 전송
		const form_data = new FormData();
		/*
		자바스크립트로 호출 document.getElementById('photo').files[0]
		jquery로 호출 $('#photo')[0].files[0]
		*/
		form_data.append('photo',$('#photo')[0].files[0]);
		$.ajax({
			url:'updateMyPhoto.do',
			type:'post',
			data:form_data,
			dataType:'json',
			contentType:false,//데이터 객체를 문자열로 바꿀지에 대한 값. true 일반문자
			processData:false,//해당 타입을 true로 하면 일반 text로 구분
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 사용하세요!');
				}else if(param.result == 'success'){
					alert('프로필 사진이 수정되었습니다.');
					//수정된 이미지 정보 저장
					photo_path = $('.my-photo').attr('src');
					$('#photo').val('');
					$('#photo_choice').hide();
					$('#photo_btn').show();//수정 버튼 표시
				}else{
					alert('파일 전송 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
			
		});
		
	});//end of click
	
	$('#photo_reset').click(function(){
		//초기 이미지 표시
		//이미지 미리보기 전 이미지로 되돌리기
		$('.my-photo').attr('src',photo_path);
		$('#photo').val('');
		$('#photo_choice').hide();
		$('#photo_btn').show();
	});
	
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h3>마이페이지</h3>
		<div class="mypage-div align-center">
			<c:if test="${empty member.mem_photo}">
			<img src="${pageContext.request.contextPath}/images/face.png" width="100%" height="100%" class="my-photo">
			</c:if>
			<c:if test="${!empty member.mem_photo}">
			<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="100%" height="100%" class="my-photo">
			</c:if>
			<div class="align-center">
				<input type="button" value="이미지 변경" id="photo_btn">
			</div>
			<div id="photo_choice" style="display: none;">
				<input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br> 
				<input type="button" value="변경" id="photo_submit"> 
				<input type="button" value="취소" id="photo_reset">
			</div>
			</div>
		<div class="mypage-div">
			<span><b>${member.mem_nickname}</b>님</span>
			<button onclick="location.href='modifyNicknameForm.do'">닉네임 변경</button>
			<ul>
				<li>
					<label>위치 : ${member.mem_address1}</label>
				</li>
				<li>
					<label>이메일 : ${member.mem_email}</label>
				</li>	
				<li>	
					<label>연락처 : ${member.mem_phone}</label>
				</li>
				<li>	
					<label>가입일 : ${member.mem_regdate}</label>
				</li>
			</ul>
			<div class="align-center">
				<button onclick="location.href='modifyMemberForm.do'">회원정보 수정</button>
				<button onclick="location.href='modifyPasswordForm.do'">비밀번호 변경</button>
			</div>
		</div>
		<hr size="1" width="100%">
		<div>
		<h3>나의 거래</h3>
		<ul>
			<li><a>관심목록</a></li>
			<li><a>구매내역</a></li>
			<li><a>판매내역</a></li>
		</ul>
		</div>
		<hr size="1" width="100%">
		<div>
		<h3>동네 생활</h3>
		<ul>
			<li><a>동네생활 활동</a></li>
		</ul>
		</div>
	</div>
</div>
</body>
</html>