$(function(){
	let idChecked = 0; //0은 중복체크 미실행 또는 중복, 1은 미중복
	let nicknameChecked = 0; //0은 중복체크 미실행 또는 중복, 1은 미중복
	
	//아이디 중복체크
	$('#id_check').click(function(){
		if(!/^[A-Za-z0-9]{4,12}$/.test($('#mem_id').val())){
			alert('영문 또는 숫자 사용, 최소 4자 ~ 최대 12자를 사용하세요!');
			$('#mem_id').val('').focus();
			return false;
		}
		//서버와 통신
		$.ajax({
			url:'checkDuplicatedId.do',
			type:'post',
			data:{mem_id:$('#mem_id').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'idNotFound'){
					idChecked = 1;
					$('#message_id').css('color','#000000').text('등록 가능 ID');
				}else if(param.result == 'idDuplicated'){
					idChecked = 0
					$('#message_id').css('color','red').text('중복된 ID');
					$('#mem_id').val('').focus();
				}else{
					idChecked = 0;
					alert('아이디 중복체크 오류 발생');
				}
			},
			error:function(){
				idChecked = 0;
				alert('네트워크 오류 발생');
			}
		});
	});//end of click
	
	//아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화')
	$('#register_form #mem_id').keydown(function(){
		idChecked = 0;
		$('#message_id').text('');
	});//end of keydown
	
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
					$('#message_nickname').css('color','#000000').text('등록 가능 닉네임');
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
	
	//아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화')
	$('#register_form #mem_nickname').keydown(function(){
		nicknameChecked = 0;
		$('#message_nickname').text('');
	});//end of keydown
	
	//회원정보 등록 유효성 체크
	$('#register_form').submit(function(){
		let items = document.querySelectorAll('.input-check');
		for(let i=0;i<items.length;i++){
			if(items[i].value.trim()==''){
				let label = document.querySelector('label[for="'+items[i].id+'"]');
				alert(label.textContent + ' 항목은 필수 입력');
				items[i].value = '';
				items[i].focus();
				return false;
			}//end of if
			
			if($('input[type=radio]:checked').length < 1){
			alert('회원유형을 선택하세요!');
			return false;
		}
			if(items[i].id == 'mem_id' 
					     && !/^[A-Za-z0-9]{4,12}$/.test($('#mem_id').val())){
				alert('영문 또는 숫자 사용, 최소 4자 ~ 최대 12자를 사용하세요!');
				$('#mem_id').val('').focus();
				return false;
			}
			
			if(items[i].id =='mem_id' && idChecked==0){
				alert('아이디 중복체크 필수');
				return false;
			}
			if(items[i].id =='mem_nickname' && nicknameChecked==0){
				alert('닉네임 중복체크 필수');
				return false;
			}
			
			if(items[i].id == 'zipcode' 
					            && !/^[0-9]{5}$/.test($('#zipcode').val())){
				alert('우편번호를 입력하세요(숫자5자리)');
				$('#zipcode').val('').focus();
				return false;
			}
	
		}//end of for
	});//end of submit
	
});	