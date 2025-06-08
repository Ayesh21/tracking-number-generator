package com.tracking.number.generator.controller;

import com.tracking.number.generator.dto.TrackingNumberRequest;
import com.tracking.number.generator.dto.TrackingNumberResponse;
import com.tracking.number.generator.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

import static com.tracking.number.generator.utils.LogConstant.GET_TRACKING_NUMBER;
import static com.tracking.number.generator.utils.UriConstant.GENERATE_NEXT_TRACKING_NUMBER;
import static com.tracking.number.generator.utils.UriConstant.TRACKING_URL;

/**
 * The type Tracking controller.
 */
@RestController
@RequestMapping(TRACKING_URL)
@RequiredArgsConstructor
public class TrackingController {
    private final TrackingService trackingService;
    private static final Logger logger = LoggerFactory.getLogger(TrackingController.class);

    /**
     * Gets next tracking number.
     *
     * @param originCountryId      the origin country id
     * @param destinationCountryId the destination country id
     * @param weight                 the weight
     * @param createdAt             the created at
     * @param customerId            the customer id
     * @param customerName          the customer name
     * @param customerSlug          the customer slug
     * @return the next tracking number
     */
    @GetMapping(GENERATE_NEXT_TRACKING_NUMBER)
    public Mono<TrackingNumberResponse> getNextTrackingNumber(
            @RequestParam String originCountryId,
            @RequestParam String destinationCountryId,
            @RequestParam double weight,
            @RequestParam Instant createdAt,
            @RequestParam UUID customerId,
            @RequestParam String customerName,
            @RequestParam String customerSlug
    ) {
        TrackingNumberRequest request = new TrackingNumberRequest(
                originCountryId,
                destinationCountryId,
                weight,
                createdAt,
                customerId,
                customerName,
                customerSlug
        );
        logger.info(GET_TRACKING_NUMBER, request);
        return trackingService.generateTrackingNumber(request);
    }
}
