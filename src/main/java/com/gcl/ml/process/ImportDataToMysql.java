package com.gcl.ml.process;

import com.gcl.ml.util.ReadDataFromCsvToMysql;
import com.gcl.ml.util.ReadUserLabelToMysql;

/**
 * Created by 14557 on 2017/4/28.
 */
public class ImportDataToMysql {
    public static void main(String[] args) {
        System.out.println("大概需要两分钟时间，或许更长，请耐心等待。");
        System.out.println("期间请不要有别的操作，防止数据导入失败！！！！");

        System.out.println("正在导入用户类别数据.....");
        ReadUserLabelToMysql.readUserLabelToMysql();

        System.out.println("正在导入图书数据.....");
        ReadDataFromCsvToMysql.readBookCsvToMysql();

        System.out.println("正在导入用户评分数据.....");
        ReadDataFromCsvToMysql.readRatingCsvToMysql();

        System.out.println("数据导入完成啦！");
    }
}
