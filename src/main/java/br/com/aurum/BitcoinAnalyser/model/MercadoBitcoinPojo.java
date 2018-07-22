package br.com.aurum.BitcoinAnalyser.model;

import java.math.BigDecimal;

/**
 * Pojo contendo as informações de retorno do JSON do serviço do mercadobitcoin
 */
public class MercadoBitcoinPojo  {

	private String date;
	private String type;
	private BigDecimal price;
	private BigDecimal amount;
	private BigDecimal tid;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getTid() {
		return tid;
	}
	public void setTid(BigDecimal tid) {
		this.tid = tid;
	}
	
}
