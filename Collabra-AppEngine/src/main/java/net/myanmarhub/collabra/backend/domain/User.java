package net.myanmarhub.collabra.backend.domain;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
 */
@Entity
public class User {

    @Id
    @GenericGenerator(name = "hilo-gen", strategy = "hilo")
    @GeneratedValue(generator = "hilo-gen")
    /**
     * Uniqued user id for storage purpose
     */
    private Long id;

    /**
     * username to display. This must be unique.
     */
    @Column(unique = true)
    private String username;

    /**
     * Timestamp for last time user login
     */
    private Date lastLoginTime;

    /**
     * List of conversations that user made
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Conversation> conversations = new HashSet<Conversation>();


    /**
     * Default Constructor
     */
    public User() {
    }

    /**
     * Constructor
     *
     * @param id            unique id to identify user. Use null if initialising new user
     * @param username      username to display
     * @param lastLoginTime last time user logged in
     */
    public User(Long id, String username, Date lastLoginTime) {
        this.id = id;
        this.username = username;
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * Get unique id for User
     *
     * @return unique id for User
     */
    public Long getId() {
        return id;
    }

    /**
     * Set unique id for User
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     *
     * @param username username to display
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get last logged in time
     *
     * @return last logged in time
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * Set last logged in time
     *
     * @param lastLoginTime last time user logged in
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
