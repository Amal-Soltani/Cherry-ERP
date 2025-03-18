package com.cherry.erp.account.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@Table(name = "users")
@Data
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "SEQ", sequenceName = "users_seq", allocationSize = 1)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@TypeDef(name = "int-array", typeClass = IntArrayType.class)
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE users SET is_active = false WHERE id = ? AND version = ?")
public class SpiUser extends GenericEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_ID")
    private Employee employee;

    @Column(name = "e_mail", nullable = false)
    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email;

    @Column(name = "password", nullable = false)
    //@NotBlank(message = "password is required")
    @Size(min = 6, message = "password must have more than 6 characters")
    private String password;

    @Column(name = "enabled", columnDefinition = "boolean default false")
    private boolean enabled;

    @Column(name = "roles", columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] roles = new String[0];

    @Column(name = "stores", columnDefinition = "integer[]")
    @Type(type = "int-array")
    private Integer[] stores = new Integer[0];

    @Column(name = "nb_connection", nullable = false, columnDefinition = "integer DEFAULT 0")
    private Integer connection = 0;

    @Column(name = "phone")
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_connexion")
    private Date lastConnexion;

    @Column(name = "logged_as")
    private Integer loggedAs;

    public void addRole(String role) {
        if (!hasRole(role)) {
            List<String> rolesList = Arrays.asList(getRoles());
            ArrayList<String> roleArray = new ArrayList<>(rolesList);
            roleArray.add(role);
            this.roles = roleArray.toArray(new String[0]);
        }
    }

    public boolean hasRole(String role) {
        for (String r : this.roles) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }

}
