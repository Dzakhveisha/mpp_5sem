package com.bsuir.spp.tasklist.dao.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;
}
