<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>apList</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
	window.onload = function(){
		let myForm = document.getElementById('search_form');
		//이벤트 연결
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
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
		<h2>지원서 목록</h2>
		<form id="search_form" action="apList.do" method="get">
			<ul>
				<li>
			<ul class="search">
				<li>
				<input type="button" value="필터">
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>		
			</li>
		</ul>
			<c:if test="${count==0}">
				<div class="result-display">
					표시할 게시물이 없습니다.
				</div>
			</c:if>
			<c:if test="${count > 0}">
				<table>
					<tr>
						<th>알바 글 제목</th>
						<th>닉네임</th>
						<th>지원서 파일</th>
						<th>지원일</th>
					</tr>
					<c:forEach var="alba" items="${apList}">
						<tr>
							<td class="align-center"><a href="detailAlba.do?alba_num=${alba.alba_num}">${alba.alba_title}</a></td>
							<td class="align-center">${member.mem_nickname}</td>
							<td class="align-center">${alba.alba_filename}</td>
							<td class="align-center">${aplist.apList_reg_date}</td>
						</tr>
					</c:forEach>
				</table>
				<hr size="1" width="100%">
				<div class="align-center">${page}</div>
			</c:if>
			</form>
		</div>
	</div>
</body>
</html>