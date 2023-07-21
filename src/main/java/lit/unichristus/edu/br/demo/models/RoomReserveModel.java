package lit.unichristus.edu.br.demo.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lit.unichristus.edu.br.demo.enums.LocalEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reserve_room")
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class RoomReserveModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Serial
    private static final long serialVersionUID = 1L;

    private Date createdDate;

    private boolean isDeleted;
    //    --------------------------

    private UUID room;

    private Date date;
    private Date initialTime;

    private Date finalTime;

    @Enumerated(EnumType.STRING)
    private LocalEnum local;

    private boolean shared;

    @Column(nullable = true, length = 30)
    private String observation;

    private UUID responsible;

    private boolean presence;

    public RoomReserveModel(Date createdDate, boolean isDeleted, UUID room, Date date, Date initialTime, Date finalTime, LocalEnum local, boolean shared, String observation, UUID responsible, boolean presence) {
        this.isDeleted = isDeleted;
        this.room = room;
        this.date = date;
        this.initialTime = initialTime;
        this.finalTime = finalTime;
        this.local = local;
        this.shared = shared;
        this.observation = observation;
        this.responsible = responsible;
        this.presence = presence;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public UUID getRoom() {
        return room;
    }

    public void setRoom(UUID room) {
        this.room = room;
    }

    public Date getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(Date initialTime) {
        this.initialTime = initialTime;
    }

    public Date getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(Date finalTime) {
        this.finalTime = finalTime;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalEnum getLocal() {
        return local;
    }

    public void setLocal(LocalEnum local) {
        this.local = local;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }


    public UUID getResponsible() {
        return responsible;
    }

    public void setResponsible(UUID responsible) {
        this.responsible = responsible;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }
}
