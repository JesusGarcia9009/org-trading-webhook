package com.trading.webhook.entity;
// Generated 10-08-2023 09:41:42 by Hibernate Tools 4.3.6.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Operations generated by hbm2java
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "operations", schema = "public")
public class Operations implements java.io.Serializable {

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
	private Date openDate;
	private Date closeDate;
	private String account;

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "dealid", nullable = false, length = 3000)
	public String getDealid() {
		return this.dealid;
	}

	public void setDealid(String dealid) {
		this.dealid = dealid;
	}

	@Column(name = "dealreference", nullable = false, length = 3000)
	public String getDealreference() {
		return this.dealreference;
	}

	public void setDealreference(String dealreference) {
		this.dealreference = dealreference;
	}

	@Column(name = "affecteddeals", nullable = false, length = 3000)
	public String getAffecteddeals() {
		return this.affecteddeals;
	}

	public void setAffecteddeals(String affecteddeals) {
		this.affecteddeals = affecteddeals;
	}

	@Column(name = "epic", nullable = false, length = 100)
	public String getEpic() {
		return this.epic;
	}

	public void setEpic(String epic) {
		this.epic = epic;
	}

	@Column(name = "direction", nullable = false, length = 100)
	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name = "state", nullable = false, length = 100)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "order_size", nullable = false, precision = 11, scale = 4)
	public BigDecimal getOrderSize() {
		return this.orderSize;
	}

	public void setOrderSize(BigDecimal orderSize) {
		this.orderSize = orderSize;
	}

	@Column(name = "guaranteedstop")
	public Boolean getGuaranteedstop() {
		return this.guaranteedstop;
	}

	public void setGuaranteedstop(Boolean guaranteedstop) {
		this.guaranteedstop = guaranteedstop;
	}

	@Column(name = "stoplevel", precision = 11, scale = 4)
	public BigDecimal getStoplevel() {
		return this.stoplevel;
	}

	public void setStoplevel(BigDecimal stoplevel) {
		this.stoplevel = stoplevel;
	}

	@Column(name = "profitlevel", precision = 11, scale = 4)
	public BigDecimal getProfitlevel() {
		return this.profitlevel;
	}

	public void setProfitlevel(BigDecimal profitlevel) {
		this.profitlevel = profitlevel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "open_date", nullable = false, length = 29)
	public Date getOpenDate() {
		return this.openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_date", length = 29)
	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name = "account", nullable = false, length = 100)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
