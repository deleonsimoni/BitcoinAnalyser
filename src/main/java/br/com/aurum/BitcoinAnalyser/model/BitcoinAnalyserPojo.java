package br.com.aurum.BitcoinAnalyser.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pojo contendo as informações de retorno do JSON
 */
public class BitcoinAnalyserPojo implements Serializable {

	
	private static final long serialVersionUID = 7389848133724129186L;
	
	@JsonProperty("maiores_vendas")
	private List<BigDecimal> topSell;
	
	@JsonProperty("maiores_compras")
	private List<BigDecimal> topBuy;
	
	@JsonProperty("media_compra")
	private BigDecimal averageBuy;
	
	@JsonProperty("media_venda")
	private BigDecimal averageSell;
	
	@JsonProperty("mediana_compra")
	private BigDecimal medianBuy;
	
	@JsonProperty("mediana_venda")
	private BigDecimal medianSell;
	
	@JsonProperty("desvio_padrao_venda")
	private BigDecimal standardDeviationSell;
	
	@JsonProperty("desvio_padrao_compra")
	private BigDecimal standardDeviationBuy;
	
	public List<BigDecimal> getTopSell() {
		return topSell;
	}

	public void setTopSell(List<BigDecimal> topSell) {
		this.topSell = topSell;
	}

	public List<BigDecimal> getTopBuy() {
		return topBuy;
	}

	public void setTopBuy(List<BigDecimal> topBuy) {
		this.topBuy = topBuy;
	}

	public BigDecimal getAverageBuy() {
		return averageBuy;
	}

	public void setAverageBuy(BigDecimal averageBuy) {
		this.averageBuy = averageBuy;
	}

	public BigDecimal getAverageSell() {
		return averageSell;
	}

	public void setAverageSell(BigDecimal averageSell) {
		this.averageSell = averageSell;
	}

	public BigDecimal getMedianBuy() {
		return medianBuy;
	}

	public void setMedianBuy(BigDecimal medianBuy) {
		this.medianBuy = medianBuy;
	}

	public BigDecimal getMedianSell() {
		return medianSell;
	}

	public void setMedianSell(BigDecimal medianSell) {
		this.medianSell = medianSell;
	}

	public BigDecimal getStandardDeviationSell() {
		return standardDeviationSell;
	}

	public void setStandardDeviationSell(BigDecimal standardDeviationSell) {
		this.standardDeviationSell = standardDeviationSell;
	}

	public BigDecimal getStandardDeviationBuy() {
		return standardDeviationBuy;
	}

	public void setStandardDeviationBuy(BigDecimal standardDeviationBuy) {
		this.standardDeviationBuy = standardDeviationBuy;
	}
}
