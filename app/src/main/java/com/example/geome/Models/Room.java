package com.example.geome.Models;

public class Room {
    public int id;
    public int idHotel;
    public String roomNumber;
    public String description;
    public double price;
    public String nameRoom;
    public int capacity;

    public Room(int id, int idHotel, String roomNumber, String description, double price, String nameRoom, int capacity) {
        this.id = id;
        this.idHotel = idHotel;
        this.roomNumber = roomNumber;
        this.description = description;
        this.price = price;
        this.nameRoom = nameRoom;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
