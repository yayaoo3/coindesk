package com.oswin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="Cryptocurrency")
public class Coin {
    @Id              //主鍵id
    @GeneratedValue(generator = "system-uuid")  //主鍵自動生成
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "chartName")
    private String chartName;
    @Column(name = "disclaimer")
    private String disclaimer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @Column(name = "updatedTime",
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedTime;


    //@ManyToOne(targetEntity = ForeignCurrency.class)
    //@ManyToOne(cascade=CascadeType.ALL)
    //@ManyToOne(cascade=CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private List<ForeignCurrency> foreignCurrency = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }


    public void setForeignCurrency(List<ForeignCurrency> foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }

    public List<ForeignCurrency> getForeignCurrency() {
        return foreignCurrency;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id='" + id + '\'' +
                ", chartName='" + chartName + '\'' +
                ", disclaimer='" + disclaimer + '\'' +
                ", updatedTime=" + updatedTime +
                ", foreignCurrency=" + foreignCurrency +
                '}';
    }


    //private ForeignCurrency bpi;
}