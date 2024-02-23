package it.eg.cookbook.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class DocumentPojo {

    private Integer id;
    private String name;
    private String description;
    private LocalDate data;
    private String updateBy;

}
