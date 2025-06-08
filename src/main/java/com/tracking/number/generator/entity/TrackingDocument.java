package com.tracking.number.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * The type Tracking document.
 */
@Document(collection = "tracking_numbers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "tracking_unique", def = "{'trackingNumber': 1}", unique = true)

public class TrackingDocument {
    @Id
    private String id;
    private String trackingNumber;
    private String originCountryId;
    private String destinationCountryId;
    private double weight;
    private Instant createdAt;
    private String customerId;
    private String customerName;
    private String customerSlug;
}