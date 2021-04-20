

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;

public class DruidFactory2 {
    private static DruidDataSource dataSource=null;
    public static void init() throws Exception{
        dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("dashenjibie1234");
        dataSource.setUrl("jdbc:mysql://localhost:3306/BlockchainText?serverTimezone=GMT%2B8&useSSL=false");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);

    }
    public static Connection getConnection() throws Exception{
        if(null==dataSource){
            init();
        }
        return dataSource.getConnection();
    }
}
