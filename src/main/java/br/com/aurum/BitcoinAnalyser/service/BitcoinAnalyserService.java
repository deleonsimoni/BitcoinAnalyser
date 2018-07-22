/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class BitcoinAnalyserService {

    @Inject
    private Logger log;

    public static List<MercadoBitcoinPojo>  callMercadoBitcoin() throws Exception 
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try
		{
			HttpGet getRequest = new HttpGet("https://www.mercadobitcoin.net/api/BTC/trades/1501871369/1501891200/");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			
			//Verificando código de erro retornado
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) 
			{
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}
			
			//Now pull back the response object
			HttpEntity httpEntity = response.getEntity();
			String apiOutput = EntityUtils.toString(httpEntity);
			
			Gson gson = new Gson();
			Type listType = new TypeToken< ArrayList<MercadoBitcoinPojo> >(){}.getType();
			return gson.fromJson(apiOutput, listType);
		}
		finally
		{
			//Important: Close the connect
			httpClient.getConnectionManager().shutdown();
		}
	}
    
    public BitcoinAnalyserPojo getBitcoinAnalyser() throws Exception{
        log.info(" ** Consumindo Serviço mercadobitcoin " );
        List<MercadoBitcoinPojo> listaMercadoBitcoin = callMercadoBitcoin();
        log.info(" ** Serviço mercadobitcoin consumido com sucesso ** " );
        
        log.info(" ** Trabalhando os dados " );
        BitcoinAnalyserPojo bitcoinAurum = refineData(listaMercadoBitcoin);
        log.info(" ** Dados trabalhados com sucesso ** " );

    	return bitcoinAurum;
    }

	private BitcoinAnalyserPojo refineData(List<MercadoBitcoinPojo> listaMercadoBitcoin) {
		BitcoinAnalyserPojo bitcoinAurum = new BitcoinAnalyserPojo();
        BigDecimal mediaVenda  = BigDecimal.ZERO;
        BigDecimal mediaCompra = BigDecimal.ZERO;
        BigDecimal totalCompra = BigDecimal.ZERO;
        BigDecimal totalVenda  = BigDecimal.ZERO;
        List<BigDecimal> valoresCompra = new ArrayList<BigDecimal>();
        List<BigDecimal> valoresVenda = new ArrayList<BigDecimal>();
        List<BigDecimal> maioresCompras = new ArrayList<BigDecimal>();
        List<BigDecimal> maioresVendas = new ArrayList<BigDecimal>();
        
        for (MercadoBitcoinPojo mercadoBitCoin : listaMercadoBitcoin) {
			switch (mercadoBitCoin.getType()) {
			case "sell":
				totalVenda = totalVenda.add(BigDecimal.ONE);
				mediaVenda = mediaVenda.add(mercadoBitCoin.getPrice()).setScale(5, RoundingMode.HALF_EVEN);
				valoresVenda.add(mercadoBitCoin.getPrice());
				break;
			case "buy":
				totalCompra = totalCompra.add(BigDecimal.ONE);
				mediaCompra = mediaCompra.add(mercadoBitCoin.getPrice()).setScale(5, RoundingMode.HALF_EVEN);
				valoresCompra.add(mercadoBitCoin.getPrice());
				break;
			}
		}
        
        Collections.sort(valoresCompra);
        Collections.sort(valoresVenda);
        bitcoinAurum.setMedia_compra(mediaCompra.divide(totalCompra, 5, BigDecimal.ROUND_HALF_UP));
        bitcoinAurum.setMedia_venda(mediaVenda.divide(totalVenda, 5, BigDecimal.ROUND_HALF_UP));
        
        if(valoresCompra.size()%2 == 0){
            bitcoinAurum.setMediana_compra(valoresCompra.get(((valoresCompra.size()/2) + (valoresCompra.size()/2+1))/2 ));
        } else {
            bitcoinAurum.setMediana_compra(valoresCompra.get((valoresCompra.size()+1)/2));
        }
        
        if(valoresVenda.size()%2 == 0){
            bitcoinAurum.setMediana_venda(valoresVenda.get(((valoresVenda.size()/2) + (valoresVenda.size()/2+1))/2 ));
        } else {
            bitcoinAurum.setMediana_venda(valoresVenda.get((valoresCompra.size()+1)/2));
        }
        
        
        for (int i = 0; maioresCompras.size() < 5; i++) {
        	if(i == 0 || valoresCompra.get(i).compareTo(maioresCompras.get(maioresCompras.size()-1)) != 0){
            	maioresCompras.add(valoresCompra.get(i));
        	} else {
        		continue;
        	}
		}
        for (int i = 0; maioresVendas.size() < 5; i++) {
        	if(i == 0 || valoresVenda.get(i).compareTo(maioresVendas.get(maioresVendas.size()-1)) != 0){
        		maioresVendas.add(valoresVenda.get(i));
        	} else {
        		continue;
        	}
		}
		bitcoinAurum.setMaiores_compras(maioresCompras);
		bitcoinAurum.setMaiores_vendas(maioresVendas);
		return bitcoinAurum;
	}
}
