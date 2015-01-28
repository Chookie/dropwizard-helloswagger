package com.example.helloworld.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * Created by Alison on 24/01/15
 */
@ApiModel(value = "A pet is a person's best friend")
public class Saying {
    private long id;

    @Length(max = 30)
    private String content;

    @JsonCreator
    public Saying(@JsonProperty("id") long id, @JsonProperty("content") String content) {
        this.id = id;
        this.content = content;
    }

    @SuppressWarnings("unused")
    @JsonProperty
    @ApiModelProperty(value = "Order Status", required=true, allowableValues = "placed,approved,delivered")
    public long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    @JsonProperty
    @ApiModelProperty(value = "Order Status", required=true, allowableValues = "placed,approved,delivered")
    public String getContent() {
        return content;
    }
}
