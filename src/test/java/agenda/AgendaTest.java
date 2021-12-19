package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class AgendaTest {
    Agenda agenda;
    
    // November 1st, 2020
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);

    // January 5, 2021
    LocalDate jan_5_2021 = LocalDate.of(2021, 1, 5);

    // November 1st, 2020, 22:30
    LocalDateTime nov_1__2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);

    //Nouvelles dates
    LocalDateTime nov_1__2020_21_30 = LocalDateTime.of(2020, 11, 1, 21, 30);
    LocalDateTime nov_1__2020_23_30 = LocalDateTime.of(2020, 11, 1, 23, 30);
    LocalDateTime dec_1_2021_13_00 = LocalDateTime.of(2021, 12, 1, 13, 00);
    LocalDateTime dec_15_2021_13_00 = LocalDateTime.of(2021, 12, 15, 13, 00);
    LocalDateTime dec_2_2021_14_00 = LocalDateTime.of(2021, 12, 2, 14, 00);

    // 120 minutes
    Duration min_120 = Duration.ofMinutes(120);

    // Autres durées
    Duration min_60 = Duration.ofMinutes(60);

    // A simple event
    // November 1st, 2020, 22:30, 120 minutes
    Event simple = new Event("Simple event", nov_1__2020_22_30, min_120);

    //New events
    Event piscine1 = new Event("Piscine", dec_1_2021_13_00, min_120);
    Event piscine2 = new Event("Piscine", dec_15_2021_13_00, min_120);
    Event patinoire = new Event("Patinoire", dec_1_2021_13_00, min_120);

    // A Weekly Repetitive event ending at a given date
    RepetitiveEvent fixedTermination = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, jan_5_2021);

    // A Weekly Repetitive event ending after a give number of occurrrences
    RepetitiveEvent fixedRepetitions = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, 10);
    
    // A daily repetitive event, never ending
    // November 1st, 2020, 22:30, 120 minutes
    RepetitiveEvent neverEnding = new RepetitiveEvent("Never Ending", nov_1__2020_22_30, min_120, ChronoUnit.DAYS);

    @BeforeEach
    public void setUp() {
        agenda = new Agenda();
        agenda.addEvent(simple);
        agenda.addEvent(fixedTermination);
        agenda.addEvent(fixedRepetitions);
        agenda.addEvent(neverEnding);
    }
    
    @Test
    public void testMultipleEventsInDay() {
        assertEquals(4, agenda.eventsInDay(nov_1_2020).size(), "Il y a 4 événements ce jour là");
        assertTrue(agenda.eventsInDay(nov_1_2020).contains(neverEnding));
    }

    @Test
    public void testFindByTitle() {
        agenda.addEvent(piscine1);
        agenda.addEvent(piscine2);
        agenda.addEvent(patinoire);

        assertEquals(2, agenda.findByTitle("piscine").size(), "Il y a 2 événements de ce nom");
        assertEquals(0, agenda.findByTitle("luge").size(), "Il y a 0 événement de ce nom");
    }

    @Test
    public void testAgendaLibre() {
        //création d'un event qui doit débuter en même temps que le simpleEvent déjà programmé
        Event simpleMemeTemps = new Event("Simple event", nov_1__2020_22_30, min_60);

        //création d'un event qui doit débuter avant la fin du simpleEvent déjà programmé
        Event simpleDeb = new Event("Simple event", nov_1__2020_23_30, min_120);
        
        //création d'un event qui doit terminer après le début du simpleEvent déjà programmé
        Event simpleFin = new Event("Simple event", nov_1__2020_21_30, min_120);

        assertTrue(agenda.isFreeFor(piscine1), "Les 2 évènements ne se chevauchent pas");
        assertFalse(agenda.isFreeFor(simpleMemeTemps), "Un evenement est déjà programmé au même moment");
        assertFalse(agenda.isFreeFor(simpleDeb), "Un evenement est déjà programmé et n'est pas encore terminé");
        assertFalse(agenda.isFreeFor(simpleFin), "Un autre evenement est déjà programmé avant que celui-ci ne soit terminé");
    }
}
