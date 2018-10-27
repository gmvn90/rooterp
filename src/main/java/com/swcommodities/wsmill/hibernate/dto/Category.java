package com.swcommodities.wsmill.hibernate.dto;


import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.swcommodities.wsmill.hibernate.dto.serializer.CategorySerializer;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@JsonSerialize(using = CategorySerializer.class)
@Table(name = "categories")
@Inheritance(strategy=InheritanceType.JOINED)
public class Category extends AbstractTimestampEntity implements Comparable {
    public Category() {

    }

    public Category(String name) {
        // TODO Auto-generated constructor stub
        this.name = name;
    }

    public Category(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // The user's email
    @NotNull
    private String name;

    @OneToMany(mappedBy="category", cascade= CascadeType.REMOVE, fetch = FetchType.EAGER)
    @OrderBy("id asc")
    @Sort(type = SortType.NATURAL)
    private SortedSet<OperationalCost> options;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("id asc")
    @Sort(type = SortType.NATURAL)
    private SortedSet<Category> children = new TreeSet<>();


    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

    public SortedSet<Category> getChildren() {
        return children;
    }

    public void setChildren(SortedSet<Category> children) {
        this.children = children;
    }

    public void addChildren(Category category) {
        if(category != null) {
            if(children == null) {
                children = new TreeSet<>();
            }
            children.add(category);
            category.setParent(this);
        }
    }

    public SortedSet<OperationalCost> getOptions() {
        return options;
    }

    public SortedSet<OperationalCost> getChildOptions() {
        SortedSet<OperationalCost> options = new TreeSet<>();
        for(Category category: getChildren()) {
            options.addAll(category.getOptions());
        }
        return options;
    }

    public void setOptions(SortedSet<OperationalCost> options) {
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {
        Category cat = (Category) o;
        return Long.compare(id, cat.getId());
    }



}