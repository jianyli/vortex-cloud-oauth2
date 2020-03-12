package com.vortex.cloud.domain;

import com.vortex.cloud.vfs.data.model.BakDeleteModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vortex_userInfo")
public class UserInfo extends BakDeleteModel {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
