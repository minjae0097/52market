create table albalist(
	albalist_num number not null,
	alba_title varchar2(150) not null,
	alba_content clob not null,
	alba_reg_date date default sysdate not null,
	alba_modify_date date default sysdate not null,
	alba_photo1 varchar2(60),
	alba_photo2 varchar2(60),
	alba_ip varchar2(40) not null,
	alba_location varchar2(150),
	mem_num number not null,
	alba_filename varchar2(150) not null,
	constraint albalist_pk primary key (albalist_num),
	constraint albalist_fk foreign key (mem_num) references member (mem_num)
);
create sequence albalist_seq;

create table alba_fav(
	albalist_num number not null,
	mem_num number not null,
	constraint alba_fav_fk1 foreign key (albalist_num) references albalist (albalist_num),
	constraint alba_fav_fk2 foreign key (mem_num) references member (mem_num)
);

create table aplist(
	aplist_num number not null,
	albalist_num number not null,
	mem_num number not null,
	aplist_title varchar2(150) not null,
	aplist_reg_date date default sysdate not null,
	alba_filename varchar2(150) not null
	constraint aplist_pk primary key (aplist_num),
	constraint aplist_fk1 foreign key (albalist_num) references albalist (albalist_num),
	constraint aplist_fk2 foreign key (mem_num) references member (mem_num)
);
create sequence aplist_seq;

