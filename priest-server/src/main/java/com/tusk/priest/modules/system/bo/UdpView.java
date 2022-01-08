package com.tusk.priest.modules.system.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UdpView {
    private String ip;
    private String version;
}
