package com.example.ipapp.object.institution;

import java.util.HashMap;
import java.util.Map;

public class Role {

    public static final String CAN_MODIFY_INSTITUTION = "Can_Modify_Institution";
    public static final String CAN_DELETE_INSTITUTION = "Can_Delete_Institution";
    public static final String CAN_ADD_MEMBERS = "Can_Add_Members";
    public static final String CAN_REMOVE_MEMBERS = "Can_Remove_Members";
    public static final String CAN_UPLOAD_DOCUMENTS = "Can_Upload_Documents";
    public static final String CAN_PREVIEW_UPLOADED_DOCUMENTS = "Can_Preview_Uploaded_Documents";
    public static final String CAN_REMOVE_UPLOADED_DOCUMENT = "Can_Remove_Uploaded_Documents";
    public static final String CAN_SEND_DOCUMENTS = "Can_Send_Documents";
    public static final String CAN_PREVIEW_RECEIVED_DOCUMENTS = "Can_Preview_Received_Documents";
    public static final String CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT = "Can_Preview_Specific_Received_Document";
    public static final String CAN_REMOVE_RECEIVED_DOCUMENT = "Can_Remove_Received_Documents";
    public static final String CAN_DOWNLOAD_DOCUMENTS = "Can_Download_Documents";
    public static final String CAN_ADD_ROLES = "Can_Add_Roles";
    public static final String CAN_REMOVE_ROLES = "Can_Remove_Roles";
    public static final String CAN_MODIFY_ROLES = "Can_Modify_Roles";
    public static final String CAN_ASSIGN_ROLES = "Can_Assign_Roles";
    public static final String CAN_DE_ASSIGN_ROLES = "Can_Deassign_Roles";

    private int ID;
    private String name;
    private Map<String, Boolean> rightsDictionary;

    public Role(){
        this.rightsDictionary = new HashMap<>();
    }

    public Role setID(int ID) {
        this.ID = ID;
        return this;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public Role setRightsDictionary(Map<String, Boolean> rightsDictionary) {
        this.rightsDictionary = rightsDictionary;
        return this;
    }

    public Role setRight(String rightName, boolean toggle){
        this.rightsDictionary.put(rightName, toggle);
        return this;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Map<String, Boolean> getRightsDictionary() {
        return rightsDictionary;
    }
}

