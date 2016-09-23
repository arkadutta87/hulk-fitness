package com.websystique.springmvc.bin;

import com.websystique.springmvc.common.PackageDurationUnitEnum;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by arkadutta on 13/09/16.
 */
public class SendSMS {

    public static void main(String[] args){

        System.out.println(sendSms());
        //printDateDiff();
    }

    private static void printDateDiff(){
        String str1 = "2016-09-18 23:59:59";
        String str2  = "2016-09-20 23:59:59";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(str1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = date2.getTime() - date1.getTime();
        System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

    }

    private static void calculateExpirayDate(){

        String durationUnit = "MONTHS";//aPkg.getDurationUnit();
        double durationValue = 1.00;//aPkg.getDurationValue();

        String enrollmentDateStr = "09/18/2016";//request.getPackage_enrollment_date();
        int topUpDays = 2 ;//request.getTop_up_time();
        Date dt = null;


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            dt = simpleDateFormat.parse(enrollmentDateStr);
        }catch(Exception e){
            e.printStackTrace();
        }

        Date dt2 = dt;
        if(durationValue > 0){
            if(PackageDurationUnitEnum.DAYS.value.equals(durationUnit.toUpperCase())){
                Calendar c = Calendar.getInstance();
                c.setTime(dt); // Now use today date.
                c.add(Calendar.DATE, new Double(durationValue).intValue()-1);//Add 100 year

                dt2 = c.getTime();


            }else if((PackageDurationUnitEnum.MONTHS.value.equals(durationUnit.toUpperCase()))){
                Calendar c = Calendar.getInstance();
                c.setTime(dt); // Now use today date.
                c.add(Calendar.MONTH, new Double(durationValue).intValue());//Add 100 year

                dt2 = c.getTime();

                c = Calendar.getInstance();
                c.setTime(dt2); // Now use today date.
                c.add(Calendar.DATE, -1);

                dt2 = c.getTime();

            }
        }

        Date dt3 = dt2 ;
        if(topUpDays > 0 ){

            Calendar c = Calendar.getInstance();
            c.setTime(dt2); // Now use today date.
            c.add(Calendar.DATE, topUpDays);//Add 100 year

            dt3 = c.getTime();
        }

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(dt3);

        dateStr += " 23:59:59";

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date adt1 = null;
        try {
            adt1= simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            adt1 = new Date();
        }

        System.out.println(adt1);


    }

    private static void calculatePrice(){

        double price = 9480.99;
        double discount_percentage = 25.25;

        double final_price = price - (price*discount_percentage/100);
        final_price = Math.round( final_price * 100.0 ) / 100.0;

        System.out.println("The final price is --- "+final_price);
    }

    public static String sendSms() {
        try {
            // Construct data
            String user = "username=" + "arka.dutta@binaryworkers.com";
            String hash = "&hash=" + "8f3f9d872d9f57720938250efdc6edc4a0aad2f1";
            String message = "&message=" +"your dynamic otp is 3245678953"; //"&message=" + "Hello Shalmoli Dutta you are enrolled to the Puja Bumper Package on 13th September, 2016. Your member is 01 , " +
                    //"please mention this while correspondence.";
            String sender = "&sender=" + "HULKFC";
            String numbers = "&numbers=" + "9742019093";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
            String data = user + hash + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}
