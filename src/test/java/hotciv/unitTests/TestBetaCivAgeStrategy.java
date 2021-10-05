package hotciv.unitTests;

import hotciv.variants.betaCiv.BetaCivAgeStrategy;
import hotciv.standard.AgeStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBetaCivAgeStrategy {
    private AgeStrategy ageStrategy;
    @BeforeEach
    public void setUp() {
        ageStrategy = new BetaCivAgeStrategy();
    }

    @Test
    public void shouldReturn100AtAge3500BC() {
        assertThat(ageStrategy.calculateAge(-3500),is(100));
    }

    @Test
    public void shouldReturn100AtAge200BC() {
        assertThat(ageStrategy.calculateAge(-200),is(100));
    }

    @Test
    public void shouldReturn99AtAge100BC() {
        assertThat(ageStrategy.calculateAge(-100),is(99));
    }

    @Test
    public void shouldReturn2AtAge1BC() {
        assertThat(ageStrategy.calculateAge(-1),is(2));
    }

    @Test
    public void shouldReturn2AtAge1AC() {
        assertThat(ageStrategy.calculateAge(1),is(49));
    }

    @Test
    public void shouldReturn50AtAge50AC() {
        assertThat(ageStrategy.calculateAge(50),is(50));
    }

    @Test
    public void shouldReturn25AtAge1750() {
        assertThat(ageStrategy.calculateAge(1750),is(25));
    }

    @Test
    public void shouldReturn10AtAge1900() {
        assertThat(ageStrategy.calculateAge(1900),is(5));
    }
}


