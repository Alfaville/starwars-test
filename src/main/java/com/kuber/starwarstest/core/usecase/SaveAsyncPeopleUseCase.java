package com.kuber.starwarstest.core.usecase;

import org.springframework.messaging.handler.annotation.Payload;

public interface SaveAsyncPeopleUseCase {

    void execute(@Payload String payload);

}
