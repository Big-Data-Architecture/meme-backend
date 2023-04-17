package com.bda.userservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouritesEntityPK implements Serializable {
    @Column(name = "user_id")
    @Id
    private int userId;
    @Column(name = "meme_id")
    @Id
    private String memeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouritesEntityPK that = (FavouritesEntityPK) o;
        return userId == that.userId && Objects.equals(memeId, that.memeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, memeId);
    }
}
