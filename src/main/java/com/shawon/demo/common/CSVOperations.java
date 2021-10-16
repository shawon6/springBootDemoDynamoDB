package com.shawon.demo.common;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
public class CSVOperations {
    private static final String SAMPLE_CSV_FILE_PATH_R = "D:\\csv\\keyValue.csv";
    private static final String SAMPLE_CSV_FILE_PATH_W = "D:\\csv\\keyValueW.csv";

    public static List<KeyValue> csvToBean() throws IOException {
        List<KeyValue> keyValueList = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH_R));
        ) {
            CsvToBean<KeyValue> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(KeyValue.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            //return csvToBean.parse(); //Not good for memory

            Iterator<KeyValue> csvUserIterator = csvToBean.iterator();

            while (csvUserIterator.hasNext()) {
                KeyValue keyValue = csvUserIterator.next();
                keyValueList.add(keyValue);
            }
            System.out.println("keyValueList.toString() = " + keyValueList.toString());
        }
        return keyValueList;
    }

    public static void write(List<KeyValue> keyValues) throws Exception{
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE_PATH_W));
        ) {
            StatefulBeanToCsv<KeyValue> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsv.write(keyValues);
        }
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(csvToBean().toString());
        write(csvToBean());

    }

}
