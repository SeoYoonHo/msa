package com.minibank.customer.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Customer {

	private String cstmId;
	private String cstmNm;
	private String cstmAge;
	private String cstmGnd;
	private String cstmPn;
	private String cstmAdr;

	@Builder
	public Customer(String cstmId, String cstmNm, String cstmAge, String cstmGnd, String cstmPn, String cstmAdr,
			Long oneTmTrnfLmt, Long oneDyTrnfLmt) {
		super();
		this.cstmId = cstmId;
		this.cstmNm = cstmNm;
		this.cstmAge = cstmAge;
		this.cstmGnd = cstmGnd;
		this.cstmPn = cstmPn;
		this.cstmAdr = cstmAdr;
	}
}
