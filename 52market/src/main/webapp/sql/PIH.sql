create table board(
	board_num number not null,
	board_title varchar2(150) not null,
	board_content clob not null,
	board_hit number(9) default 0 not null,
	board_regdate date default sysdate not null,
	board_modifydate date,
	board_filename varchar2(150),
	board_ip varchar2(40) not null,
	mem_num number not null,
	constraint board_pk primary key (board_num),
	constraint board_fk foreign key (mem_num) references member (mem_num)
);

create sequence board_seq;

create table board_reply(
	re_num number not null,
	re_content varchar2(900) not null,
	re_regdate date default sysdate not null,
	re_modifydate date,
	re_ip varchar2(40) not null,
	board_num number not null,
	mem_num number not null,
	constraint board_reply_pk primary key (re_num),
	constraint board_reply_fk1 foreign key (board_num) references board (board_num),
	constraint board_reply_fk2 foreign key (mem_num) references member (mem_num)
);

create sequence board_reply_seq; 

create table board_fav(
	board_num number not null,
	mem_num number not null,
	constraint board_fav_fk1 foreign key (board_num) references board (board_num),
	constraint board_fav_fk2 foreign key (mem_num) references member (mem_num)
);	