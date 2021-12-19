package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;
    
    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The duration of the event 
     */
    private Duration myDuration;


    /**
     * Constructs an event
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     */
    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */
    public boolean isInDay(LocalDate aDay) {
        long dureeMinutes = getDuration().toMinutes();
        LocalDateTime myEnd = myStart.plus(dureeMinutes, ChronoUnit.MINUTES);

        LocalDate jourFin = myEnd.toLocalDate();
        LocalDate jourDebut = myStart.toLocalDate();

        if (aDay.isBefore(jourFin) && aDay.isAfter(jourDebut)||aDay.equals(jourDebut) || aDay.equals(jourFin)){
            return true;
        }
        return false;
        
    }
   
    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }

    public void setStart(LocalDateTime date){
        this.myStart = date;
    }


    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    }

    @Override
    public String toString() {
        return "Simple Event : [Intitulé=" + myTitle + ", durée=" + myDuration + ", début=" + myStart +  "]";
    }

   
    
}
