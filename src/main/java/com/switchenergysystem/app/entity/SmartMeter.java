package com.switchenergysystem.app.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("Smart_meters")
public class SmartMeter {

    @Id
    private String id;

    @NotEmpty(message = "Enter meter id")
    @Field("meter_id")
    private String meterId;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "must be a valid email id")
    @NotEmpty
    @Field("email_id")
    private String emailId;

    @NotEmpty(message = "Enter provider id")
    @Field("provider_id")
    private String providerId;

    private String status;
    private List<Reading> readings;

}
