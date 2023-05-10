package co.develhope.ManyToManyDemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ParticipationToEvent {

    @Id
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    /*
    id   user_id    event_id
    1    4          7
    2    4          12
    3    5          7
     */


}
