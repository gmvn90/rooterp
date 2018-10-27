package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dunguyen on 8/8/16.
 */
@Entity
@Table(name = "file_uploads")
public class FileUpload extends AbstractTimestampEntity implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String originalName;

    private String url;

    public FileUpload() {

    }

    public FileUpload(int id) {
        this.id = Long.valueOf(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(Object o) {
        FileUpload fileUpload = (FileUpload) o;
        if (fileUpload.getId() > id) {
            return -1;
        } else {
            return 1;
        }
    }
}
