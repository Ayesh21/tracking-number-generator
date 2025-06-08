package com.tracking.number.generator.repository;

import com.tracking.number.generator.entity.TrackingDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * The interface Tracking mongo repository.
 */
@Repository
public interface TrackingMongoRepository extends ReactiveMongoRepository<TrackingDocument, String> {
    /**
     * Exists by tracking number mono.
     *
     * @param trackingNumber the tracking number
     * @return the mono
     */
    Mono<Boolean> existsByTrackingNumber(String trackingNumber);
}
