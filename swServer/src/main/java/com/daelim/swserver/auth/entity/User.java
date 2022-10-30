package com.daelim.swserver.auth.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String userId;
    @Column(length =10, nullable = false)
    private String userName;
    @Column(nullable = false)
    private String userPassword;
    @Column(nullable = false)
    private String email;
}