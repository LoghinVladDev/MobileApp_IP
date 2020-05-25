package com.example.ipapp.object.institution;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Role {

    public String toString(){
        return "{id=" + this.ID + ",name=" + this.name + ",rights=" + this.rightsDictionary.toString() + "}";
    }

    public String toViewString(){
        return this.name;
    }

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
    public static final String CAN_APPROVE_DOCUMENTS = "Can_Approve_Documents";

    private int ID;
    private String name;
    private Map<String, Boolean> rightsDictionary;

    public Role(){
        this.rightsDictionary = new HashMap<>();
        this.rightsDictionary.put(CAN_MODIFY_INSTITUTION, false);
        this.rightsDictionary.put(CAN_DELETE_INSTITUTION, false);
        this.rightsDictionary.put(CAN_ADD_MEMBERS, false);
        this.rightsDictionary.put(CAN_REMOVE_MEMBERS, false);
        this.rightsDictionary.put(CAN_UPLOAD_DOCUMENTS, false);
        this.rightsDictionary.put(CAN_PREVIEW_UPLOADED_DOCUMENTS, false);
        this.rightsDictionary.put(CAN_REMOVE_UPLOADED_DOCUMENT, false);
        this.rightsDictionary.put(CAN_SEND_DOCUMENTS, false);
        this.rightsDictionary.put(CAN_PREVIEW_RECEIVED_DOCUMENTS, false);
        this.rightsDictionary.put(CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT, false);
        this.rightsDictionary.put(CAN_REMOVE_RECEIVED_DOCUMENT, false);
        this.rightsDictionary.put(CAN_DOWNLOAD_DOCUMENTS, false);
        this.rightsDictionary.put(CAN_ADD_ROLES, false);
        this.rightsDictionary.put(CAN_REMOVE_ROLES, false);
        this.rightsDictionary.put(CAN_MODIFY_ROLES, false);
        this.rightsDictionary.put(CAN_ASSIGN_ROLES, false);
        this.rightsDictionary.put(CAN_DE_ASSIGN_ROLES, false);
        this.rightsDictionary.put(CAN_APPROVE_DOCUMENTS, false);
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

    public boolean isAllowed(String rightName){
        return this.rightsDictionary.get(rightName);
    }

    /*
    {"Can_Modify_Institution": "1",
                    "Can_Delete_Institution": "1",
                    "Can_Add_Members": "1",
                    "Can_Remove_Members": "0",
                    "Can_Upload_Documents": "0",
                    "Can_Preview_Uploaded_Documents": "1",
                    "Can_Remove_Uploaded_Documents": "1",
                    "Can_Send_Documents": "1",
                    "Can_Preview_Received_Documents": "1",
                    "Can_Preview_Specific_Received_Document": "1",
                    "Can_Remove_Received_Documents": "1",
                    "Can_Download_Documents": "1",
                    "Can_Add_Roles": "1",
                    "Can_Remove_Roles": "1",
                    "Can_Modify_Roles": "1",
                    "Can_Assign_Roles": "1",
                    "Can_Deassign_Roles": "1"
                   }
     */
    public JSONObject getRightsDictionaryJSON() throws JSONException {
        return new JSONObject()
                .put(Role.CAN_MODIFY_INSTITUTION, this.getRightsDictionary().get(Role.CAN_MODIFY_INSTITUTION) ? 1 : 0)
                .put(Role.CAN_DELETE_INSTITUTION, this.getRightsDictionary().get(Role.CAN_DELETE_INSTITUTION) ? 1 : 0)
                .put(Role.CAN_ADD_MEMBERS, this.getRightsDictionary().get(Role.CAN_ADD_MEMBERS) ? 1 : 0)
                .put(Role.CAN_REMOVE_MEMBERS, this.getRightsDictionary().get(Role.CAN_REMOVE_MEMBERS) ? 1 : 0)
                .put(Role.CAN_UPLOAD_DOCUMENTS, this.getRightsDictionary().get(Role.CAN_UPLOAD_DOCUMENTS) ? 1 : 0)
                .put(Role.CAN_PREVIEW_UPLOADED_DOCUMENTS, this.getRightsDictionary().get(Role.CAN_PREVIEW_UPLOADED_DOCUMENTS) ? 1 : 0)
                .put(Role.CAN_REMOVE_UPLOADED_DOCUMENT, this.getRightsDictionary().get(Role.CAN_REMOVE_UPLOADED_DOCUMENT) ? 1 : 0)
                .put(Role.CAN_SEND_DOCUMENTS, this.getRightsDictionary().get(Role.CAN_SEND_DOCUMENTS) ? 1 : 0)
                .put(Role.CAN_PREVIEW_RECEIVED_DOCUMENTS, this.getRightsDictionary().get(Role.CAN_PREVIEW_RECEIVED_DOCUMENTS) ? 1 : 0)
                .put(Role.CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT, this.getRightsDictionary().get(Role.CAN_PREVIEW_SPECIFIC_RECEIVED_DOCUMENT) ? 1 : 0)
                .put(Role.CAN_REMOVE_RECEIVED_DOCUMENT, this.getRightsDictionary().get(Role.CAN_REMOVE_RECEIVED_DOCUMENT) ? 1 : 0)
                .put(Role.CAN_DOWNLOAD_DOCUMENTS, this.getRightsDictionary().get(Role.CAN_DOWNLOAD_DOCUMENTS) ? 1 : 0)
                .put(Role.CAN_ADD_ROLES, this.getRightsDictionary().get(Role.CAN_ADD_ROLES) ? 1 : 0)
                .put(Role.CAN_REMOVE_ROLES, this.getRightsDictionary().get(Role.CAN_REMOVE_ROLES) ? 1 : 0)
                .put(Role.CAN_MODIFY_ROLES, this.getRightsDictionary().get(Role.CAN_MODIFY_ROLES) ? 1 : 0)
                .put(Role.CAN_ASSIGN_ROLES, this.getRightsDictionary().get(Role.CAN_ASSIGN_ROLES) ? 1 : 0)
                .put(Role.CAN_DE_ASSIGN_ROLES, this.getRightsDictionary().get(Role.CAN_DE_ASSIGN_ROLES) ? 1 : 0)
                .put(Role.CAN_APPROVE_DOCUMENTS, this.getRightsDictionary().get(Role.CAN_APPROVE_DOCUMENTS) ? 1 : 0);
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

