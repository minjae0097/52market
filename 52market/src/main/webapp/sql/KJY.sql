create table alba(
	alba_num number not null,
	alba_title varchar2(150) not null,
	alba_content1 clob not null,
	alba_content2 clob not null,
	alba_reg_date date default sysdate not null,
	alba_modify_date date default sysdate not null,
	alba_photo varchar2(60),
	alba_ip varchar2(40) not null,
	alba_location varchar2(150),
	abla_post varchar2(5) not null,
	alba_address1 varchar2(90) not null,
	alba_address2 varchar2(90) not null,
	mem_num number not null,
	alba_filename varchar2(150) not null,
	constraint alba_pk primary key (alba_num),
	constraint alba_fk foreign key (mem_num) references member (mem_num)
);
create sequence alba_seq;

create table alba_fav(
	alba_num number not null,
	mem_num number not null,
	constraint alba_fav_fk1 foreign key (alba_num) references alba (alba_num),
	constraint alba_fav_fk2 foreign key (mem_num) references member (mem_num)
);

create table aplist(
	aplist_num number not null,
	alba_num number not null,
	mem_num number not null,
	aplist_title varchar2(150) not null,
	aplist_reg_date date default sysdate not null,
	alba_filename varchar2(150) not null,
	constraint aplist_pk primary key (aplist_num),
	constraint aplist_fk1 foreign key (alba_num) references alba (alba_num),
	constraint aplist_fk2 foreign key (mem_num) references member (mem_num)
);
create sequence aplist_seq;

