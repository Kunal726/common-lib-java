package com.projects.marketmosaic.common.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenValidationRespDTO {
    private boolean valid;
    private String username;
    private Long userId;
    private String email;
    private String name;
    private List<String> authorities;
}