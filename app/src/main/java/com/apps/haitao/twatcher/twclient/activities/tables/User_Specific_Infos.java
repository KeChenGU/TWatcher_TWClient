package com.apps.haitao.twatcher.twclient.activities.tables;

public class User_Specific_Infos {

    private String specific_attribute;

    private String specific_info;

    public User_Specific_Infos(String specific_attribute, String specific_info) {
        this.specific_attribute = specific_attribute;
        this.specific_info = specific_info;
    }

    public String getSpecific_attribute() {
        return specific_attribute;
    }

    public void setSpecific_attribute(String specific_attribute) {
        this.specific_attribute = specific_attribute;
    }

    public String getSpecific_info() {
        return specific_info;
    }

    public void setSpecific_info(String specific_info) {
        this.specific_info = specific_info;
    }
}
