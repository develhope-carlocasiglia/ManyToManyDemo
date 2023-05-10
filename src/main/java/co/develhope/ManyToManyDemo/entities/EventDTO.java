package co.develhope.ManyToManyDemo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class EventDTO {

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    // ["carlo.casiglia@gmail.com", "christian@libero.it", ...]
    private String[] participantsEmails;

    private int creationUserId;

    public EventDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String[] getParticipantsEmails() {
        return participantsEmails;
    }

    public void setParticipantsEmails(String[] participantsEmails) {
        this.participantsEmails = participantsEmails;
    }
}
