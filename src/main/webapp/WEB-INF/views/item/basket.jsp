<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic+Coding|Nanum+Pen+Script" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
<fmt:requestEncoding value="UTF-8" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param value="" name="pageTitle"/>
</jsp:include>
<style>
.productList-container{
	position:relative;
	margin-left:180px;
}
.mp-title>h2{
     font-family: 'Nanum Pen Script', cursive;


}
.buy-container{
	float: right;
/* 	margin-left:125%; */

}
#buyButton{

   float: right;
}

.mp-container{
font-family: 'Nanum Gothic', sans-serif;
}

</style>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/mypage.css" />
<div class="mypage-container">
	<div class="real-content-container">
		<div class="mp-container">
		 <div class="mp-title">
			<h2>장바구니에 담긴 물품을 구매해 보세요!</h2>
			 </div>
			<div class="mypage-body">
		<table class="table table-striped">
    <thead>
    <tr>    
     <!--  <th scope="col">장바구니번호</th> -->
      <th scope="col">물품번호</th>
      <th scope="col">상품이름</th>
      <th scope="col">상품정보</th>
      <th scope="col">상품금액</th>
      <th scope="col">수량</th>
      
      <th scope="col">취소</th>
    </tr>
  </thead>
  <tbody>
    <c:if test="${ empty list }">
		<tr>
			<td colspan="8">장바구니에 물품이 없습니다.</td>
		</tr>
	</c:if>
		
		<c:if test="${not empty list }">
			<c:forEach items="${list}" var="p">
			<tr>
				<%-- <td>${p.SEQ_BASKET_NO}</td>	 --%>			
				<td>${p.SEQ_PRODUCT_NO}</td>	
				<td>${p.PRODUCT_NAME}</td>	
				<td>${p.PRODUCT_DESCRIPTION}</td>		
				<td>${p.PRODUCT_PRICE}</td>	
				<td>${p.PRODUCT_STOCK}</td>
			<!-- 	<td><input id="basketSelect" type="checkbox" /></td> -->
				<td>
				 <button type="button" class="btn btn-danger" onclick="deleteBasket('${p.SEQ_BASKET_NO}')">취소</button>
				 </td>	
			</tr>			
			</c:forEach>
		</c:if>
  
  </tbody>
</table>
<form name="basketDelFrm" action="deleteBasket.do" method="post">
	<input type="hidden" name="no" value="${p.SEQ_BASKET_NO}"/>
</form>

		      <div class="buy-container">
		      
		 	 	<h4>상품구매 금액 합계:원</h4>
		 	
		     
		 	 	<br />
		      <button type="button" id="buyButton" class="btn btn-primary btn-lg">구매하기</button>
		   </div>
		  </div>
		</div>	
	</div>
</div>
<script>

function deleteBasket(no){
    var frm = document.basketDelFrm;
    frm.no.value = no;
    frm.submit();
}

</script>



 




<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>