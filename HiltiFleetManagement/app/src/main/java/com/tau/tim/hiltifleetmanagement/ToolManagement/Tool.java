package com.tau.tim.hiltifleetmanagement.ToolManagement;

/**
 * Created by Tim on 11/4/2015.
 */
public class Tool {

    private String toolName = "";
    private String modelNumber = "";
    private double price = 0.0;
    private String features = "";
    private String description = "";
    private String effectiveDate ="";
    private int toolID = 0;
    private int clientID = 0;
    private String ownership = "";
    private String status = "";
    private String purchaseDate = "";
    private String returnDate = "";
    private String replacementID = "";
    private int toolCount = 0;

    public Tool(){};

    public Tool(String toolName, String modelNumber, String description, int toolCount){
        this.toolName = toolName;
        this.modelNumber = modelNumber;
        this.description = description;
        this.toolCount = toolCount;
    }

    public Tool(int toolID, String toolName, String toolModelNumber, String ownership, String status, String purchaseDate, String returnDate){
        this.toolID = toolID;
        this.toolName = toolName;
        this.modelNumber = toolModelNumber;
        this.ownership = ownership;
        this.status = status;
        this.purchaseDate = purchaseDate;
        this.returnDate = returnDate;
    }

    public String getModelNumber(){
        return modelNumber;
    }

    public void setModelNumber(String modelNumber){
        this.modelNumber = modelNumber;
    }

    public String getReplacementID() {
        return replacementID;
    }

    public void setReplacementID(String replacementID) {
        this.replacementID = replacementID;
    }

    public int getToolID() {
        return toolID;
    }

    public void setToolID(int toolID) {
        this.toolID = toolID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setToolCount(int toolCount){
        this.toolCount = toolCount;
    }

    public int getToolCount(){
        return toolCount;
    }
}
