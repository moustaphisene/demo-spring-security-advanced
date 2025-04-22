package sen.bank.demospring6security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter @Setter
public class Mandate {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String pwd;
    @Column(name="role")
    private String role;


}
