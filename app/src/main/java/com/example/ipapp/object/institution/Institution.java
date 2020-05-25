package com.example.ipapp.object.institution;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Institution {
    private int ID;
    private String name;

    private List<Member> memberList;
    private List<Role> roleList;
    private List<Address> addressList;

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    public String debugToString(){
        return
            "{ID=" +
            this.ID +
            ",name=" +
            this.name +
            ",members=" +
            this.memberList +
            ",roles=" +
            this.roleList +
            ",addresses=" +
            this.addressList +
            "}";
    }

    public Institution setAddressList(List<Address> addressList) {
        this.addressList = addressList;
        return this;
    }

    public Institution setMemberList(List<Member> memberList) {
        this.memberList = memberList;
        return this;
    }

    public Institution setRoleList(List<Role> roleList) {
        this.roleList = roleList;
        return this;
    }

    public Institution(){
        this.memberList = new ArrayList<>();
        this.roleList = new ArrayList<>();
        this.addressList = new ArrayList<>();
    }

    public Institution setName(String name){
        this.name = name;
        return this;
    }

    public Institution setID(int ID){
        this.ID = ID;
        return this;
    }

    public Institution addMembers(Collection<Member> members){
        this.memberList.addAll(members);
        return this;
    }

    public Institution addRoles(Collection<Role> roles){
        this.roleList.addAll(roles);
        return this;
    }

    public Institution addAddresses(Collection<Address> addresses){
        this.addressList.addAll(addresses);
        return this;
    }

    public int getID() {
        return ID;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public String getName() {
        return name;
    }
}
