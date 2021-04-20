import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.ResultSetMetaData;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectAll {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DruidFactory2.getConnection();
            //构建数据库执行者
            Statement stmt = conn.createStatement();
            //执行SQL语句并返回结果到ResultSet
            ResultSet rs = stmt.executeQuery("select * from Block1");
            JSONObject jsonObj = new JSONObject();//创建json格式的数据
            JSONArray jsonArr = new JSONArray();//json格式的数组
            int i=1;
            while(rs.next()){
                ResultSetMetaData data= (ResultSetMetaData) rs.getMetaData();
//                System.out.println(data.getColumnLabel(2));
//                System.out.println(rs.getArray(i));

                try {
                    JSONObject jsonObjArr = new JSONObject();
                    jsonObjArr.put(data.getColumnLabel(2), rs.getString(2));
                    jsonObjArr.put(data.getColumnLabel(3), rs.getString(3));
                    jsonObjArr.put(data.getColumnLabel(4), rs.getString(4));
                    jsonObjArr.put(data.getColumnLabel(6), rs.getString(6));
                    jsonArr.add(jsonObjArr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            jsonObj.put("block", jsonArr);//再将这个json格式的的数组放到最终的json对象中。
            try(DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream("../Text418/fei.txt",true)))){
                dos.write(jsonObj.toString().getBytes());
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
