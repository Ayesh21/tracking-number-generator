package com.tracking.number.generator.interceptor;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/** The type Tracing interceptor. */
@Component
public class loggingInterceptor implements WebFilter {

    private final Tracer tracer;

    /**
     * Instantiates a new Tracing WebFilter.
     *
     * @param tracer the tracer
     */
    public loggingInterceptor(final Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final String spanName = exchange.getRequest().getMethod().name() + " " + exchange.getRequest().getURI();
        final Span span = this.tracer.nextSpan().name(spanName).start();

        exchange.getAttributes().put("customSpan", span);
        Tracer.SpanInScope scope = this.tracer.withSpan(span);

        return chain.filter(exchange)
                .doFinally(
                        signalType -> {
                            if (scope != null) {
                                scope.close();
                            }
                            Span currentSpan = (Span) exchange.getAttributes().get("customSpan");
                            if (currentSpan != null) {
                                Throwable error = exchange.getAttribute("org.springframework.web.server.attribute.ServletRequest.ERROR");
                                if (error != null) {
                                    currentSpan.error(error);
                                }
                                currentSpan.end();
                            }
                        });
    }
}
