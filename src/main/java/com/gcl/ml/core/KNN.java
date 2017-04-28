package com.gcl.ml.core;

import com.gcl.ml.bean.UserVector;
import com.gcl.ml.util.MyVector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * KNN算法
 * Created by gcl on 2017/3/10.
 */
public class KNN {

    /**
     * k-近邻算法，返回用户的所属标签
     */
    public static int knn(List<UserVector> userVectorList, UserVector user, int k) {

        float[] dists = new float[k];
        for (int i = 0; i < k; i++) {
            dists[i] = Float.MAX_VALUE;
        }

        int[] label = new int[k];

        for (UserVector u : userVectorList) {
            Arrays.sort(dists);
            float dist = MyVector.dist(u, user);
            for (int i = 0; i < k; i++) {
                if (dist < dists[i]) {
                    dists[i] = dist;
                    label[i] = u.getLabel();
                }
            }
        }

        int[] maxLabel = new int[20];
        for (int i : label) {
            maxLabel[i]++;
        }

        int lavel = 0;

        for (int i : maxLabel) {
            if (i > lavel) {
                lavel = i;
            }
        }
        return lavel;
    }

    public static List<UserVector> getAllUserVector() {
        // 读取文件
        String userVectorPath = KNN.class.getResource("/data/userLabel").getPath();

        BufferedReader reader = null;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeSpecialFloatingPointValues();
        Gson gson = gsonBuilder.create();

        List<UserVector> list = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(userVectorPath));

            String line;
            while ((line = reader.readLine()) != null) {
                list.add(gson.fromJson(line, UserVector.class));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
