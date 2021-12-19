package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Description : A repetitive event that terminates after a given date, or after
 * a given number of occurrences
 */
public class FixedTerminationEvent extends RepetitiveEvent {
    private LocalDate terminationInclusive;
    private long numberOfOccurrences=-1;
    
    /**
     * Constructs a fixed terminationInclusive event ending at a given date
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param terminationInclusive the date when this event ends
     */
    public FixedTerminationEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency, LocalDate terminationInclusive) {
        super(title, start, duration, frequency);
        this.terminationInclusive = terminationInclusive;
    }

    /**
     * Constructs a fixed termination event ending after a number of iterations
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param numberOfOccurrences the number of occurrences of this repetitive event
     */
    public FixedTerminationEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency, long numberOfOccurrences) {
        super(title, start, duration, frequency);
        this.numberOfOccurrences = numberOfOccurrences ;
    }

    /**
     *
     * @return the termination date of this repetitive event
     */
    public LocalDate getTerminationDate() {
        LocalDate res;
        if (numberOfOccurrences==-1){
            res=terminationInclusive;
        } else {
            LocalDate jourDebut = getStart().toLocalDate();
            res=jourDebut.plus(numberOfOccurrences-1, frequency) ;
        }
        return res;   
    }

    public long getNumberOfOccurrences() {
        long res;
        if (numberOfOccurrences==-1){
            res=frequency.between(getStart().toLocalDate(), terminationInclusive)+1 ;
        } else {
            res = numberOfOccurrences;
        }
        return res;
    }

    public boolean isInDay(LocalDate aDay){
        //si le jour passé en paramètre appartient aux jours d'exceptions (là où un event est exceptionnellement suspendu)
        for (LocalDate d : this.getDatesException()){
            if (d.equals(aDay)){
                return false;
            }
        }

        LocalDate myStart = getStart().toLocalDate();
        LocalDate myEnd = myStart.plus(getNumberOfOccurrences(), frequency);

        //on vérifie que le jour passé en paramètre est bien compris entre le 1er et le dernier jour d'occurence de l'event
        if (aDay.isBefore(myEnd) && aDay.isAfter(myStart)|| aDay.equals(myStart) || aDay.equals(myEnd)){
            //pour chaque jour où se produit l'event (1 fois/semaine), on vérifie si ce jour là correspond au jour passé en paramètre
            for (int i=0 ; i<= this.getNumberOfOccurrences() ; i++){
                if (myStart.plus(i, frequency).equals(aDay)){
                    return true;
                }
            }
            
        }
        return false;
    }
        
}
