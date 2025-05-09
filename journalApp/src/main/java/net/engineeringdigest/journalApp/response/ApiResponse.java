package net.engineeringdigest.journalApp.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data;
    private String message;
}
