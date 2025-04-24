package com.trading.webhook.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OperationsDto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String dealid;
	private String dealreference;
	private String affecteddeals;
	private String epic;
	private String direction;
	private String state;
	private BigDecimal orderSize;
	private Boolean guaranteedstop;
	private BigDecimal stoplevel;
	private BigDecimal profitlevel;
	private String openDate;
	private String closeDate;
	private String account;
	
	
	public OperationsDto(Integer id, String dealid, String dealreference, String affecteddeals, String epic,
			String direction, String state, BigDecimal orderSize, Boolean guaranteedstop, BigDecimal stoplevel,
			BigDecimal profitlevel, Date openDate, Date closeDate, String account) {
		super();
		this.id = id;
		this.dealid = dealid;
		this.dealreference = dealreference;
		this.affecteddeals = affecteddeals;
		this.epic = epic;
		this.direction = direction;
		this.state = state;
		this.orderSize = orderSize;
		this.guaranteedstop = guaranteedstop;
		this.stoplevel = stoplevel;
		this.profitlevel = profitlevel;
		this.openDate = new SimpleDateFormat("dd-MM-yyyy hh:mm").format(openDate);
		this.closeDate = new SimpleDateFormat("dd-MM-yyyy hh:mm").format(closeDate);
		this.account = account;
	}
	
	

	
}
