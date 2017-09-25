package com.willpower.timechicken.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import com.willpower.timechicken.comm.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * SharedPreferences工具类
 */
public class SharedPreferences {

    public static SharedPreferences instance;
    private android.content.SharedPreferences sharedPreferences;

    private SharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Single instance.
     */
    public static SharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferences(context);
        }
        return instance;
    }

    public void putValue(String key, Object value) {
        Editor editor = sharedPreferences.edit();
        if (value == null || value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        editor.commit();
    }

    public <T extends Object> Object getValue(String key, Class<T> cls) {
        try {
            if (cls.equals(String.class)) {
                String returnValue = sharedPreferences.getString(key, "");
                return returnValue;
            } else if (cls.equals(Boolean.class)) {
                return sharedPreferences.getBoolean(key, false);
            } else if (cls.equals(Integer.class)) {
                return sharedPreferences.getInt(key, 0);
            } else if (cls.equals(Float.class)) {
                return sharedPreferences.getFloat(key, 0.0f);
            }
        }catch (Exception e){
            e.printStackTrace();
            if (cls.equals(String.class)) {
                return null;
            } else if (cls.equals(Boolean.class)) {
                return false;
            } else if (cls.equals(Integer.class)) {
                return 0;
            } else if (cls.equals(Float.class)) {
                return 0.0f;
            }
        }
        return null;
    }

    /**
     * 清除配置文件
     */
    public void clearPreferences() {
        if (sharedPreferences != null) {
            Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

    /**
     * 将Object信息Base64后存入Preferences
     * @param name
     * @param obj
     */

    public void setObjectPreferences(String name, Object obj) {
        Editor editor = sharedPreferences.edit();
        if (obj == null) {
            editor.putString(name, null);
            editor.commit();
            return;
        }
        try {
            ByteArrayOutputStream toByte = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(toByte);
            oos.writeObject(obj);
            // 对byte[]进行Base64编码
            String obj64 = new String(Base64.encode(toByte.toByteArray(),
                    Base64.DEFAULT));
            editor.putString(name, obj64);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SharePreference中值，Base64解码之后传出
     *
     * @param name
     */
    public Object getObjectPreferences(String name) {
        try {
            String obj64 = sharedPreferences.getString(name, "");
            if (TextUtils.isEmpty(obj64)) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(obj64, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
