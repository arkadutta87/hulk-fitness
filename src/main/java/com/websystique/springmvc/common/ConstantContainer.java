package com.websystique.springmvc.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arkadutta on 18/08/16.
 */
public class ConstantContainer {
    private static final Logger logger = LoggerFactory.getLogger(ConstantContainer.class);
    private static ConstantContainer ourInstance = new ConstantContainer();

    public static ConstantContainer getInstance() {
        return ourInstance;
    }

    private static final String CONFIGURATION_FILE = "/etc/env/epme.properties";
    private String REDIS_URI = null;


    private ConstantContainer() {

        FileInputStream reader;

        try{
            reader = new FileInputStream(CONFIGURATION_FILE);

            Properties prop = new Properties();
            try{
                prop.load(reader);
                /*String dishCoreName = prop.getProperty("dishSuggesterCore");
                String gcGscCoreName = prop.getProperty("gcGSCCore");
                String dishItemCoreName = prop.getProperty("dishItemCore");
                CATEGORY_CORE = prop.getProperty("categorizationCore");
                dishSuggestionCore = new HttpSolrClient(dishCoreName);
                gcGscCore = new HttpSolrClient(gcGscCoreName);
                dishItemCore = new HttpSolrClient(dishItemCoreName);*/
                REDIS_URI = prop.getProperty("redisUri");


            }catch(IOException e1){
                logger.error(e1.getMessage());
            }


        } catch(FileNotFoundException e){
            logger.error(e.getMessage());
        }
    }

    public String getREDIS_URI(){
        return REDIS_URI;
    }
}
