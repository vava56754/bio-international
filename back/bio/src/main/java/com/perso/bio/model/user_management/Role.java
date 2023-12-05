package com.perso.bio.model.user_management;

import com.perso.bio.constants.Field;
import com.perso.bio.enums.TypeDeRole;
import jakarta.persistence.*;

@Entity
@Table(name = Field.ROLE)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    private TypeDeRole roleName;

    public Role() {

    }

    public Role(TypeDeRole roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public TypeDeRole getRoleName() {
        return roleName;
    }

    public void setRoleName(TypeDeRole roleName) {
        this.roleName = roleName;
    }
}
