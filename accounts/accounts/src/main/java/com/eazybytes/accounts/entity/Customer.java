package com.eazybytes.accounts.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Customer extends  BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name="customer_id")
    private Long customerId;

    private String name;

    private String email;

    @Column(name="mobile_number")
    private String mobileNumber;
}
