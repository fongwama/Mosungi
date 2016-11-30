package com.fongwama.mosungi.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by STEVEN on 07/10/2016.
 */

public class AgendaALarm implements Parcelable{
    private int id;
    private String titre;
    private String message;
    private String messageNumbers;
    private long dateMillisNow;
    private long dateMillisWakeUp;
    private String dateHumanNow;
    private String dateHumanWakeUp;
    private int repeatCount;
    private int repeatTimeInterval;

    private String musicPath;
    private int volumeLevel;

    private boolean state;

    public AgendaALarm() {
    }

    public AgendaALarm(int id, String titre, String message, String messageNumbers, long dateMillisNow, long dateMillisWakeUp, String dateHumanNow, String dateHumanWakeUp, int repeatCount, int repeatTimeInterval, String musicPath, int volumeLevel, boolean state) {
        this.id = id;
        this.titre = titre;
        this.message = message;
        this.messageNumbers = messageNumbers;
        this.dateMillisNow = dateMillisNow;
        this.dateMillisWakeUp = dateMillisWakeUp;
        this.dateHumanNow = dateHumanNow;
        this.dateHumanWakeUp = dateHumanWakeUp;
        this.repeatCount = repeatCount;
        this.repeatTimeInterval = repeatTimeInterval;
        this.musicPath = musicPath;
        this.volumeLevel = volumeLevel;
        this.state = state;
    }

    protected AgendaALarm(Parcel in) {
        id = in.readInt();
        titre = in.readString();
        message = in.readString();
        messageNumbers = in.readString();
        dateMillisNow = in.readLong();
        dateMillisWakeUp = in.readLong();
        dateHumanNow = in.readString();
        dateHumanWakeUp = in.readString();
        repeatTimeInterval = in.readInt();
        musicPath = in.readString();
        volumeLevel = in.readInt();
        state = in.readByte() != 0;
    }

    public static final Creator<AgendaALarm> CREATOR = new Creator<AgendaALarm>() {
        @Override
        public AgendaALarm createFromParcel(Parcel in) {
            return new AgendaALarm(in);
        }

        @Override
        public AgendaALarm[] newArray(int size) {
            return new AgendaALarm[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageNumbers() {
        return messageNumbers;
    }

    public void setMessageNumbers(String messageNumbers) {
        this.messageNumbers = messageNumbers;
    }

    public long getDateMillisNow() {
        return dateMillisNow;
    }

    public void setDateMillisNow(long dateMillisNow) {
        this.dateMillisNow = dateMillisNow;
    }

    public long getDateMillisWakeUp() {
        return dateMillisWakeUp;
    }

    public void setDateMillisWakeUp(long dateMillisWakeUp) {
        this.dateMillisWakeUp = dateMillisWakeUp;
    }

    public String getDateHumanNow() {
        return dateHumanNow;
    }

    public void setDateHumanNow(String dateHumanNow) {
        this.dateHumanNow = dateHumanNow;
    }

    public String getDateHumanWakeUp() {
        return dateHumanWakeUp;
    }

    public void setDateHumanWakeUp(String dateHumanWakeUp) {
        this.dateHumanWakeUp = dateHumanWakeUp;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getRepeatTimeInterval() {
        return repeatTimeInterval;
    }

    public void setRepeatTimeInterval(int repeatTimeInterval) {
        this.repeatTimeInterval = repeatTimeInterval;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public int getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(int volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titre);
        dest.writeString(message);
        dest.writeString(messageNumbers);
        dest.writeLong(dateMillisNow);
        dest.writeLong(dateMillisWakeUp);
        dest.writeString(dateHumanNow);
        dest.writeString(dateHumanWakeUp);
        dest.writeInt(repeatTimeInterval);
        dest.writeString(musicPath);
        dest.writeInt(volumeLevel);
        dest.writeByte((byte) (state ? 1 : 0));
    }
}
