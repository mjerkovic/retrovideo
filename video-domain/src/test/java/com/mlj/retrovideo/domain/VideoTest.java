package com.mlj.retrovideo.domain;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

public class VideoTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(Video.class);
    }

    @Test
    public void testAddVideo() throws Exception {
        fixture.given()
                .when(new AddVideo("todo1", "need to implement the aggregate"))
                .expectEvents(new VideoAddedEvent("todo1", "need to implement the aggregate"));
    }

}
