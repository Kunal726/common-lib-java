package com.projects.marketmosaic.common.exception.handler;

import com.projects.marketmosaic.common.dto.resp.BaseRespDTO;
import com.projects.marketmosaic.common.exception.MarketMosaicCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseGlobalHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseRespDTO> handleGenericException(Exception ex) {
        log.error("Unexpected error: ", ex);
        BaseRespDTO response = new BaseRespDTO();
        response.setCode("SYSTEM_ERROR");
        response.setMessage(ex.getMessage());
        response.setStatus(false);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(MarketMosaicCommonException.class)
    public ResponseEntity<BaseRespDTO> commonException(MarketMosaicCommonException ex) {
        if(ex.getCode() == 401) {
            log.error("Unauthorized error: ", ex);
            BaseRespDTO response = new BaseRespDTO();
            response.setCode("AUTH_ERROR");
            response.setMessage(ex.getMessage());
            response.setStatus(false);
            return ResponseEntity.status(401).body(response);
        }


        log.error("Unexpected error: ", ex);
        BaseRespDTO response = new BaseRespDTO();
        response.setCode("SYSTEM_ERROR");
        response.setMessage(ex.getMessage());
        response.setStatus(false);
        return ResponseEntity.internalServerError().body(response);
    }
}
