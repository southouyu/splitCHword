package com.datamodel;

import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.execution.SparkSqlParser;
import org.apache.spark.sql.internal.SQLConf;
import org.apache.spark.sql.types.DataTypes;

import java.util.Map;

/**
 * @CLASSNAME sparkUtil
 * @Description SparkUDF开发
 * @Author OUY
 * @Date 2019/9/11 16:58
 **/
public class SparkUtil {

//    SparkSession session;
//    public SparkSession CreateSession(){
//        SparkSession spark = SparkSession
//                .builder()
//                .master("local[2]")
//                .appName("splitCHWord")
//                .config("spark.sql.caseSensitive","false")
//                .getOrCreate();
//        return spark;
//    }

//    public  Dataset<Row> readDataFromCsv(){
//       return this.CreateSession().read()
//                .format("csv")
//                .option("header",true)
//                .option("multiLine",true)
//                .load(getFilePath());
//    }

    private static void regUDF(SparkSession session,UDF1 udf){
        session.udf().register("splitCHword",udf,DataTypes.StringType);
    }
//
//    public  String getFilePath(){
//       return SparkUtil.class.getClassLoader().getResource("test.csv").getPath();
//    }

    private static void createUDF(SparkSession session){
        UDF1 udf = new UDF1<String,String>(){
            @Override
            public String call(String t1) throws Exception{
                return SplitCHwordUtil.splitDetail(t1);
            }
        };
        regUDF(session,udf);
    }

    public static Dataset<Row> splitCHWord(SparkSession session,Dataset<Row> dataset,Map<String,Object> paraMap){
        createUDF(session);
        SparkSqlParser sparkSqlParser = new SparkSqlParser(new SQLConf());
        StringBuilder splitSQL = new StringBuilder("splitCHword(");
        splitSQL.append(paraMap.get("inputCol").toString()+")");
        dataset = dataset.withColumn(paraMap.get("outputCol").toString(),
                new Column(sparkSqlParser.parseExpression(splitSQL.toString())));
        return dataset;
    }
}

