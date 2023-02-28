package cryptoMarketDemo.servise;

import cryptoMarketDemo.models.User;
import cryptoMarketDemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiseImplementation implements UserService {
    @Autowired
    UserRepository userRepository;

    //@Override
    //public User findUserBySecretKey(String secretKey) {
    //    return userRepository.findUserBySecret_key(secretKey);
    //}

    @Override
    public List<User> findUserByEmail(String email, Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        List<User> res = new ArrayList<>();
        String query = "select * from user_table u WHERE u.email = " + "'" + email + "'";
        ResultSet rs = stmt.executeQuery(query);
        User u = (User) rs;
        res.add(u);
        return res;
    }

    @Override
    public User register(User user) {
        return null;
    }

    @Override
    public User seeBalance(Long id) {
        return null;
    }

    @Override
    public User topUpTheBalance(Long id, BigDecimal balance) {
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
