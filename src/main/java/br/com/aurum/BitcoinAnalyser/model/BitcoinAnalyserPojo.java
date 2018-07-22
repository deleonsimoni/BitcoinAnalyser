package br.com.aurum.BitcoinAnalyser.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Pojo contendo as informações de retorno do JSON
 */
public class BitcoinAnalyserPojo implements Serializable {

	
	private static final long serialVersionUID = 7389848133724129186L;
	private List<BigDecimal> maiores_vendas;
	private List<BigDecimal> maiores_compras;
	private BigDecimal media_compra;
	private BigDecimal media_venda;
	private BigDecimal mediana_compra;
	private BigDecimal mediana_venda;
	private BigDecimal desvio_padrao_venda;
	private BigDecimal desvio_padrao_compra;
	
	public List<BigDecimal> getMaiores_vendas() {
		return maiores_vendas;
	}
	public void setMaiores_vendas(List<BigDecimal> maiores_vendas) {
		this.maiores_vendas = maiores_vendas;
	}
	public List<BigDecimal> getMaiores_compras() {
		return maiores_compras;
	}
	public void setMaiores_compras(List<BigDecimal> maiores_compras) {
		this.maiores_compras = maiores_compras;
	}
	public BigDecimal getMedia_compra() {
		return media_compra;
	}
	public void setMedia_compra(BigDecimal media_compra) {
		this.media_compra = media_compra;
	}
	public BigDecimal getMedia_venda() {
		return media_venda;
	}
	public void setMedia_venda(BigDecimal media_venda) {
		this.media_venda = media_venda;
	}
	public BigDecimal getMediana_compra() {
		return mediana_compra;
	}
	public void setMediana_compra(BigDecimal mediana_compra) {
		this.mediana_compra = mediana_compra;
	}
	public BigDecimal getMediana_venda() {
		return mediana_venda;
	}
	public void setMediana_venda(BigDecimal mediana_venda) {
		this.mediana_venda = mediana_venda;
	}
	public BigDecimal getDesvio_padrao_venda() {
		return desvio_padrao_venda;
	}
	public void setDesvio_padrao_venda(BigDecimal desvio_padrao_venda) {
		this.desvio_padrao_venda = desvio_padrao_venda;
	}
	public BigDecimal getDesvio_padrao_compra() {
		return desvio_padrao_compra;
	}
	public void setDesvio_padrao_compra(BigDecimal desvio_padrao_compra) {
		this.desvio_padrao_compra = desvio_padrao_compra;
	}
	
}
