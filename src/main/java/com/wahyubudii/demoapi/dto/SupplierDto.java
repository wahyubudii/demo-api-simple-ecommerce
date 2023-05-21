package com.wahyubudii.demoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SupplierDto {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Address is required")
    private String address;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;
}
