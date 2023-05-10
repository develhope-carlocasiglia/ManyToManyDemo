package co.develhope.ManyToManyDemo.controllers;


import co.develhope.ManyToManyDemo.entities.Event;
import co.develhope.ManyToManyDemo.entities.EventDTO;
import co.develhope.ManyToManyDemo.entities.User;
import co.develhope.ManyToManyDemo.repositories.EventRepository;
import co.develhope.ManyToManyDemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/create-event")
    public ResponseEntity createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event();
        eventRepository.save(event);

        event.setTitle(eventDTO.getTitle());
        event.setDateTime(eventDTO.getDateTime());
        Set<User> participantSet = new HashSet<>();
        for (String email : eventDTO.getParticipantsEmails()) {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) return ResponseEntity.badRequest().body("user not found " + email);
            User participant = optionalUser.get();
            participantSet.add(participant);
            /*
            oltre ad aggiungere gli utenti ad un evento dobbiao anche aggiungere
            l'evento agli eventi dell'utente, sennò l'utente non lo sa.
            ecco perché all'inizio di questo metodo HO GIà SALVATO l'evento nel db
            altrimenti starei cercando di associare ad un utente un oggetto che non esiste
             */
            participant.addEvent(event);
            userRepository.save(participant); // ho cambiato qualcosa!! SALVO
        }
        // esco dal for che ho un set di utenti
        event.setParticipants(participantSet);

        return ResponseEntity.ok(eventRepository.save(event));

    }

    // cancellare un evento
    @DeleteMapping("/delete-event")
    public ResponseEntity deleteEvent(@RequestParam int eventId) {
        /*
        Se rimuovo un evento, gli utenti che ce lo avevano non lo sanno!
        Non lo rimuovono dalle loro liste di eventi, perché non sono mappedBy
        Nelle many to one usavamo orphanRemoval e cascade e facevamo fare tutto automaticamente a spring
        stavolta non possiamo, perché altre entità dipendono da questa entità e non posso cancellare
        utenti innocenti per colpa di questa rimozione
         */

        // PRIMA di cancellare un evento informo gli utenti
        Event eventToBeDeleted = eventRepository.findById(eventId).get(); // TODO gestire optional
        for (User user : eventToBeDeleted.getParticipants()) {
            user.removeEvent(eventToBeDeleted);
            userRepository.save(user);
        }
        // ora che tutti gli utenti coinvolti sanno che l'evento sta per essere rimosso
        // posso rimuovere l'evento
        eventRepository.delete(eventToBeDeleted);
        return ResponseEntity.ok().build();
    }

    // cancellare un utente
    @DeleteMapping("/delete-user")
    public ResponseEntity deleteUser(@RequestParam int userId) {
        return ResponseEntity.ok("todo");
    }

    // rimuovere un partecipante da un evento
    @PutMapping("/remove-participant-from-event")
    public ResponseEntity removeParticipantFromEvent(
            @RequestParam int eventId, @RequestParam int userId) {
        return ResponseEntity.ok("todo");
    }


}
