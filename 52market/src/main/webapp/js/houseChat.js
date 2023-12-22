$(function(){
	let message_socket = new WebSocket("ws://localhost:8080/52market/webSocket");
		message_socket.onopen = function(evt) {
				message_socket.send("usg:");
		};
		//서버로부터 메시지를 받으면 호출되는 함수 지정
		message_socket.onmessage = function(evt) {
			//메시지 알림
			let data = evt.data;
			if (data.substring(0, 4) == "usg:") {
				console.log('데이터 처리');
				selectList();
			}
		};
		message_socket.onclose = function(evt) {
			//소켓이 종료된 후 부과적인 작업이 있을 경우 명시
		};	
	
	//초기 데이터(목록) 호출
	function selectList(){
		let form_data = $('#chat_form').serialize();
		$('#loading').show();
		$.ajax({
			url:'listChatHouse.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				
				$('#chatList').empty();
				
				$(param.list).each(function(index,item){
					let chatList = "";
					if(item.mem_num==param.user_num){
						chatList = '<div class="chat ch2">'
					}else{
						chatList = '<div class="chat ch1">';
					}
					if(item.read_check==1){
						chatList += '<div class="read"><span>1</span></div>';
					}
					chatList += '<div class="textbox">'+item.message+'</div></div>';
					
					//문서 객체에 추가
					$('#chatList').append(chatList);
					//스크롤를 하단으로 위치시킴
					$('#chatList').scrollTop($("#chatList")[0].scrollHeight);
				});
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류 발생');
				message_socket.close();
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
			url:'writeChatHouse.do',
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
					message_socket.send("usg:");
				}else{
					alert('채팅 오류 발생');
					message_socket.close();
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
				message_socket.close();
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
	$('#message').keydown(function(event){
			if(event.keyCode == 13 && !event.shiftKey) {
				$('#chat_form').trigger('submit');
			}
	});
	$('#sell').click(function(){
		let chatroom_num = $(this).attr('data-sell');
			$.ajax({
			url:'sellChatHouse.do',
			type:'post',
			data:{chatroom_num:chatroom_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					divClear();
					message_socket.send("usg:");
				}else{
					alert('채팅 오류 발생');
					message_socket.close();
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
				message_socket.close();
			}
		});
		//기본 이벤트 제거
	})
	//부동산 판매
});