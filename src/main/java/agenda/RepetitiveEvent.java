package agenda;

import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Description : A repetitive Event
 */
public class RepetitiveEvent extends Event {

    protected ChronoUnit frequency;
    protected ArrayList<LocalDate> datesException = new ArrayList<>();
    /**
     * Constructs a repetitive event
     *
     * @param title the title of this event
     * @param start the start of this event
     * @param duration myDuration in seconds
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     */
    public RepetitiveEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency) {
        super(title, start, duration);
        this.frequency = frequency;
    }

    /**
     * Adds an exception to the occurrence of this repetitive event
     *
     * @param date the event will not occur at this date
     */
    public void addException(LocalDate date) {
        datesException.add(date);
    }

    
    public ArrayList<LocalDate> getDatesException() {
        return datesException;
    }


    public boolean isInDay(LocalDate aDay){

        //on initialise dateSup à la date de début de l'event
        LocalDate dateSup = getStart().toLocalDate();

        //on vérifie que le jour passé en paramètre est après le jour de début de l'event
        if (aDay.isAfter(dateSup) || aDay.equals(dateSup)){
            //pour chaque jour où se produit l'event (1 fois/semaine), on vérifie si ce jour là correspond au jour passé en paramètre
            while (aDay.isAfter(dateSup) || aDay.equals(dateSup)){
                
                if (dateSup.equals(aDay) && (!datesException.contains(aDay))){
                    return true;
                }
                dateSup=dateSup.plus(1, frequency);
                
            }
        }
        return false;
    }

    
    
    

    /**
     *
     * @return the type of repetition
     */
    public ChronoUnit getFrequency() {
        return frequency;   
    }


}
