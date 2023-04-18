package com.bda.userservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Table(name = "users", schema = "public", catalog = "users")
@AllArgsConstructor
public class UserEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "user_id")
  private int userId;
  @Basic
  @Column(name = "username")
  private String username;
  @Basic
  @Column(name = "password")
  private String password;
  @Basic
  @Column(name = "firstname")
  private String firstname;
  @Basic
  @Column(name = "lastname")
  private String lastname;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserEntity that = (UserEntity) o;
    return userId == that.userId && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, username, password, firstname, lastname);
  }
}
