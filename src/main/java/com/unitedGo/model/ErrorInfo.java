package com.unitedGo.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ErrorInfo {
    private int errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;
    
}
