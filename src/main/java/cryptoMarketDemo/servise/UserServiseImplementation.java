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
    public HashMap<String, String> exchangeCurrency(String secret_key, String currency_from, String currency_to, BigDecimal amount, Connection con) throws SQLException {
        try (Statement stmt = con.createStatement()) {
            String query_out = String.format("Select %s_wallet from user_table where secret_key = '%s'", currency_from, secret_key);
            ResultSet rs1 = stmt.executeQuery(query_out);
            if (rs1.next()){
                String rs1String = String.format("%s_wallet", currency_from);
                BigDecimal soldCurrency = rs1.getBigDecimal(rs1String).subtract(amount); // количество оставшейся обменянной валюты
                if (rs1.getBigDecimal(rs1String).compareTo(amount) >= 0){ // хватает ли средств
                    String query_out2 = String.format("Select %s_wallet from exchange_rate_table where base_currency = '%s'", currency_from, currency_to);
                    String rs2String = String.format("%s_wallet", currency_from); // курс для новой валюты
                    ResultSet rs2 = stmt.executeQuery(query_out2);
                    if (rs2.next()){
                        BigDecimal newAmount = amount.divide(rs2.getBigDecimal(rs2String)); // количество новой валюты
                        String query_out3 = String.format("Select %s_wallet from user_table where secret_key = '%s'", currency_to, secret_key);
                        String rs3String = String.format("%s_wallet", currency_to);
                        ResultSet rs3 = stmt.executeQuery(query_out3); // старое количество итоговой валюты
                        if (rs3.next()){


                            BigDecimal boughtCurrency = rs3.getBigDecimal(rs3String).add(newAmount); // нынешнее количество полученной валюты


                            String query_in1 = String.format("update user_table set %s_wallet = %f where secret_key = '%s'", currency_from, soldCurrency, secret_key).replace(',', '.');
                            stmt.executeUpdate(query_in1);
                            String query_in2 = String.format("update user_table set %s_wallet = %f where secret_key = '%s'", currency_to, boughtCurrency, secret_key).replace(',', '.');
                            stmt.executeUpdate(query_in2);

                            String query_out_res = String.format("Select %s_wallet, %s_wallet from user_table where secret_key = '%s'", currency_from, currency_to, secret_key);
                            ResultSet resultSet = stmt.executeQuery(query_out_res);
                            if (resultSet.next()){
                                HashMap<String, String> response = new HashMap<>();
                                response.put("currency_from", currency_from);
                                response.put("currency_to", currency_to);
                                response.put("amount_from", String.valueOf(resultSet.getBigDecimal(rs1String)));
                                response.put("amount_to", String.valueOf(resultSet.getBigDecimal(rs3String)));
                                return response;
                            }
                        }
                    }
                }
                else {
                    HashMap<String, String> error = new HashMap<>();
                    error.put("Ошибка: ", "недостаточно средств на счету");
                    return error;
                }
            }
            else{
                HashMap<String, String> error = new HashMap<>();
                error.put("Ошибка: ", "неверно указана валюта или пользователь не найден");
                return error;
            }


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

        }
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
