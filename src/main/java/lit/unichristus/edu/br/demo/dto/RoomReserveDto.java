package lit.unichristus.edu.br.demo.dto;

import lit.unichristus.edu.br.demo.enums.LocalEnum;
import lit.unichristus.edu.br.demo.models.RoomReserveModel;

import java.util.Date;
import java.util.UUID;

public class RoomReserveDto {

    private boolean isDeleted;
    //    --------------------------

    private UUID room;

    private Date date;
    private Date initialTime;


    private Date finalTime;

    private LocalEnum local;

    private boolean shared;

    private String observation;

    private UUID responsible;

    private boolean presence;

    public RoomReserveModel toModel() {
        return new RoomReserveModel(isDeleted,room,date,initialTime,finalTime,local,shared,observation,responsible,presence);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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
