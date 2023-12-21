<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지원서 목록(사업자)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
   window.onload = function(){
      let myForm = document.getElementById('search_form');
      myForm.onsubmit = function(){
         let keyword = document.getElementById('keyword');
         if(keyword.value.trim()==''){
            alert('검색어를 입력하세요');
            keyword.value='';
            keyword.focus();
            return false;
         }
      };
   };
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="content-main">
		<h2>지원서 목록(사업자)</h2>
		<form action="aplistForB.do" method="get" id="search_form">
            <ul class="search">
               <li>
                  <select name="keyfield" id="keyfield">
                     <option value="1" <c:if test="${param.keyfield ==1}">selected</c:if>>글 제목</option>
                  </select>
               </li>
               <li>
                  <input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
               </li>
               <li>
                  <input type="submit" value="찾기">
               </li>
            </ul>
         </form>
		<div class="list-space align-right">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		<c:if test="${count ==0}">
		<div class="result-display">
			표시할 목록이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>구인 글 제목</th>
				<th>닉네임</th>
				<th>지원서 파일</th>
				<th>지원일</th>
			</tr>
			<c:forEach var="alba" items="${list}">
			<tr>
				<td>${alba.alba_title}</td>
				<td>${alba.mem_nickname}</td>
				<td><a href="${pageContext.request.contextPath}/upload/${alba.alba_filename}">${alba.alba_filename}</a></td>
				<td>${alba.aplist_reg_date}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>