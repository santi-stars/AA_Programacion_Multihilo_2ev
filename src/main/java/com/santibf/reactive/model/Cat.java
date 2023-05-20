package com.santibf.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cat {

    private List<String> tags;
    private String createdAt;
    private String updatedAt;
    private boolean validated;
    private String owner;
    private String file;
    private String mimetype;
    private long size;
    private String _id;
    private String url;

}
