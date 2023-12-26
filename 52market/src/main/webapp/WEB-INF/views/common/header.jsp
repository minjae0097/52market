<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<!-- header 시작 -->
<%--<div id="main_logo">
	<h1 class="align-center"></h1>
</div> --%>
<%-- 메뉴 시작 --%>

<%-- 메뉴 끝 --%>
<!-- header 끝 -->
<div id="main_nav">
   <ul class="menu">
      <li>
      <a href="${pageContext.request.contextPath}/main/main.do">
      <img class="header-logo-img" src="${pageContext.request.contextPath}/upload/오이.png" width="50" height="50">
      </a>
      </li>
      <li>
         <a href="${pageContext.request.contextPath}/product/list.do">중고거래</a>
         <ul>
            <li><a href="${pageContext.request.contextPath}/product/list.do?product_category=1">디지털기기</a></li>
            <li><a href="${pageContext.request.contextPath}/product/list.do?product_category=2">생활/주방</a></li>
            <li><a href="${pageContext.request.contextPath}/product/list.do?product_category=3">유아동</a></li>
            <li><a href="${pageContext.request.contextPath}/product/list.do?product_category=4">의류/잡화</a></li>
            <li><a href="${pageContext.request.contextPath}/product/list.do?product_category=5">티켓/교환권</a></li>
         </ul>
      </li>
      <li><a href="${pageContext.request.contextPath}/board/list.do">동네생활</a></li>
      <li><a href="${pageContext.request.contextPath}/alba/list.do">알바</a></li>
      <li><a href="${pageContext.request.contextPath}/house/list.do">부동산직거래</a></li>
      <li><a href="${pageContext.request.contextPath}/car/list.do">중고차직거래</a></li>
      <%-- 회원 문의관리 시작 --%>
      <c:if test="${!empty user_num && user_auth != 9}">
         <li>
            <a href="${pageContext.request.contextPath}/qna/list.do">문의게시판</a>
         </li>
      </c:if>
      <%-- 회원 문의관리 끝 --%>
      <%-- 관리자 문의관리 시작 --%>
      <c:if test="${!empty user_num && user_auth == 9}">
         <li>
            <a href="${pageContext.request.contextPath}/qna/adminList.do">문의관리</a>
         </li>
      </c:if>
      <%-- 관리자 문의관리 끝 --%>
      </ul>
      
      <ul class="menu2">
      <c:if test="${!empty user_num && !empty user_photo}">
         <li class="menu-profile"><img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo"></li>
      </c:if>
      <c:if test="${!empty user_num && empty user_photo}">
         <li class="menu-profile"><img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo"></li>
      </c:if>
      <c:if test="${!empty user_num}">
         <li><a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a></li>
         <li>[${user_id}님]
         <a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
         </li>
      </c:if>
      <c:if test="${empty user_num}">
         <li><a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a></li>
         <li><a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a></li>
      </c:if>
   </ul>
</div>
