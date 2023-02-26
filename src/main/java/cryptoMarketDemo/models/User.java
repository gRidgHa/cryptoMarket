package cryptoMarketDemo.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "user_table")
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    @Column(name = "secret_key")
    private String secret_key;

    @Column(name = "user_type")
    private String user_type;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "btc_wallet")
    private BigDecimal btc_wallet;

    @Column(name = "ton_wallet")
    private BigDecimal ton_wallet;

    @Column(name = "rub_wallet")
    private BigDecimal rub_wallet;
}
