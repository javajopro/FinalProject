<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="" name="pageTitle"/>
</jsp:include>
<style>
.content-container {
    width: 1060px;
    margin-left: 110px;
    padding-top: 48px;
}
.sidebar{
    vertical-align: top;
    display: inline-block;
    width: 144px;
    margin-bottom: 36px;
   	margin-right: 5%;
}
.sidebar1{
	color: #666666;
    font-size: 25px;
    font:bolder;
    line-height: 27px;
    border-bottom: 1px solid #ececec;
    padding-bottom: 15px;
}
.sidebar2{
	border: 1px solid #ececec;
}
.sidebar3{
	border: 1px solid #ececec;
}
.sidebar4{
    border: 1px solid #ececec;
}
.sidebar5{
    border: 1px solid #ececec;
}
.sidebar a{
	display: block;
	padding-left: 15px;
    font-size: 14px;
    color: #1f1f1f;
}
.headline{
	font-size: 20px;
    line-height: 23px;
    padding-bottom: 20px;
    border-bottom: 2px solid #ececec;
    margin-bottom: 2%;
}
.content{
	display: inline-block;
	width:80%;
}
#news{

	border-bottom:2px solid #ececec;
	height: 50px;
	width:100%;
}
.newscontent{
	border-bottom:2px solid #ececec;
	background: #e9e9e9;
}

</style>

<div class="content-container">	
		<div class="sidebar">
			<div class="sidebar1">
				고객센터
			</div>
			<div class="sidebar2" id="sidebar">
				<a href="${pageContext.request.contextPath}/customercenter/ccintro.do">겟잇 소개</a>
			</div>
			<div class="sidebar3" id="sidebar">
				<a href="${pageContext.request.contextPath}/customercenter/ccnews.do">겟잇 소식</a>
			</div>
			<div class="sidebar4" id="sidebar">
				<a href="${pageContext.request.contextPath}/customercenter/ccinquiry.do">1:1 문의</a>
			</div>
			<div class="sidebar5" id="sidebar">
				<a href="${pageContext.request.contextPath}/customercenter/ccqna.do">자주 묻는 질문</a>
			</div>
		</div>
		<div class="content">
			<div class="headline">
				여기 소식 헤드라인
			</div>
			<section>
			
			<c:forEach items="${list}" var="b">
				<div class="news1" id="news" data-toggle="collapse" data-target="#${b.SEQ_BOARD_NO}" aria-expanded="false" aria-controls="collapseExample">	
				${b.BOARD_TITLE} 
				${b.BOARD_DATE}
				</div>
				<div class="collapse" id="${b.SEQ_BOARD_NO}">
				  <div class="newscontent">
					${b.BOARD_CONTENT}
				  </div>
				</div>
			</c:forEach>
				
				
			</section>
		</div>
</div>		
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>