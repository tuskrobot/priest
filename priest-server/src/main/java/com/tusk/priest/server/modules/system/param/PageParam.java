package com.tusk.priest.server.modules.system.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {
    public Integer page;
    public Integer limit;
}
