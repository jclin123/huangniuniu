package Test;

import com.huangniuniu.movie.pojo.Movie;

import java.util.Calendar;
import java.util.Date;

public class test {
    public static void main(String[] args) {

        Date date = new Date();
        System.out.println(date);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH,-3);
        Date time = instance.getTime();
        System.out.println(time);
    }


}
