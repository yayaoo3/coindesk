package com.oswin.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ForeignCurrencyPK implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Id
    @Column(name = "code")
    private String code;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
