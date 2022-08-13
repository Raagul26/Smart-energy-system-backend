package com.switchenergysystem.app.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("Providers")
public class Provider {

    @Id
    private String id;

    @NotBlank
    @Size(min = 3, max = 15, message = "must contain min 3 characters")
    private String name;

    @Field("amount_per_kwh")
    private Double amountPerKwh;

    private String status;

}
