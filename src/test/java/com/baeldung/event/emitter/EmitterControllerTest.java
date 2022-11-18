package com.baeldung.event.emitter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

@ExtendWith(SpringExtension.class)
@WebFluxTest(EmitterController.class)
class EmitterControllerTest {

    @Autowired
    WebTestClient client;

    @Test
    void testEventEmitter() {
        ParameterizedTypeReference<ServerSentEvent<EmitterController.Foo>> type
                        = new ParameterizedTypeReference<ServerSentEvent<EmitterController.Foo>>() {

        };
        client.get()
              .uri("/event")
              .exchange()
              .expectStatus()
              .isOk()
              .returnResult(EmitterController.Foo.class)
              .getResponseBody()
              .subscribe(new Consumer<EmitterController.Foo>() {
                  @Override
                  public void accept(EmitterController.Foo foo) {
                      assertEquals(0L, foo.id());
                      assertEquals("name-0", foo.name());
                  }
              });
    }
}