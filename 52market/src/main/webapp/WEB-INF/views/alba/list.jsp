<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알바</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/KJY.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//좋아요 선택 여부와 선택한 총 개수 읽기
	function selectFav(){
		$.ajax({
			url:'getAlbaFav.do',
			type:'post',
			data:{alba_num:$('#output_fav').attr('data-num')},
			dataType:'json',
			success:function(param){
				displayFav(param);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}	
		});
	}
	
});
</script>
</head>
<body>
<div class="page-main1">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<a href="${pageContext.request.contextPath}/alba/list.do"><img src="${pageContext.request.contextPath}/images/알바배너.png" width="960"></a>
		<div class="content">
			<h2>알바 최신글</h2>
		<form id="search_form" action="list.do" method="get" class="align-right">
			<ul>
				<li>
					<ul class="search">
						<li>
							<select id="keyfield1" name="keyfield">
								<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
								<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
							<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>제목+내용</option>
							</select>
						</li>
						<li>
							<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
							<input type="submit" value="검색">
						</li>
					</ul>		
				</li>
			</ul>
		</form>
		<div class="image-space">
			<c:forEach var="alba" items="${albaList}">
				<div class="horizontal-area99">
					<span class="horizontal-area">
						<a href="${pageContext.request.contextPath}/alba/detailAlba.do?alba_num=${alba.alba_num}">
							<img src="${pageContext.request.contextPath}/upload/${alba.alba_photo}">
						</a>
					</span>
					<div class="list-horizontal">
						<a href="${pageContext.request.contextPath}/alba/detailAlba.do?alba_num=${alba.alba_num}">
							<span style="font-size: 20pt;"><br>
							${alba.alba_title}</span><br>
							<span>${alba.alba_address1}</span>
							<br>
							<span style="font-size: 10pt;">
							조회 ${alba.alba_hit}
							관심 ${alba.alba_fav}
							지원자수 ${alba.apcount}
							</span>
						</a>
					</div>
				</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
			<div class="align-center">${page}</div>
		</div>
	</div>
	<div class="align-right">
		<c:if test="${user_auth==3}">
		<input type="button" value="글쓰기" onclick="location.href='insertAlbaForm.do'">
		</c:if>
	</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html> 