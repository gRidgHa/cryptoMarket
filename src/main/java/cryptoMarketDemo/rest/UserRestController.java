package cryptoMarketDemo.rest;

import cryptoMarketDemo.models.User;
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
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/cryptoMarket/")
public class UserRestController {
    @Autowired
    private UserService userService;



    @GetMapping("getByEmail")
    public ResponseEntity<List<User>> getUserbyEmail(@RequestHeader("email") String email) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<User> user = this.userService.findUserByEmail(email, con);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("seeBalance")
    public ResponseEntity<HashMap<String, BigDecimal>> seeBalance(@RequestHeader("secret_key") String secret_key) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/relax", "postgres", "123456");
        if (secret_key == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HashMap<String, BigDecimal> hm = this.userService.seeBalance(secret_key, con);

        if (secret_key == null) { //TODO переписать эту проверку
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hm, HttpStatus.OK);
    }


}

