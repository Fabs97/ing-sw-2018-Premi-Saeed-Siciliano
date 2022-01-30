package Shared;

import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.Schemes.Scheme;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 33;
    private String name;
    private Scheme scheme;
    private Color color;
    private PrivateObjective privateObjective1;
    private PrivateObjective privateObjective2;
    private int favours;
    private int privateObjectivePoints;
    private int points;
    private volatile boolean endTurn;
    private volatile boolean Available;
    private boolean tenagliaARotelleUsed;

    public Player(String name){
        this.name = name;
        this.scheme = null;
        this.privateObjective1 = null;
        this.privateObjective2 = null;
        favours = 0;
        points = 0;
        privateObjectivePoints = 0;
        endTurn = false;
        tenagliaARotelleUsed = false;
    }

    public void setPrivateObjective1(PrivateObjective privateObjective1) {
        this.privateObjective1 = privateObjective1;
    }

    public void setPrivateObjective2(PrivateObjective privateObjective2) {
        this.privateObjective2 = privateObjective2;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public boolean isEndTurn() {
        return this.endTurn;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public String getName() {
        return name;
    }

    public int getFavours() {
        return favours;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public void setFavours(int favours) {
        this.favours = favours;
    }

    public PrivateObjective getPrivateObjective1() {
        return privateObjective1;
    }

    public PrivateObjective getPrivateObjective2() {
        return privateObjective2;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public int getPrivateObjectivePoints() {
        return privateObjectivePoints;
    }

    public void setPrivateObjectivePoints(int privateObjectivePoints) {
        this.privateObjectivePoints = privateObjectivePoints;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public boolean isTenagliaARotelleUsed() {
        return tenagliaARotelleUsed;
    }

    public void setTenagliaARotelleUsed(boolean tenagliaARotelleUsed) {
        this.tenagliaARotelleUsed = tenagliaARotelleUsed;
    }

    public void setAvailable(boolean value){
        this.Available = value;
    }

    public boolean isAvailable() {
        return Available;
    }
}
