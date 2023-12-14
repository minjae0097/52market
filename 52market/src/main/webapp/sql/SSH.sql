--제품상세
create table product_detail(
 product_num number not null,
 product_seller number not null,
 product_buyer number,
 product_category number(1) not null,
 product_price number(12) not null,
 product_tradedate date,
 product_image varchar2(150) not null,
 
 constraint product_detail_pk primary key (product_num),
 constraint product_detail_fk1 foreign key (product_seller) references member (mem_num),
 constraint product_detail_fk2 foreign key (product_buyer) references member (mem_num)
);

--제품list
create table product(
 product_num number not null,
 product_title varchar2(150) not null,
 product_content clob not null,
 product_image varchar2(150) not null,
 product_reg_date date default sysdate not null,
 product_modify_date date,
 product_status number(1) default 0 not null,
 product_mem number not null, --detail 때문에 추가 
 product_hit number(9) default 0 not null, --조회수 추가
 
 constraint product_pk primary key (product_num),
 constraint product_detail_fk1 foreign key (product_mem) references member (mem_num),
 constraint product_detail_fk2 foreign key (product_num) references product_detail (product_num)
);


--제품 관심 등록
create table product_fav(
 product_num number not null,
 mem_num number not null,

 constraint product_fav_fk1 foreign key (product_num) references product (product_num),
 constraint product_fav_fk2 foreign key (mem_num) references member (mem_num)
);

--문의 게시판
create table qna(
 qna_num number not null,
 mem_num number not null,
 qna_title varchar2(150) not null,
 question_content clob not null,
 question_filename varchar2(150),
 question_regdate date default sysdate not null,
 ask_content clob,
 ask_regdate date,

 constraint qna_pk primary key (qna_num),
 constraint qna_fk foreign key (mem_num) references member (mem_num)
);
create sequence qna_seq;
