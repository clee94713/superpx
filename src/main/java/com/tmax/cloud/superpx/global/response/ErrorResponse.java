package com.tmax.cloud.superpx.global.response;

import com.tmax.cloud.superpx.global.error.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private boolean success;
    private ErrorCode error;

    private ErrorResponse(final ErrorCode code) {
        this.success = false;
        this.error = code;
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}