package cryptoMarketDemo.servise;

import cryptoMarketDemo.models.User;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public interface UserService {

    //User findUserBySecretKey(String secretKey);

    List<User> findUserByEmail(String email, Connection con) throws SQLException;

    User register(User user); // регистрация

    HashMap<String, BigDecimal> seeBalance(String secret_key, Connection con) throws SQLException; //просмотр баланса кошелька

    User topUpTheBalance(Long id, BigDecimal balance); //пополнение кошелька

    User withdraw(Long id, String currency, BigDecimal count, String credit_card);  //вывод денег с кошелька

    User seeTheExchangeRate(Long id, String currency); //просмотр актуального курса валют

    User exchangeCurrency(Long id, String currencyFrom, String currencyTo, BigDecimal amount); //обмен валюты

    User changeExchangeRate(Long id, String baseCurrency, BigDecimal first_value, BigDecimal second_value); //изменение курса валют (админ)

    HashMap<String, BigDecimal> seeAmountOfSpecificCurrency(Long id, String baseCurrency); //просмотреть общую сумму денег на счетах пользователей в конкретной валюте (админ)

    HashMap<String, BigDecimal> seeAmountOfOperations(Long id, String dateFrom, String dateTo); //просмотреть общую сумму денег на счетах пользователей в конкретной валюте (админ)
}
