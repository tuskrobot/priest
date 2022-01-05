package com.tusk.priest.server.modules.system.domain;

import com.tusk.priest.server.modules.rpc.dto.RegisterDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_register_info")
@Entity
public class Register implements Serializable {
    @Id
    private String id;
    private String type;
    private String host;
    private Integer port;
    @CreatedDate
    private Date createDate;
    private String redisHost;
    private Integer redisDb;

    public Register(String type, String host, Integer port) {
        this.type = type;
        this.host = host;
        this.port = port;
    }

    public Register(RegisterDto dto) {
        this.id = dto.getId();
        this.type = dto.getType();
        this.host = dto.getHost();
        this.port = dto.getPort();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Register register = (Register) o;
        return type.equals(register.getType()) && host.equals(register.getHost()) && port.equals(register.getPort());
    }

}


