package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Description : An agenda that stores events
 */
public class Agenda {

    ArrayList<Event> lesEvents = new ArrayList<>();
    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */
    public void addEvent(Event e) {
        lesEvents.add(e);
    }

    /**
     * Computes the events that occur on a given day
     *
     * @param day the day toi test
     * @return and iteraror to the events that occur on that day
     */
    public List<Event> eventsInDay(LocalDate day) {
        ArrayList<Event> res = new ArrayList<>();
        for(Event e : lesEvents){
            if(e.isInDay(day)){
                res.add(e);
            }
        }
        return res;
    }

    /**
     * Trouver les événements de l'agenda en fonction de leur titre
     * @param title le titre à rechercher
     * @return les événements qui ont le même titre
     */
    public List<Event> findByTitle(String title) {
        List<Event> memeTitre = new ArrayList<>();
        for (Event e : lesEvents){
            //on compare les chaines de caractères en les mettant en minuscules
            if (e.getTitle().toLowerCase().equals(title.toLowerCase())){
                memeTitre.add(e);
            }
        }   
        return memeTitre;    
    }
    
    /**
     * Déterminer s’il y a de la place dans l'agenda pour un événement
     * @param e L'événement à tester (on se limitera aux événements simples)
     * @return vrai s’il y a de la place dans l'agenda pour cet événement
     */
    public boolean isFreeFor(Event e) {
        for (Event myEvent : lesEvents){
            if (e.getStart().equals(myEvent.getStart()) // les 2 events commencent en même temps
                || e.getStart().isAfter(myEvent.getStart()) && e.getStart().isBefore(myEvent.getStart().plus(myEvent.getDuration())) // l'event que l'on teste débute pendant un autre event (avant la fin d'un autre event)
                || e.getStart().isBefore(myEvent.getStart()) && e.getStart().plus(e.getDuration()).isAfter(myEvent.getStart()) // l'event que l'on teste termine après le début d'un autre event
                ){
                    return false;
            }
        }
        return true;       
    }
   
}
