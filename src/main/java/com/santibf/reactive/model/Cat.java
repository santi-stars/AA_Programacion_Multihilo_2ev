package com.santibf.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cat {

    private String _id;
    private List<String> tags;
    private String owner;
    private String createdAt;
    private String updatedAt;

}
