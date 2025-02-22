package com.cluster.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
         String id,
         @NotNull(message = "First name is required")
         String firstname,
         @NotNull(message = "Last name is required")
         String lastname,
         @NotNull(message = "Email is required")
         @Email(message = "Invalid email address")
         String email,
         Address address
) {

}
