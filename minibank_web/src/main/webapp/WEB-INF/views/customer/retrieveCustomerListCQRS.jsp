<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container">
	<br />
	<h2>
		<span>고객 목록 조회(CQRS)</span>
	</h2>
    
	<form id="form1" onsubmit="return false;">
		<table class="global_table" cellpadding="0" cellspacing="0">
			<colgroup>
				<col style="width:12%;" />
				<col style="width:15%;" />
				<col style="width:12%;" />
				<col style="width:15%;" />
				<col style="width:12%;" />
				<col style="width:15%;" />
				<col/>
			</colgroup>
			<tr>
				<th class="width135">고객ID</th>
				<td>
					<input class="txt" id="cstmId" name="cstmId" type="text" value="" />
				</td>
				<th class="width135">이름</th>
				<td>
					<input class="txt" id="cstmNm" name="cstmNm" type="text" value="" />
				</td>
				<th class="width135">계좌번호</th>
				<td>
					<input class="txt" id="acntNo" name="acntNo" type="text" value="" />
				</td>
				<td>
					<input id="btnSearch" type="button" value="조회" class="search_btn"/>
				</td>
			</tr>
		</table>
	</form>			
	<br />

   	<h3>고객 정보</h3>  
	<table class="global_table" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th width="12%">고객ID</th>
				<th width="12%">이름</th>
				<th width="12%">전화번호</th>
				<th width="16%">1일 이체 한도</th>
				<th width="16%">1회 이체 한도</th>
				<th width="10%">보유 계좌 수</th>
				<th>총 잔액</th>
			</tr>
		</thead>
		<tbody id="customerList">
			<tr>
				<td colspan="7">조회된 값이 없습니다</td>
			</tr>
		</tbody>
	</table>
</div>

<script type="text/javascript"
		src="<%=request.getContextPath()%>/resource/js/customer/retrieveCustomerListCQRS.js"></script>