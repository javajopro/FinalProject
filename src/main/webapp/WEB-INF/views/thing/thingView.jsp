<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="UTF-8" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="" name="pageTitle"/>
</jsp:include>
<div class="sell-title" style="text-align: center;font-style: oblique;font-size: xx-large;">먼저, 판매 방법을 선택하세요.</div>
<div class="sell-title" style="text-align: center;font-style: oblique;">겟잇의 모든 중고 거래는 안전거래입니다!</div>

<div style="display: inline-flex; text-align: center; margin-left: 32%;">  
<div class="card" style="width: 18rem;">
  <img class="card-img-top" src="${pageContext.request.contextPath }/resources/images/대신.PNG" alt="Card image cap">
  <div class="card-body">
    <h5 class="card-title">겟잇 베이직</h5>
    <p class="card-text"></p>  
  </div>
  <ul class="list-group list-group-flush">
    <li class="list-group-item">한번에 1개 제품 등록</li>
    <li class="list-group-item">컨시지어가 판매가격 제안</li>
    <li class="list-group-item">구매자 응대 X</li>
    <li class="list-group-item">무료 배송 지원</li>
    <li class="list-group-item">안 팔리면 겟잇이 매입</li>
  </ul>
  <div class="card-body">
    <button type="button" class="btn btn-primary">판매하기</button>
  </div>
</div> 
<div class="card" style="width: 18rem;">
  <img class="card-img-top" src="${pageContext.request.contextPath }/resources/images/직접.PNG"  alt="Card image cap">
  <div class="card-body">
    <h5 class="card-title">직접 판매</h5>
    <p class="card-text"></p>
  </div>
  <ul class="list-group list-group-flush">
    <li class="list-group-item">한번에 1개 제품 등록</li>
    <li class="list-group-item">판매가격 직접 입력</li>
    <li class="list-group-item">구매자 응대 O</li>
    <li class="list-group-item">편의점 택배 서비스 이용</li>
    <li class="list-group-item">매입 X</li> 
  </ul>
  <div class="card-body">
    <button type="button" class="btn btn-primary">판매하기</button> 
  </div>
</div> 

</div> 
<hr /> 
<div class="sell-title" style="text-align: center;font-style: oblique;font-size: xx-large;">나에게 맞는 판매 방법은?</div> 
<div class="jumbotron">
  <h1 class="display-4">안녕! 겟잇 베이직</h1>
  <hr class="my-4">
  <p class="lead">1. 팔 물건은 있는데, 판매하기가 귀찮다.</p> 
  <p class="lead">2. 안 팔리는 물건, 매입해줬으면 좋겠다.</p> 
  <a class="btn btn-primary btn-lg" href="#" role="button">겟잇 베이직으로 판매하기</a>
</div>
<div class="jumbotron">
  <h1 class="display-4">안녕! 직접 판매</h1>
  <hr class="my-4">
  <p class="lead">1. 그냥 구매자랑 직접 거래하고 싶다.</p> 
  <p class="lead">2. 네고, 직거래 없이 안전거래 하고 싶다.</p> 
  <a class="btn btn-primary btn-lg" href="#" role="button">직접 판매하기</a> 
</div>


<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>