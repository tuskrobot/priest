package com.eio.licenseserver.module.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UdpRequest {
    private String type;
    private String data;

    public UdpRequest(String type) {
        this.type = type;
    }
}
