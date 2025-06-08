package com.tracking.number.generator.dto;

import java.time.Instant;

/**
 * The type Tracking number response.
 */
public record TrackingNumberResponse(
        String trackingNumber,
        Instant createdAt
) {}