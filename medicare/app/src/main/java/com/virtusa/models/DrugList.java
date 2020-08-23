
package com.virtusa.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "pharmacyMasterId",
    "drugId",
    "drugName",
    "isActive",
    "mappingId",
    "isAvailable",
    "drugPriceEach",
    "currency"
})
public class DrugList implements Serializable {

    @JsonProperty("pharmacyMasterId")
    private String pharmacyMasterId;
    @JsonProperty("drugId")
    private String drugId;
    @JsonProperty("drugName")
    private String drugName;
    @JsonProperty("isActive")
    private String isActive;
    @JsonProperty("mappingId")
    private String mappingId;
    @JsonProperty("isAvailable")
    private String isAvailable;
    @JsonProperty("drugPriceEach")
    private Double drugPriceEach;
    @JsonProperty("currency")
    private String currency;
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

    @JsonProperty("drugId")
    public String getDrugId() {
        return drugId;
    }

    @JsonProperty("drugId")
    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    @JsonProperty("drugName")
    public String getDrugName() {
        return drugName;
    }

    @JsonProperty("drugName")
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    @JsonProperty("isActive")
    public String getIsActive() {
        return isActive;
    }

    @JsonProperty("isActive")
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @JsonProperty("mappingId")
    public String getMappingId() {
        return mappingId;
    }

    @JsonProperty("mappingId")
    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    @JsonProperty("isAvailable")
    public String getIsAvailable() {
        return isAvailable;
    }

    @JsonProperty("isAvailable")
    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    @JsonProperty("drugPriceEach")
    public Double getDrugPriceEach() {
        return drugPriceEach;
    }

    @JsonProperty("drugPriceEach")
    public void setDrugPriceEach(Double drugPriceEach) {
        this.drugPriceEach = drugPriceEach;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
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
