<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
table.table {
	border-collapse: separate;
	border-spacing: 0;
}
.bg-red {
	background-color: red solid 1px !important;
}
</style>
<title>DirEngine - Free Bootstrap 4 Template by Colorlib</title>
<script
	src="${pageContext.request.contextPath}/resources/script/jquery-3.6.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Alex+Brush"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/open-iconic-bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/animate.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/owl.carousel.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/owl.theme.default.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/magnific-popup.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/aos.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/ionicons.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery.timepicker.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/flaticon.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/icomoon.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>

	<%@ include file="../views/top.jsp"%>


	<div class="hero-wrap js-fullheight"
		style="background-image: url('${pageContext.request.contextPath}/resources/images/bg_2.jpg');">
		<div class="overlay"></div>
		<div class="container">
			<div
				class="row no-gutters slider-text js-fullheight align-items-center justify-content-center"
				data-scrollax-parent="true">
				<div class="col-md-9 ftco-animate text-center"
					data-scrollax=" properties: { translateY: '70%' }">
					<p class="breadcrumbs"
						data-scrollax="properties: { translateY: '30%', opacity: 1.6 }">
						<span class="mr-2"><a href="index.html">Home</a></span> <span>매물등록</span>
					</p>
					<h1 class="mb-3 bread"
						data-scrollax="properties: { translateY: '30%', opacity: 1.6 }">신규
						매물등록</h1>
				</div>
			</div>
		</div>
	</div>

	<div class="container" align="left">
		<h1>주의 사항</h1>
		<pre>
· 일반적인 형태의 주거용 전월세 매물만 내놓을 수 있습니다.
  1) 한 번의 방등록으로 반경 5km이내 모든 중개사무소에 방 정보가 노출되어 중개가 시작됩니다.
  2) 내놓은 방은 검증된 공인중개사가 1,900만 직방 이용자들에게 중개해 드립니다.
  3) 방을 내놓는 데에는 별도의 비용이 들지 않습니다. (거래 성사 시 법정 중개보수가 발생합니다)
· 등록된 매물은 30일 후에 중개가 종료되며, 중개사에게 방 내놓기·관리 메뉴에서 재등록이 가능합니다.
· 내놓은 방의 정보가 정확하지 않거나 가이드에 맞지 않는 경우, 중개가 종료되고 정보수정을 요청드릴 수 있습니다.
· 중개사무소는 개인 회원으로 중개사무소에 방내놓기 등록을 할 수 없고, 중개사무소 회원으로 가입해야 합니다. 
	[중개사무소 가입신청하기]
· 아래와 같은 매물은 등록이 불가하며, 중개가 종료될 수 있습니다.
  (매매, 분양, 장기전세 / 고시텔, 원룸텔, 셰어하우스, 하숙, 게스트하우스, 숙박업소 등)
  </pre>
	</div>

	<div class="container">
		<table class="table">
			<thead align="left">
				<tr>
					<th colspan="3">상세 정보</th>
				</tr>
			</thead>
			<tbody align="left">
				<tr>
					<th style="background-color: #dedede;">보증금</th>
					<td><input type="text">만원</td>
				</tr>
				<tr>
					<th style="background-color: #dedede;">월세</th>
					<td><input type="text">만원</td>
				</tr>
				<tr>
					<th style="background-color: #dedede;">방구조</th>
					<td><select name="romm_type">
							<option value="">선택하세요</option>
							<option value="오픈형 원룸">오픈형 원룸</option>
							<option value="복층형 원룸">복층형 원룸</option>
							<option value="기타">기타</option>
					</select></td>
				</tr>
				<tr>
					<th style="background-color: #dedede;">관리비</th>
					<td><input type="text">만원</td>
				</tr>
				<tr>
					<th style="background-color: #dedede;">크기</th>
					<td>전용면적 : <input type="text">m<sup>2</sup>=<input type="text"><sup>P</sup>
					<br>계약면적 : <input type="text">m<sup>2</sup>=<input type="text"><sup>P</sup>
					</td>					
				</tr>
				<tr>
					<th style="background-color: #dedede;">층수</th>
					<td>건물 층 수 : <select name="romm_type">
							<option value="">선택하세요</option>
							<option value="1층">1층</option>
							<option value="이상">...</option>
					</select>
					 / 해당 층 : <select name="romm_type">
							<option value="">선택하세요</option>
							<option value="1층">1층</option>
							<option value="이상">...</option>
					</select>
					</td>
				</tr>
			</tbody>

			<tbody align="left">
				<th>건물 유형</th>
				<td><button type="button" class="btn btn-info">원룸</button></td>
				<td>오피스텔</td>
			</tbody>

		</table>
	</div>

	<div class="container p-3 my-3 bg-dark text-white"></div>

	<div class="container p-3 my-3 bg-primary text-white"></div>

	<footer class="ftco-footer ftco-bg-dark ftco-section">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<p>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;
						<script>
							document.write(new Date().getFullYear());
						</script>
						All rights reserved | This template is made with <i
							class="icon-heart" aria-hidden="true"></i> by <a
							href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</p>
				</div>
			</div>
		</div>
	</footer>



	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>


	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.waypoints.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.stellar.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/owl.carousel.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.magnific-popup.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/aos.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.animateNumber.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.timepicker.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/scrollax.min.js"></script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/google-map.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>