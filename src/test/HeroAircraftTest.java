package test;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class HeroAircraftTest {
    @Test
    void getHero(){
        assertSame(HeroAircraft.getInstance(),HeroAircraft.getInstance());
    }
}
