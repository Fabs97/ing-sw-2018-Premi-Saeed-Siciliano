package Shared.Model.Tools;

import Shared.Color;

import java.io.Serializable;

public class ToolCard implements Serializable {
    private static final long serialVersionUID = 92;
    private String name;
    private String description;
    private int serialNo;
    private Color color;
    private boolean used;

    public ToolCard(String name, String description, int serialNo, Color color){
        this.name = name;
        this.description = description;
        this.serialNo = serialNo;
        this.color = color;
        this.used = false;
    }
    public /*@pure@*/ String getName() {
        return name;
    }

    public /*@pure@*/ String getDescription() {
        return description;
    }

    public /*@pure@*/ int getSerialNo() {
        return serialNo;
    }

    public /*@pure@*/ Color getColor() {
        return color;
    }

    public boolean /*@pure@*/ isUsed() {
        return used;
    }

    public void useCard(){
        this.used = true;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\nDescription: " + description + "\nSerial number: " + serialNo + "\nColor: " + color + "\nHas been used? " + used;
    }
}
