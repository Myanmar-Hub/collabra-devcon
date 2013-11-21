package net.myanmarhub.collabra.backend.domain;

import com.google.appengine.repackaged.org.codehaus.jackson.map.annotate.JsonSerialize;

import org.hibernate.annotations.GenericGenerator;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * An entity for messsages sent from the web console to the registered devices
 * <p/>
 * Its associated endpoint, MessageEndpoint.java, was NOT automatically generated
 * from this class. While it is easy to generate endpoints automatically, you can
 * write an endpoint manually without generating it. You still need to generate
 * the associated client library from the endpoint when changes are made.
 * <p/>
 * For more information on endpoints, see
 * http://developers.google.com/eclipse/docs/cloud_endpoints.
 */

@Entity
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MessageData {

    @Id
    @GenericGenerator(name = "hilo-gen", strategy = "hilo")
    @GeneratedValue(generator = "hilo-gen")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private int kind;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private long timestamp;

    @ManyToOne
    private GCMDevice gcmDevice;

    public MessageData(String message, int kind, int type) {
        this.message = message;
        this.kind = kind;
        this.type = type;
        this.timestamp = Calendar.getInstance().getTime().getTime();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GCMDevice getGcmDevice() {
        return gcmDevice;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setGcmDevice(GCMDevice gcmDevice) {
        this.gcmDevice = gcmDevice;
    }
}
