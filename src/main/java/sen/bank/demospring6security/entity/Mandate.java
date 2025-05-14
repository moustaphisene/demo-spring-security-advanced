package sen.bank.demospring6security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter @Setter
public class Mandate {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;

    private String name;
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    @Column(name="role")
    private String role;

    @Column(name = "create_dt")
    @JsonIgnore
    private Date createDt;


    @OneToMany(mappedBy = "mandate", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Authority> authorities =new HashSet<>();




}
