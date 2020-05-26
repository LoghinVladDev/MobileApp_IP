package com.example.ipapp.object.document;

import androidx.annotation.NonNull;

import com.example.ipapp.object.institution.Address;
import com.example.ipapp.object.institution.Institution;

public abstract class Document {

    protected int ID;
    protected int senderID;
    protected int receiverID;
    protected int senderInstitutionID;
    protected int receiverInstitutionID;
    protected int senderAddressID;
    protected int receiverAddressID;
    protected int creatorID;
    protected String dateCreated;
    protected String dateSent;
    protected boolean isSent;

    protected String creatorEmail;
    protected String receiverEmail;
    protected String senderEmail;
    protected Institution sender;
    protected Institution receiver;
    protected Address fromLocation;
    protected Address toLocation;

    @NonNull
    public String toViewString() {
        return "Document " + this.ID;
    }

    @NonNull
    public String getType() {
        return "Document ";
    }

    protected Document(){

    }

    public String toString(){
        return "{id=" + this.ID +
                ",senderID=" + this.senderID +
                ",senderInstitutionID=" + this.senderInstitutionID +
                ",senderAddressID=" + this.senderAddressID +
                ",receiverID=" + this.receiverID +
                ",receiverInstitutionID=" + this.receiverInstitutionID +
                ",receiverAddressID=" + this.receiverAddressID +
                ",creatorID=" + this.creatorID +
                ",isSent=" + this.isSent +
                ",dateCreated=" + this.dateCreated +
                ",dateSent=" + this.dateSent + "}";
    }

    public Document setID(int ID) {
        this.ID = ID;
        return this;
    }

    public Document setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
        return this;
    }

    public Document setCreatorID(int creatorID) {
        this.creatorID = creatorID;
        return this;
    }

    public Document setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public Document setDateSent(String dateSent) {
        this.dateSent = dateSent;
        return this;
    }

    public Document setFromLocation(Address fromLocation) {
        this.fromLocation = fromLocation;
        return this;
    }

    public Document setReceiver(Institution receiver) {
        this.receiver = receiver;
        return this;
    }

    public Document setReceiverAddressID(int receiverAddressID) {
        this.receiverAddressID = receiverAddressID;
        return this;
    }

    public Document setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
        return this;
    }

    public Document setReceiverID(int receiverID) {
        this.receiverID = receiverID;
        return this;
    }

    public Document setReceiverInstitutionID(int receiverInstitutionID) {
        this.receiverInstitutionID = receiverInstitutionID;
        return this;
    }

    public Document setSender(Institution sender) {
        this.sender = sender;
        return this;
    }

    public Document setSenderAddressID(int senderAddressID) {
        this.senderAddressID = senderAddressID;
        return this;
    }

    public Document setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
        return this;
    }

    public Document setSenderID(int senderID) {
        this.senderID = senderID;
        return this;
    }

    public Document setSenderInstitutionID(int senderInstitutionID) {
        this.senderInstitutionID = senderInstitutionID;
        return this;
    }

    public Document setSent(boolean sent) {
        isSent = sent;
        return this;
    }

    public boolean isSent() {
        return isSent;
    }

    public Document setToLocation(Address toLocation) {
        this.toLocation = toLocation;
        return this;
    }

    public int getID() {
        return ID;
    }

    public Address getFromLocation() {
        return fromLocation;
    }

    public Address getToLocation() {
        return toLocation;
    }

    public Institution getReceiver() {
        return receiver;
    }

    public Institution getSender() {
        return sender;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public int getReceiverAddressID() {
        return receiverAddressID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public int getReceiverInstitutionID() {
        return receiverInstitutionID;
    }

    public int getSenderAddressID() {
        return senderAddressID;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getSenderInstitutionID() {
        return senderInstitutionID;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateSent() {
        return dateSent;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public String getSenderEmail() {
        return senderEmail;
    }
}

