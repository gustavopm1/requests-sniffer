package com.gustavopm1.requestssniffer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BufferBody {

    String url;
    String params;
    Map<String,Object> headers;
    Object body;
}
