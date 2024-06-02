package org.doogle.springbatch.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "This class represents a hero with certain attributes like name, otherName, level, picture, and powers.")
public class Hero {

    @Id
    @Schema(description = "Unique identifier for the hero.", type = "string")
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    @NotNull
    @Size(min = 3, max = 50)
    @Schema(description = "Name of the hero. This field is required and its size should be between 3 and 50 characters.")
    private String name;

    @Schema(description = "Other name of the hero.")
    private String otherName;

    @NotNull
    @Positive
    @Schema(description = "Level of the hero. This field is required and its value should be positive.")
    private Integer level;

    @Schema(description = "Picture of the hero.")
    private String picture;

    @Schema(description = "Powers of the hero.")
    private String powers;
}