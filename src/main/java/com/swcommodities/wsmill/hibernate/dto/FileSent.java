/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by dunguyen on 8/8/16.
 */
@Entity
@Table(name = "file_sents")

public class FileSent extends AbstractTimestampEntity implements Comparable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Override
    public int compareTo(Object o) {
        FileSent fileSent = (FileSent) o;
        if (fileSent.getId() > id) {
            return -1;
        } else {
            return 1;
        }
    }

    public FileSent() {

    }

    public FileSent(int id) {
        this.id = Long.valueOf(id);
    }

    public FileSent(FileUpload fileUpload, String updater, String remindName) {
        this.fileUpload = fileUpload;
        this.updater = updater;
        this.name = fileUpload.getName();
        this.remindName = remindName;
    }

    private String updater;

    private String name;

    private String emails;

    private String remindName;

    @OneToOne
    private FileUpload fileUpload;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getRemindName() {
        return remindName;
    }

    public void setRemindName(String remindName) {
        this.remindName = remindName;
    }

}
