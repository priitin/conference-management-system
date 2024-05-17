package org.conference.web.util;

import org.conference.model.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResultUtils {

    public static ResponseEntity<Object> toResponseEntity(Result result) {
        if (result.isFailure())
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result.getErrorMessage());
        else
            return ResponseEntity.ok("");
    }
}
