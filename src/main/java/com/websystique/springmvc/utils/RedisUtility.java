package com.websystique.springmvc.utils;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.protocol.SetArgs;
import com.websystique.springmvc.common.ConstantContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arkadutta on 18/08/16.
 */
public class RedisUtility {

    private RedisClient redisClient;
    private RedisConnection<String, String> connection;

    private static final Logger logger = LoggerFactory.getLogger(RedisUtility.class);
    private static RedisUtility ourInstance = new RedisUtility();

    public static RedisUtility getInstance() {
        return ourInstance;
    }

    private RedisUtility(){
        redisClient = new RedisClient(
                RedisURI.create(ConstantContainer.getInstance().getREDIS_URI()));
        //connection = redisClient.connect();
        logger.info("Connected to Redis");
    }

    public String getVal(String key){
        String val = null;

        if(redisClient!= null){
            connection = redisClient.connect();
            val = connection.get(key);
            logger.info("Have got the value for Key - "+key + " , Value - "+val);
            connection.close();
        }

        return val;
    }

    public void deleteKey(String key){
        if(redisClient != null){
            connection = redisClient.connect();
            connection.del(key);
            logger.info("Have deleted the Key - "+key );
        }
    }

    public void shutdown(){
        if(redisClient!= null ){
            redisClient.shutdown();
            redisClient = null;
        }
    }

    public boolean setKeyVal(String key, String val){
        if(redisClient!= null){
            connection = redisClient.connect();
            connection.set(key,val);
            logger.info("Have set Key - "+key + " , Value - "+val);
            connection.close();
            return true;
        }else{
            return false;
        }

    }

    public boolean setKeyValTTL(String key , String val ,int sec){
        if(redisClient!= null){
            connection = redisClient.connect();
            //LettuceConve.toSetArgs(Expiration expiration, RedisStringCommands.SetOption option)
            SetArgs arg  =  new SetArgs();
            arg.ex(sec);
            connection.set(key,val,arg);
            logger.info("Have set Key - "+key + " , Value - "+val);
            connection.close();
            return true;
        }else{
            return false;
        }
    }
}
