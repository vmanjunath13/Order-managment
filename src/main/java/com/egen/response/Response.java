package com.egen.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor(staticName = "from")
public class Response<T> {

    @JsonProperty("meta")
    @NonNull ResponseMetaData meta;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    public Response(ResponseMetaData meta , T data){
        this.meta = meta;
        this.data = data;
    }

}
