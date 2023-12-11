--부동산 판매글 리스트 테이블
create table houselist(
house_num number not null,
house_title varchar2(150) not null,
house_content clob not null,
house_reg_date date default sysdate not null,
house_modify_date date,
house_status number(1) default 0 not null,
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
house_diposit number(8) not null,
house_price number(10) not null,
house_cost number(7) not null,
house_address1 varchar2(90) not null,
house_address2 varchar2(90) not null,
house_space number(5) not null,
house_floor number(5) not null,
house_move_in number(1) not null,
house_filename varchar2(60) not null,
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
constraint house_fav_fk1 foreign key (house_num) references houselist (house_num),
constraint house_fav_fk2 foreign key (mem_num) references member(mem_num)
);