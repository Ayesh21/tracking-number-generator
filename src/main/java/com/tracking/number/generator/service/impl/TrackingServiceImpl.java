package com.tracking.number.generator.service.impl;

import com.tracking.number.generator.dto.TrackingNumberRequest;
import com.tracking.number.generator.dto.TrackingNumberResponse;
import com.tracking.number.generator.entity.TrackingDocument;
import com.tracking.number.generator.repository.TrackingMongoRepository;
import com.tracking.number.generator.service.TrackingService;
import com.tracking.number.generator.transformer.TrackingTransformer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

import static com.tracking.number.generator.utils.LogConstant.GET_TRACKING_RESPONSE;

/**
 * The type Tracking service.
 */
@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

    private final TrackingMongoRepository trackingMongoRepository;
    private static final Logger logger = LoggerFactory.getLogger(TrackingServiceImpl.class);


    /**
     * Generate tracking number mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<TrackingNumberResponse> generateTrackingNumber(TrackingNumberRequest request) {
        return tryGenerateAndSave(request, 0)
                .map(saved -> new TrackingNumberResponse(saved.getTrackingNumber(), saved.getCreatedAt()))
                .doOnNext(response -> logger.info(GET_TRACKING_RESPONSE, response));
    }

    /**
     * Re-try and generate tracking number.
     *
     * @param request the request
     * @param attempt the number of retries
     * @return the mono
     */
    private Mono<TrackingDocument> tryGenerateAndSave(TrackingNumberRequest request, int attempt) {
        if (attempt >= 5) {
            return Mono.error(new RuntimeException("Failed to generate a unique tracking number after 5 attempts."));
        }

        String trackingNumber = trackingNumberGenerator(request);
        TrackingDocument doc = TrackingTransformer.toEntity(request, trackingNumber);
        return trackingMongoRepository.save(doc)
                .onErrorResume(DuplicateKeyException.class, e -> tryGenerateAndSave(request, attempt + 1));
    }

    /**
     * Generate a new tracking number mono.
     *
     * @param request the request
     * @return the mono
     */
    private String trackingNumberGenerator(TrackingNumberRequest request) {
        String origin = request.originCountryId().toUpperCase();
        String customerHash = Integer.toHexString(request.customerSlug().hashCode());
        String randomPart = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 999999), 36).toUpperCase();

        String raw = (origin + customerHash + randomPart).toUpperCase().replaceAll("[^A-Z0-9]", "");

        return raw.length() > 16 ? raw.substring(0, 16) : raw;
    }

}
