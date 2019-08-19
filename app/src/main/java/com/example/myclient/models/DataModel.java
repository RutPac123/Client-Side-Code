package com.example.myclient.models;

public class DataModel {

    private String studioName;
    private String projectDesc;
    private String partyName;
    private String eventDate;
    private String acNo;
    private String clientName;
    private String address;
    private String email;
    private String phoneNo;
    private String totalAmount;
    private String totalAmountPaid;


    public DataModel(){

    }

    public DataModel(String studioName, String projectDesc, String partyName, String eventDate, String acNo, String clientName, String address, String email, String phoneNo, String totalAmount, String totalAmountPaid) {
        this.studioName = studioName;
        this.projectDesc = projectDesc;
        this.partyName = partyName;
        this.eventDate = eventDate;
        this.acNo = acNo;
        this.clientName = clientName;
        this.address =address;
        this.email =email;
        this.phoneNo =phoneNo;
        this.totalAmount = totalAmount;
        this.totalAmountPaid = totalAmountPaid;
    }

    public String getStudioName() {
        return studioName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public String getPartyName() {
        return partyName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getAcNo() {
        return acNo;
    }

    public String getClientName() {
        return clientName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getTotalAmountPaid() {
        return totalAmountPaid;
    }
}
