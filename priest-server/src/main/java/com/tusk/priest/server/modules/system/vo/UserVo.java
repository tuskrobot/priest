package com.tusk.priest.server.modules.system.vo;

import com.tusk.priest.server.modules.system.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String userName;
    private String email;
    private Integer status;
    private Date createDate;

    public UserVo(User item) {
        this.userName = item.getUserName();
        this.email = item.getEmail();
        this.status = item.getStatus();
        this.createDate = item.getCreateDate();
    }
}


