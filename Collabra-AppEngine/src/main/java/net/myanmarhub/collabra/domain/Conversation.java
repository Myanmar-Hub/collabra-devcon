package net.myanmarhub.collabra.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    private Long id;

    /**
     * sender of this Conversation
     */
    @ManyToOne
    private User sender;

    /**
     * recipients of this Conversation
     */
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<User> recipients = new HashSet<User>();

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
     * Get recipients of this Conversation
     *
     * @return recipients of this Conversation
     */
    public Set<User> getRecipients() {
        return recipients;
    }

    /**
     * Set recipients of this Conversation
     *
     * @param recipients recipients of this Conversation
     */
    public void setRecipients(Set<User> recipients) {
        this.recipients = recipients;
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
                ", recipients=" + recipients +
                ", message='" + message + '\'' +
                ", sendAt=" + sendAt +
                '}';
    }
}
