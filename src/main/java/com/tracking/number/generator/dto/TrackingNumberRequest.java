package com.tracking.number.generator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Tracking number request.
 */
public record TrackingNumberRequest(
        @NotBlank(message = "Origin country ID must not be blank") String originCountryId,
        @NotBlank(message = "Destination country ID must not be blank") String destinationCountryId,
        @Positive(message = "Weight must be positive") double weight,
        @NotNull(message = "Created date/time must not be null") Instant createdAt,
        @NotNull(message = "Customer ID must not be null") UUID customerId,
        @NotBlank(message = "Customer name must not be blank") String customerName,
        @NotBlank(message = "Customer slug must not be blank") String customerSlug
) {}