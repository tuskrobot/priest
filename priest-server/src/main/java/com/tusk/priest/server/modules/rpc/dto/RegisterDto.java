package com.tusk.priest.server.modules.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_register_info")
@Entity
public class RegisterDto implements Serializable {
    @Id
    private String id;
    private String type;
    private String host;
    private Integer port;

    public RegisterDto(String type, String host, Integer port) {
        this.type = type;
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterDto registerInfo = (RegisterDto) o;
        return type.equals(registerInfo.getType()) && host.equals(registerInfo.getHost()) && port.equals(registerInfo.getPort());
    }

}


