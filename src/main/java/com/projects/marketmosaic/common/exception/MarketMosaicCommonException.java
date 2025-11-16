package com.projects.marketmosaic.common.exception;

import lombok.Getter;

public class MarketMosaicCommonException extends RuntimeException {

    @Getter
    private final int code;

    public MarketMosaicCommonException(String message) {
        super(message);
        this.code = 500;
    }
    public MarketMosaicCommonException(String message, int code) {
        super(message);
        this.code = code;
    }



    public MarketMosaicCommonException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }
}
