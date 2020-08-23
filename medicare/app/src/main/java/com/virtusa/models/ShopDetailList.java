
package com.virtusa.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "pharmacyMasterId",
    "pharmacyName",
    "isRegistered",
    "addressLine",
    "area",
    "city",
    "state",
    "postalCode",
    "country",
    "latitude",
    "longtitude",
    "openTime",
    "closedTime",
    "phone",
    "webSite",
    "drugList"
})
public class ShopDetailList   implements Serializable {

    @JsonProperty("pharmacyMasterId")
    private String pharmacyMasterId;
    @JsonProperty("pharmacyName")
    private String pharmacyName;
    @JsonProperty("isRegistered")
    private String isRegistered;
    @JsonProperty("addressLine")
    private String addressLine;
    @JsonProperty("area")
    private String area;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("country")
    private String country;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longtitude")
    private String longtitude;
    @JsonProperty("openTime")
    private String openTime;
    @JsonProperty("closedTime")
    private String closedTime;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("webSite")
    private String webSite;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonProperty("drugList")
    private List<DrugList> drugList = null;
    private Double Km;
    private Double totalAmount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pharmacyMasterId")
    public String getPharmacyMasterId() {
        return pharmacyMasterId;
    }

    @JsonProperty("pharmacyMasterId")
    public void setPharmacyMasterId(String pharmacyMasterId) {
        this.pharmacyMasterId = pharmacyMasterId;
    }

    public Double getKm() {
        return Km;
    }

    public void setKm(Double km) {
        Km = km;
    }

    @JsonProperty("pharmacyName")
    public String getPharmacyName() {
        return pharmacyName;
    }

    @JsonProperty("pharmacyName")
    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    @JsonProperty("isRegistered")
    public String getIsRegistered() {
        return isRegistered;
    }

    @JsonProperty("isRegistered")
    public void setIsRegistered(String isRegistered) {
        this.isRegistered = isRegistered;
    }

    @JsonProperty("addressLine")
    public String getAddressLine() {
        return addressLine;
    }

    @JsonProperty("addressLine")
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    @JsonProperty("area")
    public String getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(String area) {
        this.area = area;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longtitude")
    public String getLongtitude() {
        return longtitude;
    }

    @JsonProperty("longtitude")
    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    @JsonProperty("openTime")
    public String getOpenTime() {
        return openTime;
    }

    @JsonProperty("openTime")
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    @JsonProperty("closedTime")
    public String getClosedTime() {
        return closedTime;
    }

    @JsonProperty("closedTime")
    public void setClosedTime(String closedTime) {
        this.closedTime = closedTime;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("webSite")
    public String getWebSite() {
        return webSite;
    }

    @JsonProperty("webSite")
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    @JsonProperty("drugList")
    public List<DrugList> getDrugList() {
        return drugList;
    }

    @JsonProperty("drugList")
    public void setDrugList(List<DrugList> drugList) {
        this.drugList = drugList;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
