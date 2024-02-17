package com.hibernate.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "lots")
@DynamicInsert
@DynamicUpdate
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Long lotId;
    @Column(columnDefinition = "int default 0")
    private int available;
    private double price;
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "car_id")
    private Car car;

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Lot ID = " + lotId +
                "\t\tCars available  = " + available +
                "\t\tPrice = " + price +
                "\nCar:\n" + car;
    }
}
