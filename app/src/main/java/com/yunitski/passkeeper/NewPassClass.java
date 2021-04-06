package com.yunitski.passkeeper;

public class NewPassClass {

    String nameOfResource;
    String link;
    String password;

    public NewPassClass(String nameOfResource, String link, String password) {
        this.nameOfResource = nameOfResource;
        this.link = link;
        this.password = password;
    }

    public NewPassClass(String nameOfResource, String password) {
        this.nameOfResource = nameOfResource;
        this.password = password;
    }

    public String getNameOfResource() {
        return nameOfResource;
    }

    public void setNameOfResource(String nameOfResource) {
        this.nameOfResource = nameOfResource;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
