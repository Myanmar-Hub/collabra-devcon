package net.myanmarhub.collabra.backend.domain;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import com.google.appengine.repackaged.org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GCMDevice {

    @Id
    private String deviceRegistrationID;

    private String deviceInformation;

    private long timestamp;

    @OneToMany(mappedBy = "gcmDevice", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MessageData> messages = new ArrayList<MessageData>();

    public String getDeviceRegistrationID() {
        return deviceRegistrationID;
    }

    public String getDeviceInformation() {
        return this.deviceInformation;
    }

    public void setDeviceRegistrationID(String deviceRegistrationID) {
        this.deviceRegistrationID = deviceRegistrationID;
    }

    public void setDeviceInformation(String deviceInformation) {
        this.deviceInformation = deviceInformation;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<MessageData> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageData> messages) {
        this.messages = messages;
    }
}
