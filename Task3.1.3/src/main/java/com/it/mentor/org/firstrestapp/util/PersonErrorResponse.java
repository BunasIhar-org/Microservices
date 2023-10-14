package com.it.mentor.org.firstrestapp.util;

import lombok.*;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonErrorResponse {

    private String message;

    private LocalDateTime timestamp;
}