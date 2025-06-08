package com.tracking.number.generator.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Tracking number request.
 */
public record TrackingNumberRequest(
        String originCountryId,
        String destinationCountryId,
        double weight,
        Instant createdAt,
        UUID customerId,
        String customerName,
        String customerSlug
) {}