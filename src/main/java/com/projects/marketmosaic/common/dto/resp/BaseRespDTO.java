package com.projects.marketmosaic.common.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRespDTO {
    private String code;
    private String message;
    private boolean status;
    private Object data;
}
