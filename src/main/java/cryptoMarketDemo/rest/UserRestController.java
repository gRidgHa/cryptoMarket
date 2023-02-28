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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/v1/cryptoMarket/")
public class UserRestController {
    @Autowired
    private UserService userService;
    //@RequestMapping(value = "{secret_key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //public ResponseEntity<User> getUser(@PathVariable("secret_key") String secret_key){
    //    if (secret_key == null) {
    //        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //    }
//
    //    User user = this.userService.findUserBySecretKey(secret_key);
//
    //    if (user == null) {
    //        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //    }
//
    //    return new ResponseEntity<>(user, HttpStatus.OK);
    //}
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


}

