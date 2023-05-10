package com.minibank.account.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "계좌")
public class Account {

	@Schema(description = "계좌번호(중복 불가)")	
	private String acntNo;
	@Schema(description = "고객 ID")	
	private String cstmId;
	@Schema(description = "고객 이름")	
	private String cstmNm;
	@Schema(description = "계좌 이름")	
	private String acntNm;
	@Schema(description = "최초 생성일자")	
	private String newDtm;
	@Schema(description = "최초 입금액")	
	private Long acntBlnc;

	@Builder
	public Account(String acntNo, String cstmId, String cstmNm, String acntNm, String newDtm, Long acntBlnc) {
		this.acntNo = acntNo;
		this.cstmId = cstmId;
		this.cstmNm = cstmNm;
		this.acntNm = acntNm;
		this.newDtm = newDtm;
		this.acntBlnc = acntBlnc;
	}

	public static Account ofAcntNo(String acntNo) {
		return Account.builder()
				  .acntNo(acntNo).build();
	}

	public static Account ofCstmId(String cstmId) {
		return Account.builder()
				  .cstmId(cstmId).build();
	}

	public static Account of(String acntNo, Long acntBlnc) {
		return Account.builder()
				  .acntNo(acntNo)
				  .acntBlnc(acntBlnc).build();
	}
}