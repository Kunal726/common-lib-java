package com.projects.marketmosaic.common.exception;

public class MarketMosaicCommonException extends RuntimeException {
    public MarketMosaicCommonException(String message) {
        super(message);
    }

    public MarketMosaicCommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
