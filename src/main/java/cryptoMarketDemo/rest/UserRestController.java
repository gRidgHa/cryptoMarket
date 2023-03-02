package cryptoMarketDemo.rest;

import cryptoMarketDemo.models.User;
import cryptoMarketDemo.servise.SecretKeyGenerator;
import cryptoMarketDemo.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/cryptoMarket/")
public class UserRestController {
    @Autowired
    private UserService userService;


    @GetMapping("register")
    public ResponseEntity<String> seeBalance(@RequestHeader("username") String username,
                                             @RequestHeader("email") String email) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (username == null || email == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String resp = this.userService.register(username, email, con);
        con.close();

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("seeBalance")
    public ResponseEntity<HashMap<String, BigDecimal>> seeBalance(@RequestHeader("secret_key") String secret_key) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (secret_key == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HashMap<String, BigDecimal> hm = this.userService.seeBalance(secret_key, con);
        con.close();
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }

    @PostMapping("topUpTheBalance")
    public ResponseEntity<HashMap<String, BigDecimal>> topUpTheBalance(@RequestHeader("secret_key") String secret_key,
                                                                       @RequestHeader("RUB_wallet") BigDecimal balance) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (secret_key == null || balance == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HashMap<String, BigDecimal> resp = this.userService.topUpTheBalance(secret_key, balance, con);
        con.close();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("withdraw")
    public ResponseEntity<HashMap<String, BigDecimal>> withdraw(@RequestHeader("secret_key") String secret_key,
                                                                @RequestHeader("currency") String currency,
                                                                @RequestHeader("count") BigDecimal count,
                                                                @RequestHeader("credit_card") String credit_card
                                                                ) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (secret_key == null || currency == null || count == null || credit_card == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HashMap<String, BigDecimal> resp = this.userService.withdraw(secret_key, currency, count, credit_card, con);
        con.close();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    @GetMapping ("seeTheExchangeRate")
    public ResponseEntity<HashMap<String, BigDecimal>> seeTheExchangeRate(@RequestHeader("secret_key") String secret_key,
                                                                          @RequestHeader("currency") String currency) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (secret_key == null || currency == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HashMap<String, BigDecimal> resp = this.userService.seeTheExchangeRate(secret_key, currency, con);
        con.close();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("exchangeCurrency")
    public ResponseEntity<HashMap<String, String>> exchangeCurrency(@RequestHeader("secret_key") String secret_key,
                                                                    @RequestHeader("currency_from") String currency_from,
                                                                    @RequestHeader("currency_to") String  currency_to,
                                                                    @RequestHeader("amount") BigDecimal amount) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (secret_key == null || currency_from == null || currency_to == null || amount == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HashMap<String, String> resp = this.userService.exchangeCurrency(secret_key, currency_from, currency_to, amount, con);
        con.close();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    @PostMapping("changeExchangeRate")
    public ResponseEntity<HashMap<String, BigDecimal>> changeExchangeRate(@RequestHeader("base_currency") String base_currency,
                                                                      @RequestHeader("BTC") BigDecimal BTC,
                                                                      @RequestHeader("TON") BigDecimal  TON,
                                                                      @RequestHeader("RUB") BigDecimal  RUB
                                                                      ) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (BTC == null || TON == null || RUB == null ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HashMap<String, BigDecimal> resp = this.userService.changeExchangeRate(base_currency, BTC, TON, RUB, con);
        con.close();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    @GetMapping("seeAmountOfSpecificCurrency")
    public ResponseEntity<HashMap<String, BigDecimal>> seeAmountOfSpecificCurrency(@RequestHeader("secret_key") String secret_key,
                                                                                   @RequestHeader("currency") String currency) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (secret_key == null || currency == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HashMap<String, BigDecimal> resp = this.userService.seeAmountOfSpecificCurrency(secret_key, currency, con);
        con.close();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


}

