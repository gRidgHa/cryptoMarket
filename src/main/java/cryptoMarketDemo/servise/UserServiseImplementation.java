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
            stmt.executeUpdate(query_in);
            return secret_key;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
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
    public HashMap<String, BigDecimal> topUpTheBalance(String secret_key, BigDecimal balance, Connection con) {
        try (Statement stmt = con.createStatement()) {
            String query_out = String.format("select RUB_wallet from user_table where secret_key = '%s'", secret_key);
            ResultSet rs1 = stmt.executeQuery(query_out);
            if (rs1.next()){
                BigDecimal oldBalance = rs1.getBigDecimal("RUB_wallet");
                balance = balance.add(oldBalance);
            }
            String query_in = String.format("update user_table set RUB_wallet = %f where secret_key = '%s'", balance, secret_key).replace(',', '.');
            stmt.executeUpdate(query_in);
            HashMap<String, BigDecimal> res = new HashMap<>();
            res.put("RUB_balance", balance);
            return res;

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    @Override
    public HashMap<String, BigDecimal> withdraw(String secret_key, String currency, BigDecimal count, String credit_card, Connection con) throws SQLException {
        try (Statement stmt = con.createStatement()) {
            String query_out = String.format("select %s_wallet from user_table where secret_key = '%s'", currency, secret_key);
            ResultSet rs1 = stmt.executeQuery(query_out);
            if (rs1.next()){
                BigDecimal oldBalance = rs1.getBigDecimal("RUB_wallet");
                if (oldBalance.compareTo(count) >= 0){
                    oldBalance = oldBalance.subtract(count);
                    String query_in = String.format("update user_table set %s_wallet = %f where secret_key = '%s'", currency, oldBalance, secret_key).replace(',', '.');
                    stmt.executeUpdate(query_in);
                    HashMap<String, BigDecimal> res = new HashMap<>();
                    String resString = String.format("%s_balance", currency);
                    res.put(resString, oldBalance);
                    return res;

                }
                else{
                    HashMap<String, BigDecimal> error = new HashMap<>();
                    error.put("Ошибка: не хватает средств", null);
                    return error;
                }

            }
            else {
                HashMap<String, BigDecimal> error = new HashMap<>();
                error.put("Ошибка: кошелёк не найден", null);
                return error;
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    @Override
    public HashMap<String, BigDecimal> seeTheExchangeRate(String secret_key, String currency, Connection con){
        try (Statement stmt = con.createStatement()) {
            String query_out = String.format("Select * from exchange_rate_table where base_currency = '%s'", currency);
            ResultSet rs1 = stmt.executeQuery(query_out);
            if (rs1.next()){
                HashMap<String, BigDecimal> res = new HashMap<>();
                res.put("BTS", rs1.getBigDecimal("BTC_wallet"));
                res.put("TON", rs1.getBigDecimal("TON_wallet"));
                res.put("RUB", rs1.getBigDecimal("RUB_wallet"));
                return res;
            }
            else{
                HashMap<String, BigDecimal> error = new HashMap<>();
                error.put("Ошибка: неверно указана валюта", null);
                return error;
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

        }
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
