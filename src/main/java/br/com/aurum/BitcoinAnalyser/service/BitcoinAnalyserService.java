package br.com.aurum.BitcoinAnalyser.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.aurum.BitcoinAnalyser.model.BitcoinAnalyserPojo;
import br.com.aurum.BitcoinAnalyser.model.MercadoBitcoinPojo;

@Stateless
public class BitcoinAnalyserService {

	@Inject
	private Logger log;

	public static List<MercadoBitcoinPojo> callMercadoBitcoin() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet getRequest = new HttpGet("https://www.mercadobitcoin.net/api/BTC/trades/1501871369/1501891200/");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);

			// Verificando código de erro retornado
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}

			// Now pull back the response object
			HttpEntity httpEntity = response.getEntity();
			String apiOutput = EntityUtils.toString(httpEntity);

			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<MercadoBitcoinPojo>>() {
			}.getType();
			return gson.fromJson(apiOutput, listType);
		} finally {
			// Important: Close the connect
			httpClient.getConnectionManager().shutdown();
		}
	}

	public BitcoinAnalyserPojo getBitcoinAnalyser() throws Exception {
		log.info(" ** Consumindo Serviço mercadobitcoin ");
		List<MercadoBitcoinPojo> listaMercadoBitcoin = callMercadoBitcoin();
		log.info(" ** Serviço mercadobitcoin consumido com sucesso / " + listaMercadoBitcoin.size() + " dados ** ");

		log.info(" ** Trabalhando os dados ");
		BitcoinAnalyserPojo bitcoinAurum = refineData(listaMercadoBitcoin);
		log.info(" ** Dados trabalhados com sucesso ** ");

		return bitcoinAurum;
	}

	private BitcoinAnalyserPojo refineData(List<MercadoBitcoinPojo> listaMercadoBitcoin) {

		BitcoinAnalyserPojo bitcoinAurumPojo = new BitcoinAnalyserPojo();

		BigDecimal averageSell = BigDecimal.ZERO;
		BigDecimal averageBuy = BigDecimal.ZERO;

		BigDecimal totalSell = BigDecimal.ZERO;
		BigDecimal totalBuy = BigDecimal.ZERO;

		List<BigDecimal> valuesSell = new ArrayList<BigDecimal>();
		List<BigDecimal> valuesBuy = new ArrayList<BigDecimal>();

		// Separar os dados de compra e vendas calculando o total de cada e a média
		for (MercadoBitcoinPojo mercadoBitCoin : listaMercadoBitcoin) {
			switch (mercadoBitCoin.getType()) {
			case "sell":
				totalSell = totalSell.add(BigDecimal.ONE).setScale(5, RoundingMode.HALF_EVEN);
				averageSell = averageSell.add(mercadoBitCoin.getPrice()).setScale(5, RoundingMode.HALF_EVEN);
				valuesSell.add(mercadoBitCoin.getPrice());
				break;
			case "buy":
				totalBuy = totalBuy.add(BigDecimal.ONE).setScale(5, RoundingMode.HALF_EVEN);
				averageBuy = averageBuy.add(mercadoBitCoin.getPrice()).setScale(5, RoundingMode.HALF_EVEN);
				valuesBuy.add(mercadoBitCoin.getPrice());
				break;
			}
		}

		// Ordenando os valores de compra e venda
		Collections.sort(valuesBuy);
		Collections.sort(valuesSell);

		// Calculando a média
		bitcoinAurumPojo.setAverageBuy(averageBuy.divide(totalBuy, 3, BigDecimal.ROUND_HALF_UP));
		bitcoinAurumPojo.setAverageSell(averageSell.divide(totalSell, 3, BigDecimal.ROUND_HALF_UP));

		// Calculando mediana
		bitcoinAurumPojo.setMedianSell(calculeMedian(valuesSell).setScale(3, RoundingMode.HALF_EVEN));
		bitcoinAurumPojo.setMedianBuy(calculeMedian(valuesBuy).setScale(3, RoundingMode.HALF_EVEN));

		// Maiores vendas e compras
		bitcoinAurumPojo.setTopBuy(topFiveList(valuesBuy));
		bitcoinAurumPojo.setTopSell(topFiveList(valuesSell));

		// Calculando desvio padrao
		bitcoinAurumPojo.setStandardDeviationBuy(calculeStandardDeviation(valuesBuy, averageBuy));
		bitcoinAurumPojo.setStandardDeviationSell(calculeStandardDeviation(valuesSell, averageSell));

		return bitcoinAurumPojo;
	}

	private BigDecimal calculeStandardDeviation(List<BigDecimal> values, BigDecimal average) {

		BigDecimal standardDeviation = BigDecimal.ZERO;
		for (BigDecimal value : values) {
			BigDecimal aux = value.subtract(average);
			standardDeviation = standardDeviation.add(aux.multiply(aux));
		}
		return BigDecimal.valueOf((Math.sqrt(standardDeviation.doubleValue() / (values.size() - 1)))).setScale(3, RoundingMode.HALF_EVEN);
	}
	
	private BigDecimal calculeMedian(List<BigDecimal> values) {

		if (values.size() % 2 == 0) {
			return values.get(((values.size() / 2) + (values.size() / 2 + 1)) / 2);
		} else {
			return values.get((values.size() + 1) / 2);
		}
	}

	private List<BigDecimal> topFiveList(List<BigDecimal> values) {

		List<BigDecimal> topFive = new ArrayList<BigDecimal>();

		for (int i = 0; topFive.size() < 5; i++) {
			if (i == 0 || values.get(i).compareTo(topFive.get(topFive.size() - 1)) != 0) {
				topFive.add(values.get(i));
			} else {
				continue;
			}
		}
		return topFive;
	}

}
