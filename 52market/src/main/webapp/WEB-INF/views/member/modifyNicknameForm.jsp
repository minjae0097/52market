<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>닉네임 변경</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	let nicknameChecked = 0; //0은 중복체크 미실행 또는 중복, 1은 미중복
	
	//닉네임 중복체크
	$('#nickname_check').click(function(){
		//서버와 통신
		$.ajax({
			url:'checkDuplicatedNickname.do',
			type:'post',
			data:{mem_nickname:$('#mem_nickname').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'nicknameNotFound'){
					nicknameChecked = 1;
					$('#message_nickname').css('color','#000000').text('변경 가능 닉네임');
				}else if(param.result == 'nicknameDuplicated'){
					nicknameChecked = 0
					$('#message_nickname').css('color','red').text('중복된 닉네임');
					$('#mem_nickname').val('').focus();
				}else{
					nicknameChecked = 0;
					alert('닉네임 중복체크 오류 발생');
				}
			},
			error:function(){
				nicknameChecked = 0;
				alert('네트워크 오류 발생');
			}
		});
	});//end of click
	
	//닉네임 중복 안내 메시지 초기화 및 닉네임 중복 값 초기화')
	$('#modify_form #mem_nickname').keydown(function(){
		nicknameChecked = 0;
		$('#message_nickname').text('');
	});//end of keydown
	
	//회원정보 등록 유효성 체크
	$('#modify_form').submit(function(){
		let passwd = document.getElementById('mem_passwd');
		if(passwd.value.trim()==''){
				alert('비밀번호를 입력해주세요');
				passwd.value = '';
				passwd.focus();
				return false;
		}//end of if
		let nickname = document.getElementById('mem_nickname');
		if(nickname.value.trim()==''){
				alert('변경할 닉네임을 입력해주세요');
				nickname.value = '';
				nickname.focus();
				return false;
		}//end of if
		
		if(nicknameChecked==0){
			alert('닉네임 중복체크 필수');
			return false;
		}
	});//end of submit
	
});	
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>닉네임 변경</h2>
		<form action="modifyNickname.do" method="post"
		                              id="modify_form">
			<ul>
				<li>
					<label for="mem_nickname">닉네임</label>
					<input type="text" name="mem_nickname" id="mem_nickname" maxlength="10" class="input-check">
					<input type="button" value="닉네임 중복체크" id="nickname_check">
					<span id="message_nickname"></span> 
					<div class="form-notice">*(최대 10자)</div> 
				</li>
				<li>
					<label for="mem_passwd">비밀번호</label>
					<input type="password" name="mem_passwd" id="mem_passwd"
					                          maxlength="12">
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="닉네임 변경">
				<input type="button" value="My페이지" 
				                   onclick="location.href='myPage.do'">
			</div>                             
		</form>
	</div>
</div>
</body>
</html>



