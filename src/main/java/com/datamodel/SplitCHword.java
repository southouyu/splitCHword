package com.datamodel;

import com.sugon.aus.algorithm.AlgorithmInterface;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @CLASSNAME splitCHword
 * @Description TODO
 * @Author OUY
 * @Date 2019/9/11 16:40
 **/
public class SplitCHword implements AlgorithmInterface{

    @Override
    public void init() {

    }

    @Override
    public Dataset<Row> process(SparkSession sparkSession, Map<String, Object> map, Dataset<Row>... datasets) {
        return SparkUtil.splitCHWord(sparkSession,datasets[0],map);
    }

    @Override
    public void clean() {

    }

    public static void main(String[] args){
        SparkSession spark = SparkSession
                .builder()
                .master("local[2]")
                .appName("splitCHWord")
                .config("spark.sql.caseSensitive","false")
                .getOrCreate();

        String filePath = SparkUtil.class.getClassLoader().getResource("test.csv").getPath();
        Dataset<Row> dataSet = spark.read()
                .format("csv")
                .option("header",true)
                .option("multiLine",true)
                .load(filePath);
        Map<String,Object> paraMap = new HashMap(2);
        paraMap.put("inputCol","feature");
        paraMap.put("outputCol","splitFeature");

        SplitCHword splitCHword = new SplitCHword();
        splitCHword.process(spark,paraMap,dataSet).show(false);
    }
}

