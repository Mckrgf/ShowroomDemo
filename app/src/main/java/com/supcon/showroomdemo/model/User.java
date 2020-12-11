package com.supcon.showroomdemo.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author : yaobing
 * @date : 2020/12/11 10:57
 * @desc :
 */
@Entity
public class User {

    @Id
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String psd;
    @NotNull
    private String nfcId;
    @NotNull
    private String faceId;
    @Generated(hash = 855042143)
    public User(Long id, @NotNull String name, @NotNull String psd,
            @NotNull String nfcId, @NotNull String faceId) {
        this.id = id;
        this.name = name;
        this.psd = psd;
        this.nfcId = nfcId;
        this.faceId = faceId;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPsd() {
        return this.psd;
    }
    public void setPsd(String psd) {
        this.psd = psd;
    }
    public String getNfcId() {
        return this.nfcId;
    }
    public void setNfcId(String nfcId) {
        this.nfcId = nfcId;
    }
    public String getFaceId() {
        return this.faceId;
    }
    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
}

