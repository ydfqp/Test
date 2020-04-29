import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class JedisUtil {



    private synchronized Jedis getJedis() {
        Jedis jedis = new Jedis("47.105.153.130", 6379);
        jedis.auth("ytfq123");
        return jedis;
    }


    public void setList(String key ,List list){
         Jedis jedis = getJedis();
        list.forEach(l->jedis.lpush(key, JSONObject.toJSONString(l)));
        jedis.close();
    }

    public Long set(String key ,Object o){
        Jedis jedis = getJedis();
        Long lpush = jedis.lpush(key, JSONObject.toJSONString(o));
        jedis.close();
        return lpush;
    }

    public <T> List<T> getList(String key){
         Jedis jedis = getJedis();
        List<String> strings = jedis.lrange(key,0,-1);
        jedis.close();
        List<T> list=new ArrayList<>();
        for (String s:strings){
            list.add( JSONObject.parseObject(s, (Type) Student.class));
        }
        return list;
    }

    public String updateList(String key ,Integer index,Object o){
        Jedis jedis = getJedis();
        String lset = jedis.lset(key, index, JSONObject.toJSONString(o));
        jedis.close();
        return lset;
    }

    public Integer getIndex(String id ,String key){
        if (id!=null&&key!=null){
            List<Student> student = getList(key);
            Iterator<Student> iterator = student.iterator();
            int i=0;
            while (iterator.hasNext()){
                Student next = iterator.next();
                System.out.println(next);
                if (id.equals(next.getId())){
                    System.out.println(i);
                    return i;
                }
                i++;
            }
        }
        return  null;
    }

    public Student getStudentById(String id ,String key){
        if (id!=null&&key!=null){
            List<Student> student = getList(key);
            Iterator<Student> iterator = student.iterator();
            int i=0;
            while (iterator.hasNext()){
                Student next = iterator.next();
                System.out.println(next);
                if (id.equals(next.getId())){
                    return next;
                }
                i++;
            }
        }
        return  null;
    }

    public long delList(String key ,Integer index){
        Jedis jedis = getJedis();
        jedis.lset(key,index,"");
        Long lrem = jedis.lrem(key, 1, "");
        jedis.close();
        return lrem;
    }


 public static void main(String[] args){
     JedisUtil jedisUtil = new JedisUtil();
//        List<Student> list=new ArrayList<>(20);
//        for(int i=0;i<30;i++){
//         list.add(new Student(UUID.randomUUID().toString(),"张三"+i,new Date(),"miaoshu",(int)(Math.random()*50+40)));
//     }
//     jedisUtil.setList("student",list);
//     Student student = new Student(UUID.randomUUID().toString(), "张三", new Date(), "update", (int) (Math.random() * 50 + 40));
//     jedisUtil.updateList("student",0,student);
//     jedisUtil.delList("student",0);
//     List<Student> students=jedisUtil.getList("student");
//     Collections.sort(students, (o1,o2)->{return o1.getAvgscore()-o2.getAvgscore();});
//    students.forEach(System.out::println);


//     limit.forEach(System.out::println);
//     jedisUtil.set("student",student);

//     String id = "9d9fd3f0-6ea2-4169-b490-82586df2f19e";
//     if (id!=null){
//         List<Student> student = jedisUtil.getList("student");
//         Iterator<Student> iterator = student.iterator();
//         int i=0;
//         while (iterator.hasNext()){
//             Student next = iterator.next();
//             System.out.println(next);
//             if (id.equals(next.getId())){
//                 System.out.println(i);
//                 break;
//             }
//             i++;
//         }
//     }
//     String birthday = "Apr 28 14:06:36 CST";
//     Date parse = null;
//     SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
//     try {
//         parse = simpleFormatter.parse(birthday);
//     } catch (ParseException e) {
//     }
//     System.out.println(parse);
//     System.out.println(parse.equals(""));
 }
}
