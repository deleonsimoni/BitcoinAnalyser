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
package br.com.aurum.BitcoinAnalyser.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resources;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.aurum.BitcoinAnalyser.model.BitcoinAnalyserPojo;
import br.com.aurum.BitcoinAnalyser.model.MercadoBitcoinPojo;
import br.com.aurum.BitcoinAnalyser.service.BitcoinAnalyserService;

@RunWith(Arquillian.class)
public class BitcoinAnalyserTest {

    @Inject
    BitcoinAnalyserService bitcoinService;

    @Inject
    Logger log;
    
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "BitcoinAnalyser.war")
                .addClasses(BitcoinAnalyserPojo.class, BitcoinAnalyserService.class, Resources.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testCallrestMercadoBitcoin() throws Exception {
        log.info("Testando chamada Mercado Bitcoin");
    	List<MercadoBitcoinPojo> mercadoBitcoin = bitcoinService.callMercadoBitcoin();
        assertEquals(966, mercadoBitcoin.size());
    }
    
    @Test
    public void testAllprocess() throws Exception {
        log.info("Testando todo processo");
        BitcoinAnalyserPojo bitcoin = bitcoinService.getBitcoinAnalyser();
        assertNotNull(bitcoin);
    }

}
