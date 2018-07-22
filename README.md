BitcoinAnalyser: Projeto Teste
========================
Author: Deleon Simoni
Technologies: CDI, JPA, EJB, JPA, JAX-RS, BV
Summary: Projeto Teste
Target Project: WildFly

What is it?
-----------

Este é um projeto teste de análise de uma amostra do mercado de bitcoin contendo um back-end com Java e Front-end AngularJS

Conforme solicitado, este projeto provê os seguintes itens abaixo:

 - Um endpoint (http://localhost:8080/BitcoinAnalyser/aurum/bitcoin) que retorna os valores formatados coletados do Mercado Bitcoin
 - Uma aplicação AngularJS para exibição dos dados (http://localhost:8080/BitcoinAnalyser)
 
Espero que gostem. :)


System requirements
-------------------

Para realizar o build do projeto tudo o que você precisa é do Java 7.0 (Java SDK 1.7) ou superior, Maven 3.1 ou superior.

Esta aplicação tem como base o projeto JBoss WildFly Archtype para facilitar o processo de construção

 
Execute the application 
---------------------

Para executar a aplicação será necessario a utilização do servido Jboss WildFly, em meus testes utilizei a versao 8.2 (download:  http://download.jboss.org/wildfly/8.2.1.Final/wildfly-8.2.1.Final.zip )

Em segundo passo, basta executar o goals build do maven para gerar o pacote binário da aplicação em WAR e realizar o deploy no JBOSS

Por fim, teremos uma URL de acesso: http://localhost:8080/BitcoinAnalyser/


Run the Arquillian Tests 
-------------------------

Para executar os testes da aplicação basta rodar o goals clean test -Parq-wildfly-managed do maven
