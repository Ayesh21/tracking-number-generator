package com.tracking.number.generator;

import com.tracking.number.generator.dto.TrackingNumberRequest;
import com.tracking.number.generator.entity.TrackingDocument;
import com.tracking.number.generator.repository.TrackingMongoRepository;
import com.tracking.number.generator.service.impl.TrackingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingServiceImplTest {

    @Mock
    private TrackingMongoRepository trackingMongoRepository;

    @InjectMocks
    private TrackingServiceImpl trackingService;

    private TrackingNumberRequest request;
    private TrackingDocument trackingDocument;

    /** Sets up. */
    @BeforeEach
    void setUp() {
        request = new TrackingNumberRequest(
                "US",
                "IN",
                5.0,
                Instant.now(),
                UUID.randomUUID(),
                "Test Customer",
                "test-customer"
        );

        trackingDocument = new TrackingDocument(
                "1",
                "USAB123456789XYZ",
                "US",
                "IN",
                5.0,
                Instant.now(),
                request.customerId().toString(),
                "Test Customer",
                "test-customer"
        );
    }

    /**
     * Generate tracking number successfully test.
     */
    @Test
    void generateTrackingNumberSuccessfullyTest() {
        when(trackingMongoRepository.save(any(TrackingDocument.class)))
                .thenReturn(Mono.just(trackingDocument));

        StepVerifier.create(trackingService.generateTrackingNumber(request))
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(trackingDocument.getTrackingNumber(), response.trackingNumber());
                    assertEquals(trackingDocument.getCreatedAt(), response.createdAt());
                })
                .verifyComplete();

        verify(trackingMongoRepository, times(1)).save(any(TrackingDocument.class));
    }

    /**
     * Generate tracking number with retries and succeed on third attempt.
     */
    @Test
    void generateTrackingNumberRetriesAndSucceedsTest() {
        when(trackingMongoRepository.save(any(TrackingDocument.class)))
                .thenReturn(
                        Mono.error(new DuplicateKeyException("Duplicate 1")),
                        Mono.error(new DuplicateKeyException("Duplicate 2")),
                        Mono.just(trackingDocument)
                );

        StepVerifier.create(trackingService.generateTrackingNumber(request))
                .assertNext(response -> {
                    assertEquals(trackingDocument.getTrackingNumber(), response.trackingNumber());
                })
                .verifyComplete();

        verify(trackingMongoRepository, times(3)).save(any(TrackingDocument.class));
    }

    /**
     * Generate tracking number fails after 5 duplicate attempts.
     */
    @Test
    void generateTrackingNumberFailsAfterFiveAttemptsTest() {
        when(trackingMongoRepository.save(any(TrackingDocument.class)))
                .thenReturn(
                        Mono.error(new DuplicateKeyException("Duplicate 1")),
                        Mono.error(new DuplicateKeyException("Duplicate 2")),
                        Mono.error(new DuplicateKeyException("Duplicate 3")),
                        Mono.error(new DuplicateKeyException("Duplicate 4")),
                        Mono.error(new DuplicateKeyException("Duplicate 5"))
                );

        StepVerifier.create(trackingService.generateTrackingNumber(request))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().contains("Failed to generate a unique tracking number")
                )
                .verify();

        verify(trackingMongoRepository, times(5)).save(any(TrackingDocument.class));
    }
}