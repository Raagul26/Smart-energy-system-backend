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
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("Users")
public class User {

    @Id
    private String id;

    @NotEmpty(message = "Enter user name")
    private String name;

    @Field("email_id")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "must be a valid email id")
    @NotEmpty
    private String emailId;

    @NotEmpty(message = "Enter valid password")
    @Size(min = 5, message = "should contain min 5 characters")
    private String password;

    @NotEmpty(message = "Enter role of the user")
    private String role;

    private String status;

}
