package org.example.entities.Entities_Realy;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.Entities_Realy.Auth.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tbl_productAvatarImage")
public class UserAvatarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255,nullable = false)
    private String name;

//    @JsonIgnore


    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    //Getters and setters omitted for brevity

    public void setUser(UserEntity user) {
        if (user == null) {
            if (this.user != null) {
                this.user.setUserAvatar(null);
            }
        }
        else {
            user.setUserAvatar(this);
        }
        this.user = user;
    }


    private boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date created;



}
