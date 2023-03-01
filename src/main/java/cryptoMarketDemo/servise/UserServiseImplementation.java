package cryptoMarketDemo.servise;

import cryptoMarketDemo.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiseImplementation implements UserService {


    @Override
    public String register(String username, String email, Connection con) {
        SecretKeyGenerator generator = new SecretKeyGenerator();
        String secret_key = new String(generator.generatePassword());
        try (Statement stmt = con.createStatement()) {
            String query_check = String.format("select username, email from user_table where username = '%s' or email = '%s'", username, email);
            if (stmt.executeQuery(query_check) != null){
                return "Такой пользователь уже зарегистрирован";
            }
            String query_in = String.format("insert into user_table (secret_key, user_type, username, email, BTC_WALLET, TON_wallet, RUB_wallet) values ('%s', 'user', '%s', '%s', 0, 0, 0)", secret_key, username, email);
            stmt.executeQuery(query_in);
            return secret_key;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return secret_key;

        }
    }

    @Override
    public HashMap<String, BigDecimal> seeBalance(String secret_key, Connection con){
        try (Statement stmt = con.createStatement()) {
            String query = "select u.BTC_wallet, u.TON_wallet, u.RUB_wallet from user_table u WHERE u.secret_key = " + "'" + secret_key + "'";
            ResultSet rs = stmt.executeQuery(query);
            HashMap<String, BigDecimal> result = new HashMap<>();
            while (rs.next()) {
                result.put("BTC_wallet", rs.getBigDecimal("BTC_wallet"));
                result.put("TON_wallet", rs.getBigDecimal("TON_wallet"));
                result.put("RUB_wallet", rs.getBigDecimal("RUB_wallet"));
            }
            return result;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Override
    public HashMap<String, BigDecimal> topUpTheBalance(String secret_key, BigDecimal balance, Connection con) throws SQLException {
        return null;
    }


    @Override
    public User withdraw(Long id, String currency, BigDecimal count, String credit_card) {
        return null;
    }

    @Override
    public User seeTheExchangeRate(Long id, String currency) {
        return null;
    }

    @Override
    public User exchangeCurrency(Long id, String currencyFrom, String currencyTo, BigDecimal amount) {
        return null;
    }

    @Override
    public User changeExchangeRate(Long id, String baseCurrency, BigDecimal first_value, BigDecimal second_value) {
        return null;
    }

    @Override
    public HashMap<String, BigDecimal> seeAmountOfSpecificCurrency(Long id, String baseCurrency) {
        return null;
    }

    @Override
    public HashMap<String, BigDecimal> seeAmountOfOperations(Long id, String dateFrom, String dateTo) {
        return null;
    }
}
