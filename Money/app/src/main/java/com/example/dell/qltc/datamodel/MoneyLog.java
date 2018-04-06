package com.example.dell.qltc.datamodel;

import java.sql.Date;

public class MoneyLog {
    private int id;
    private String owner;
    private int amount;
    private String content;
    private String note;
    private String date;
    private boolean comes;

    public MoneyLog() {
    }

    public MoneyLog(int id, String owner, int amount,boolean comes, String content, String note, String date) {
        this.id = id;
        this.owner = owner;
        this.comes=comes;
        this.amount = amount;
        this.content = content;
        this.note = note;
        this.date = date;
    }

    public boolean isComes() {
        return comes;
    }

    public void setComes(boolean comes) {
        this.comes = comes;
    }

    public MoneyLog(String owner, int amount, String content, String note, String date) {
        this.owner = owner;
        this.amount = amount;
        this.content = content;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
