package com.bda.userservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "favourites", schema = "public", catalog = "users")
@IdClass(FavouritesEntityPK.class)
public class FavouritesEntity {
    @Id
    @Column(name = "user_id")
    private int userId;
    @Id
    @Column(name = "meme_id")
    private String memeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouritesEntity that = (FavouritesEntity) o;
        return userId == that.userId && Objects.equals(memeId, that.memeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, memeId);
    }
}
