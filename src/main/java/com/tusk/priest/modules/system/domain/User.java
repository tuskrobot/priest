package com.tusk.priest.modules.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private String salt;
    private String email;
    private Date createDate;
    private Date updateDate;
    private String remark;
    private Integer status = 1;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
