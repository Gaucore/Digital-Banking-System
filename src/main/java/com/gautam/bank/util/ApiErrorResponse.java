package com.gautam.bank.util;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {

    private boolean success;
    private String message;
    private List<String> errors;
    private int status;
    private String path;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}