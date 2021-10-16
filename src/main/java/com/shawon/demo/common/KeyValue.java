package com.shawon.demo.common;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue implements Serializable {
    //@CsvBindByName(column = "key")
    private String key;

    //@CsvBindByName(column = "value")
    private String value;
}
