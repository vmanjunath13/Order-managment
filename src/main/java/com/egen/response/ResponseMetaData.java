package com.egen.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Data
@Builder
@RequiredArgsConstructor(staticName = "from")
public class ResponseMetaData {

    int statusCode;
    @NonNull
    String statusMessage;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Map<String, Object> tags;

    public ResponseMetaData(int statusCode , String statusMessage, Map<String, Object> tags){
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.tags = tags;
    }
}
