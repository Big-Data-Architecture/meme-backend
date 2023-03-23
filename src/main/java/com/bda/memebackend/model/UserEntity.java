package com.bda.memebackend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "public", catalog = "users")
public class UserEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private int id;
  @Basic
  @Column(name = "username")
  private String username;
  @Basic
  @Column(name = "password")
  private String password;

}
