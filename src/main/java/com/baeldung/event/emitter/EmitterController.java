package com.baeldung.event.emitter;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalTime;

import reactor.core.publisher.Flux;

@RestController
public class EmitterController {

    @GetMapping(path = "/event", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Foo> eventEmitter() {
        return Flux.interval(Duration.ofSeconds(1))
                   .map(sequence -> new Foo(sequence, "name-"+sequence));
    }

    record Foo(Long id, String name) {
    }
}
