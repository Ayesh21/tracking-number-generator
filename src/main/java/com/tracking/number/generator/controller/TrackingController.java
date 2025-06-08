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
     * @param origin_country_id      the origin country id
     * @param destination_country_id the destination country id
     * @param weight                 the weight
     * @param created_at             the created at
     * @param customer_id            the customer id
     * @param customer_name          the customer name
     * @param customer_slug          the customer slug
     * @return the next tracking number
     */
    @GetMapping(GENERATE_NEXT_TRACKING_NUMBER)
    public Mono<TrackingNumberResponse> getNextTrackingNumber(
            @RequestParam String origin_country_id,
            @RequestParam String destination_country_id,
            @RequestParam double weight,
            @RequestParam Instant created_at,
            @RequestParam UUID customer_id,
            @RequestParam String customer_name,
            @RequestParam String customer_slug
    ) {
        TrackingNumberRequest request = new TrackingNumberRequest(
                origin_country_id,
                destination_country_id,
                weight,
                created_at,
                customer_id,
                customer_name,
                customer_slug
        );
        logger.info(GET_TRACKING_NUMBER, request);
        return trackingService.generateTrackingNumber(request);
    }
}
