package sen.bank.demospring6security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {
    @Column(name = "customer_id")
   // @JsonIgnore
    private long customerId;

    @Id
    @Column(name="account_number")
    //@JsonIgnore
    private long accountNumber;

    @Column(name="account_type")
   // @JsonIgnore
    private String accountType;

    @Column(name = "branch_address")
    //@JsonIgnore
    private String branchAddress;

    @Column(name = "create_dt")
    //@JsonIgnore
    private Date createDt;
}
