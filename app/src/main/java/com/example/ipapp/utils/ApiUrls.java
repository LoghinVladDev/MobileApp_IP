package com.example.ipapp.utils;

public class ApiUrls {
    private static final String API_ROOTPATH = "https://fiscaldocumentsapi.azurewebsites.net";

    //<editor-fold desc="ACCOUNT">
    public static final String ACCOUNT_LOGIN = API_ROOTPATH + "/Account/Login.php";
    public static final String ACCOUNT_CREATE = API_ROOTPATH + "/Account/Create.php";
    public static final String ACCOUNT_MODIFY = API_ROOTPATH + "/Account/Modify.php";
    public static final String ACCOUNT_RETRIEVE_INFORMATION = API_ROOTPATH + "/Account/Retrieve.php";
    //</editor-fold>

    //<editor-fold desc="INSTITUTIONS">

    //<editor-fold desc="BASIC_OPERATIONS">
    public static final String INSTITUTION_CREATE = API_ROOTPATH + "/Institution/Create.php";
    public static final String INSTITUTION_MODIFY = API_ROOTPATH + "/Institution/Modify.php";
    public static final String INSTITUTION_DELETE = API_ROOTPATH + "/Institution/Delete.php";
    public static final String INSTITUTION_RETRIEVE_INFORMATION = API_ROOTPATH + "/Institution/Retrieve.php";
    public static final String INSTITUTION_RETRIEVE_ADDRESSES = API_ROOTPATH + "/Institution/RetrieveAddresses.php";
    public static final String INSTITUTION_RETRIEVE_ALL = API_ROOTPATH + "/Institution/RetrieveAll.php";
    //</editor-fold>

    //<editor-fold desc="ROLES">
    public static final String INSTITUTION_ROLE_CREATE = API_ROOTPATH + "/Institution/Role/Create.php";
    public static final String INSTITUTION_ROLE_MODIFY = API_ROOTPATH + "/Institution/Role/Modify.php";
    public static final String INSTITUTION_ROLE_DELETE = API_ROOTPATH + "/Institution/Role/Delete.php";
    public static final String INSTITUTION_ROLE_RETRIEVE_ALL = API_ROOTPATH + "/Institution/Role/Retrieve.php";
    //</editor-fold>

    //<editor-fold desc="MEMBER OPERATIONS">
    public static final String INSTITUTION_MEMBER_ADD = API_ROOTPATH + "/Institution/Member/Add.php";
    public static final String INSTITUTION_MEMBER_MODIFY_ROLE = API_ROOTPATH + "/Institution/Member/Modify.php";
    public static final String INSTITUTION_MEMBER_REMOVE = API_ROOTPATH + "/Institution/Member/Remove.php";
    public static final String INSTITUTION_MEMBER_RETRIEVE_MEMBERS = API_ROOTPATH + "/Institution/Member/Retrieve.php";
    public static final String INSTITUTION_MEMBER_RETRIEVE_INSTITUTIONS_FOR_MEMBER = API_ROOTPATH + "/Institution/Member/RetrieveInstitutions.php";
    //</editor-fold>

    //<editor-fold desc="CONTACT INFORMATION OPERATIONS">
    public static final String INSTITUTION_CONTACT_INFORMATION_INSERT = API_ROOTPATH + "/Institution/Contact/Insert.php";
    public static final String INSTITUTION_CONTACT_INFORMATION_EDIT = API_ROOTPATH + "/Institution/Contact/Edit.php";
    public static final String INSTITUTION_CONTACT_INFORMATION_DELETE = API_ROOTPATH + "/Institution/Contact/Delete.php";
    public static final String INSTITUTION_CONTACT_INFORMATION_RETRIEVE = API_ROOTPATH + "/Institution/Contact/Retrieve.php";
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="DOCUMENTS">

    //<editor-fold desc="BASIC OPERATIONS">
    public static final String DOCUMENT_UPLOAD_INVOICE = API_ROOTPATH + "/Document/UploadInvoice.php";
    public static final String DOCUMENT_UPLOAD_RECEIPT = API_ROOTPATH + "/Document/UploadReceipt.php";
    public static final String DOCUMENT_MODIFY_INVOICE = API_ROOTPATH + "/Document/ModifyInvoice.php";
    public static final String DOCUMENT_MODIFY_RECEIPT = API_ROOTPATH + "/Document/ModifyReceipt.php";
    public static final String DOCUMENT_DELETE_INVOICE = API_ROOTPATH + "/Document/DeleteInvoice.php";
    public static final String DOCUMENT_DELETE_RECEIPT = API_ROOTPATH + "/Document/DeleteReceipt.php";
    //</editor-fold>

    //<editor-fold desc="DOC HANDLING">
    public static final String DOCUMENT_SEND = API_ROOTPATH + "/Document/Send.php";
    public static final String DOCUMENT_RETRIEVE_INDIVIDUAL = API_ROOTPATH + "/Document/Retrieve.php";
    //</editor-fold>

    //<editor-fold desc="MISC OPERATIONS">
    public static final String DOCUMENT_MIST_RETRIEVE_CURRENCIES = API_ROOTPATH + "/Document/Misc/RetrieveCurrencies.php";
    public static final String DOCUMENT_MIST_RETRIEVE_PAYMENT_METHODS = API_ROOTPATH + "/Document/Misc/RetrievePaymentMethods.php";
    //</editor-fold>

    //<editor-fold desc="FILTERS">
    public static final String DOCUMENT_RETRIEVE_FILTER_USER_CREATED = API_ROOTPATH + "/Document/Filters/UserCreated.php";
    public static final String DOCUMENT_RETRIEVE_FILTER_USER_SENT = API_ROOTPATH + "/Document/Filters/UserSent.php";
    public static final String DOCUMENT_RETRIEVE_FILTER_USER_RECEIVED = API_ROOTPATH + "/Document/Filters/UserReceived.php";

    public static final String DOCUMENT_RETRIEVE_FILTER_INSTITUTION_CREATED = API_ROOTPATH + "/Document/Filters/InstitutionCreated.php";
    public static final String DOCUMENT_RETRIEVE_FILTER_INSTITUTION_SENT = API_ROOTPATH + "/Document/Filters/InstitutionSent.php";
    public static final String DOCUMENT_RETRIEVE_FILTER_INSTITUTION_RECEIVED = API_ROOTPATH + "/Document/Filters/InstitutionReceived.php";
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="INGNORE FOR NOW BRO">
    public static final String NEWSFEED_CREATE = API_ROOTPATH + "/Newsfeed/Create.php";
    public static final String NEWSFEED_MODIFY = API_ROOTPATH + "/Newsfeed/Modify.php";
    public static final String NEWSFEED_DELETE = API_ROOTPATH + "/Newsfeed/Delete.php";
    public static final String NEWSFEED_RETRIEVE_ALL = API_ROOTPATH + "/Newsfeed/Retrieve.php";
    //</editor-fold>

}
