create table carlist(
	carlist_num number not null,
	carlist_title varchar2(150) not null,
	carlist_content clob not null,
	carlist_reg_date date default sysdate not null,
	carlist_modify_date date,
	carlist_status number(1) default 0 not null,
	constraint carlist_pk primary key (carlist_num),
	constraint carlist_detail_fk  foreign key (carlist_num) references carlist_detail (carlist_num)
);

create table carlist_detail(
	carlist_num number not null,
	car_seller number not null,
	car_buyer number,
	car_type number(1) not null,
	car_fuel number(1) not null,
	car_price number(12) not null,
	car_model_year number(4) not null,
	car_distance number(7) not null,
	car_transmission number(1) not null,
	car_origin number(1) not null,
	car_image varchar2(150) not null,
	car_tradedate date,
	constraint carlist_detail_pk primary key (carlist_num),
	constraint carlist_detail_fk1  foreign key (car_seller) references member (mem_num),
	constraint carlist_detail_fk2  foreign key (car_buyer) references member (mem_num)
);

create sequence carlist_seq;

create table car_fav(
	carlist_num number not null,
	mem_num number not null,
	constraint car_fav_fk1  foreign key (carlist_num) references carlist (carlist_num),
	constraint car_fav_fk2  foreign key (car_buyer) references member (mem_num)
);

