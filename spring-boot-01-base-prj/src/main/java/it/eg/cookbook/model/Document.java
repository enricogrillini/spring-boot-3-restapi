package it.eg.cookbook.model;

import java.time.LocalDate;


public class Document {

    private Integer id;
    private String name;
    private String description;
    private LocalDate data;
    private String updateBy;

    public Document(Integer id, String name, String description, LocalDate data, String updateBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.data = data;
        this.updateBy = updateBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

}

