package com.example.oauth2.security.smsCode;


import lombok.Data;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Data
class MobileSms {
    private String mobile;
    private String smsCode;
}

class SmsCodeUtils {

    public static MobileSms obtainMobile(HttpServletRequest request) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(),
                    "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = bufferedReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            ObjectMapper mapper = new ObjectMapper();
            MobileSms sms = mapper.readValue(responseStrBuilder.toString(), MobileSms.class);
            return sms;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}




