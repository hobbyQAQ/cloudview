package com.example.cloudview.utils;

import com.example.cloudview.model.PhotoResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DateUtils {
    public static SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static SimpleDateFormat to = new SimpleDateFormat("yyyy年MM月dd日");
    /**
     *   将photo按日期大小从大到小排列
     *   若日期中天数变化则直接传递数据并清空数据列表
     *    跳到下一个日期
     *    以此类推
     */


//    public static Map<Integer, List<PhotoResult.DataBean>> sortPhotoByDate(List<PhotoResult.DataBean> photos) throws ParseException {
//        Map<Integer, List<PhotoResult.DataBean>> split = new HashMap<>();
//        List<PhotoResult.DataBean> datas = new ArrayList<>();
//        int listPosition = 0;
//        for (int i = 0; i < photos.size()-1; i++) {
//            if(i == photos.size() - 2){
//                //最后有一个数据也要放进去
//                split.put(listPosition,datas);
//            }
//            PhotoResult.DataBean photo1 = photos.get(i);
//            PhotoResult.DataBean photo2 = photos.get(i+1);
//            Date date1 = from.parse(photo1.getDate());
//            Date date2 = from.parse(photo2.getDate());
//            String name1 = to.format(date1);
//            String name2 = to.format(date2);
//            String name = name1;
//            if (!datas.contains(photo1)) {
//                datas.add(photo1);
//            }
//            if(name1.compareTo(name2) == 0){ //如果日期相同
//                if (datas.contains(photo1)) {
//                    datas.add(photo2);
//                }
//            }else{
//                split.put(listPosition,datas); //如果日期不同，则把datas放入split
//                datas = new ArrayList<>(); //新建一个datas
//                listPosition ++;   //索引key++
//            }
//        }
//        return split;
//    }


    public static Map<Integer, List<PhotoResult.DataBean>> sortPhotoByDate(List<PhotoResult.DataBean> photos) throws ParseException {
        Map<Integer, List<PhotoResult.DataBean>> result = new HashMap<>();
        Map<String, List<PhotoResult.DataBean>> split = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (int i = 0; i < photos.size(); i++) {
            PhotoResult.DataBean photo = photos.get(i);
            Date date = from.parse(photo .getDate());
            String key = to.format(date);
            if (split.containsKey(key)) {
                split.get(key).add(photo);
            }else{
                List<PhotoResult.DataBean> value = new ArrayList<>();
                value.add(photo);
                split.put(key,value);
            }
        }
        Set<Map.Entry<String, List<PhotoResult.DataBean>>> entries = split.entrySet();
        Iterator<Map.Entry<String, List<PhotoResult.DataBean>>> iterator = entries.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            List<PhotoResult.DataBean> value = iterator.next().getValue();
            result.put(count++,value);
        }
        return result;
    }

    public static void sort(List<PhotoResult.DataBean> photos) {
        photos.sort(new Comparator<PhotoResult.DataBean>() {
            @Override
            public int compare(PhotoResult.DataBean o1, PhotoResult.DataBean o2) {
                return o1.getDate().compareTo(o2.getDate()) ;
            }
        });
    }





    public static String theirTime2ourTime(String time) throws ParseException {
        Date date = from.parse(time);
        return to.format(date);
    }



}
