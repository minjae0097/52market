--부동산 판매글 리스트 테이블
create table houselist(
house_num number not null,
house_content clob not null,
house_reg_date date default sysdate not null,
house_modify_date date,
house_status number(1) default 0 not null,
hit number(9) default 0 not null,
constraint houselist_pk primary key (house_num),
constraint houselist_detail_fk foreign key (house_num) references house_detail (house_num) 
);

create sequence house_detail_seq;

--부동산 게시글 테이블
create table house_detail(
house_num number not null,
house_seller_type number(1) not null,
house_type number(1) not null,
house_deal_type number(1) not null,
house_diposit number(12) not null,
house_price number(12) not null,
house_cost number(7) not null,
house_address1 varchar2(90) not null,
house_address2 varchar2(90) not null,
house_space number(5) not null,
house_floor number(5) not null,
house_move_in number(1) not null,
house_photo1 varchar2(60) not null,
house_photo2 varchar2(60) not null,
house_title varchar2(150) not null,
zipcode varchar2(5) not null,
house_trade_date date,
house_buyer number,
mem_num number,
constraint house_detail_pk primary key (house_num),
constraint house_detail_fk1 foreign key (mem_num) references member(mem_num),
constraint house_detail_fk2 foreign key (house_buyer) references member (mem_num)
);

--좋아요 테이블
create table house_fav(
house_num number not null,
mem_num number not null,
reg_date date default sysdate not null,
constraint house_fav_fk1 foreign key (house_num) references houselist (house_num),
constraint house_fav_fk2 foreign key (mem_num) references member(mem_num)
);

--채팅
create table house_chatroom(
	chatroom_num number primary key,
	house_num number references houselist (house_num) not null,
	seller_num number references member (mem_num) not null,
	buyer_num number references member (mem_num) not null
);

create table house_chat(
	chat_num number primary key,
	chatroom_num number references house_chatroom (chatroom_num) not null,
	mem_num number references member (mem_num) not null,
	message varchar2(900) not null,
	reg_date date default SYSDATE,
	read_check number(1) default 1 not null
);

create sequence house_chatroom_seq;
create sequence house_chat_seq;



