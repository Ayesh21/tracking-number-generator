package com.tracking.number.generator.transformer;

import com.tracking.number.generator.dto.TrackingNumberRequest;
import com.tracking.number.generator.dto.TrackingNumberResponse;
import com.tracking.number.generator.entity.TrackingDocument;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TrackingTransformer {

    /**
     * Converts a TrackingNumberRequest and tracking number to a TrackingDocument entity.
     *
     * @param request the TrackingNumberRequest
     * @param trackingNumber the generated tracking number
     * @return the TrackingDocument entity
     */
    public static TrackingDocument toEntity(final TrackingNumberRequest request, final String trackingNumber) {
        final TrackingDocument doc = new TrackingDocument();
        doc.setTrackingNumber(trackingNumber);
        doc.setOriginCountryId(request.originCountryId());
        doc.setDestinationCountryId(request.destinationCountryId());
        doc.setWeight(request.weight());
        doc.setCreatedAt(OffsetDateTime.now().toInstant());
        doc.setCustomerId(request.customerId().toString());
        doc.setCustomerName(request.customerName());
        doc.setCustomerSlug(request.customerSlug());
        return doc;
    }

    /**
     * Converts a TrackingDocument to a TrackingNumberResponse DTO.
     *
     * @param doc the TrackingDocument
     * @return the TrackingNumberResponse
     */
    public static TrackingNumberResponse toResponse(final TrackingDocument doc) {
        return new TrackingNumberResponse(doc.getTrackingNumber(), doc.getCreatedAt());
    }
}
