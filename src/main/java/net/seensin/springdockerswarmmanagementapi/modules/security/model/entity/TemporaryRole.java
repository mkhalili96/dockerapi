package net.seensin.springdockerswarmmanagementapi.modules.security.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NonNull;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.common.view.View;

import javax.persistence.*;

@Entity(name = "S_TEMPROLE")
@Table(name = "S_TEMPROLE")
@Data
public class TemporaryRole {

    @JsonView(View.UserView.externalView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(View.UserView.externalView.class)
    @NonNull
    @Column(name = "role_name", unique = true)
    private String roleName;

    @JsonView(View.UserView.externalView.class)
    @Column
    private Long expireDate;



    public TemporaryRole(@NonNull String roleName, Long expireDate) {
        this.roleName = roleName;
        this.expireDate = expireDate;
    }

    public TemporaryRole() {
    }
}
