package com.zhy.http.okhttp.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * Created by JimGong on 2016/6/23.
 */

public abstract class FastCallback<T> extends Callback<T> implements IGenericsSerializator{
    @Override
    public <T> T transform(String response, Class<T> classOfT) throws Exception {
        Gson mGson = new Gson();
        String jsonString = response;
        return mGson.fromJson(jsonString, classOfT);
    }


    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        try {
            T bean = this.transform(string, entityClass);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
