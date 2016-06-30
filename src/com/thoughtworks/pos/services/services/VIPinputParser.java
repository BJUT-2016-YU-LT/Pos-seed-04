package com.thoughtworks.pos.services.services;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;
import com.thoughtworks.pos.domains.UserInfo;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by pyggy on 2016/6/29.
 */
public class VIPinputParser {
    private File file;

    private final ObjectMapper objectMapper;

    public VIPinputParser(File file) {
        this.file=file;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }
   public UserInfo parser(String bar) throws IOException{
    return GetAUserAccordingtoBar(bar,getUserInfos());
   }

    private UserInfo GetAUserAccordingtoBar(String bar,HashMap<String,UserInfo> UserInfos){
        UserInfo userInfo=UserInfos.get(bar);
    return new UserInfo(bar,userInfo.getUserName(),userInfo.getUserIsVIP(),userInfo.getUserPoints());

    }
    private HashMap<String,UserInfo> getUserInfos() throws IOException{
        String UserInfosStr=FileUtils.readFileToString(file);
        TypeReference<HashMap<String,UserInfo>> typeRef=new TypeReference<HashMap<String,UserInfo>>() {};
        return objectMapper.readValue(UserInfosStr,typeRef);


    }

}
