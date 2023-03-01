package cryptoMarketDemo.servise;

import cryptoMarketDemo.models.User;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;


public interface UserService {


    String register(String username, String email, Connection con) throws SQLException; // регистрация

    HashMap<String, BigDecimal> seeBalance(String secret_key, Connection con) throws SQLException; //просмотр баланса кошелька

    HashMap<String, BigDecimal> topUpTheBalance(String secret_key, BigDecimal balance, Connection con) throws SQLException; //пополнение кошелька

    HashMap<String, BigDecimal> withdraw(String secret_key,String currency,BigDecimal count,String credit_card,Connection con) throws SQLException;  //вывод денег с кошелька

    HashMap<String, BigDecimal> seeTheExchangeRate(String secret_key,String currency, Connection con) throws SQLException; //просмотр актуального курса валют

    User exchangeCurrency(Long id, String currencyFrom, String currencyTo, BigDecimal amount); //обмен валюты

    User changeExchangeRate(Long id, String baseCurrency, BigDecimal first_value, BigDecimal second_value); //изменение курса валют (админ)

    HashMap<String, BigDecimal> seeAmountOfSpecificCurrency(Long id, String baseCurrency); //просмотреть общую сумму денег на счетах пользователей в конкретной валюте (админ)

    HashMap<String, BigDecimal> seeAmountOfOperations(Long id, String dateFrom, String dateTo); //просмотреть общую сумму денег на счетах пользователей в конкретной валюте (админ)
}
