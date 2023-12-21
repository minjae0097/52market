$(function(){
	//초기 데이터(목록) 호출
	selectList();
	function selectList(){
		
		let form_data = $('#chat_form').serialize();
		$('#loading').show();
		$.ajax({
			url:'listChatCar.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				
				$(param.list).each(function(index,item){
					let chatList = "";
					if(item.mem_num==param.user_num){
						chatList = '<div class="chat ch2">'
					}else{
						chatList = '<div class="chat ch1">';
					}
					chatList += '<div class="textbox">'+item.message+'</div>';
					
					//문서 객체에 추가
					$('#chatList').append(chatList);
				});
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}
		});
	}
	
	//댓글 등록
	$('#chat_form').submit(function(event){
		if($('#message').val().trim()==''){
			alert('내용을 입력하세요');
			$('#message').val('').focus();
			return false;
		}
		
		//form 이하의 태그에 입력한 데이터를 모두 읽어옴(쿼리 스트링 형식으로 읽어옴)
		let form_data = $(this).serialize();		
		
		//서버와 통신
		$.ajax({
			url:'writeChatCar.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					divClear();
					selectList();
				}else{
					alert('채팅 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
	
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
	}
	
	//textarea에 내용 입력시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함
		let inputLength = $(this).val().length;
		
		if(inputLength > 300){//300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		}else{//300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id') == 'message'){
				//등록폼 글자수
				$('#re_first .letter-count').text(remain);
			}else{
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
	//채팅창 내용 제거
	function divClear(){
		let chatList = document.getElementById('chatList');
		chatList.innerText = '';
	}
});