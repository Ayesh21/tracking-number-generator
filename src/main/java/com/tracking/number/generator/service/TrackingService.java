package com.tracking.number.generator.service;

import com.tracking.number.generator.dto.TrackingNumberRequest;
import com.tracking.number.generator.dto.TrackingNumberResponse;
import reactor.core.publisher.Mono;

/**
 * The interface Tracking service.
 */
public interface TrackingService {

    public Mono<TrackingNumberResponse> generateTrackingNumber(TrackingNumberRequest request);
}
