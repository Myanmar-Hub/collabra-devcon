package net.myanmarhub.collabra.backend.domain;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
 */
@Entity
public class Conversation {
    /**
     * Unique id to identify conversation
     */
    @Id
    @GenericGenerator(name = "hilo-gen", strategy = "hilo")
    @GeneratedValue(generator = "hilo-gen")
    private Long id;

    /**
     * sender of this Conversation
     */
    @ManyToOne
    private User sender;

    /**
     * message
     */
    @Column(nullable = false)
    private String message;

    /**
     * sent Date
     */
    @Column(nullable = false)
    private Date sendAt;

    /**
     * Default Constructor
     */
    public Conversation() {
    }

    /**
     * Constructor
     *
     * @param id unique id to identify conversation
     */
    public Conversation(Long id) {
        this.id = id;
    }

    /**
     * Get id for Conversation
     *
     * @return id for Conversation
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id for Conversation
     *
     * @param id unique id to identify conversation
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get sender of this Conversation
     *
     * @return sender of this Conversation
     */
    public User getSender() {
        return sender;
    }

    /**
     * Set Sender of this Conversation
     *
     * @param sender sender of this Conversation
     */
    public void setSender(User sender) {
        this.sender = sender;
    }


    /**
     * Get message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message
     *
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get sent date
     *
     * @return
     */
    public Date getSendAt() {
        return sendAt;
    }

    /**
     * Set sent date
     *
     * @param sendAt sent date
     */
    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                ", sendAt=" + sendAt +
                '}';
    }
}
