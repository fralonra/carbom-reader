package com.example.zoron.carbom.misc;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.example.zoron.carbom.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zoron on 17-4-11.
 */

public class Utils {
    public static SoundPool sp;
    public static Map<Integer, Integer> suondMap;
    public static Context context;

    //初始化声音池
    public static void initSoundPool(Context context) {
        Utils.context = context;
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        suondMap = new HashMap<>();
        suondMap.put(1, sp.load(context, R.raw.msg, 1));
    }

    //播放声音池声音
    public static void play(int sound, int number) {
        AudioManager am = (AudioManager) Utils.context.getSystemService(Utils.context.AUDIO_SERVICE);
        //返回当前AlarmManager最大音量
        float audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //返回当前AudioManager对象的音量值
        float audioCurrentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolume / audioMaxVolume;
        sp.play(
                suondMap.get(sound), //播放的音乐Id
                audioCurrentVolume, //左声道音量
                audioCurrentVolume, //右声道音量
                1, //优先级，0为最低
                number, //循环次数，0无不循环，-1无永远循环
                1);//回放速度，值在0.5-2.0之间，1为正常速度
    }

    public static int binarySearch(final List<String> list, final String epc) {
        int sn = epcToNumber(epc);
        int low = 0;
        int high = list.size() - 1;
        int middle = -1;
        while(low <= high) {
            middle = (low + high) / 2;
            if ((sn == epcToNumber(list.get(middle)))) {
                return -1;
            } else if (sn < epcToNumber(list.get(middle))){
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        if (low - high == 1) {
            middle = low;
        }
        return middle;
    }

    public static int epcToNumber(final String epc) {
        return Integer.parseInt(epc.split(".*-0*")[1]);
    }

    public static String hexToAscii(final String value) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < value.length(); i+=2) {
            String str = value.substring(i, i+2);
            output.append((char)Integer.parseInt(str, 16));
        }
        return output.toString();
    }


    public static String asciiToString(final String value) {
        StringBuilder sbu = new StringBuilder();
        String[] chars = splitStringEvery(value, 2);
        for (String aChar : chars) {
            sbu.append((char) Integer.parseInt(aChar));
        }
        return sbu.toString();
    }

    public static String[] splitStringEvery(final String s, final int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

    public static byte[] hexStringToBytes(final String src) {
        int len = src.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(src.charAt(i), 16) << 4)
                    + Character.digit(src.charAt(i + 1), 16));
        }
        return data;
    }

    public static String bytesToHexString(final byte[] src) {
        final StringBuilder builder = new StringBuilder();
        if (src == null) {
            //Log.d("hex", "null");
        }
        for (byte b : src != null ? src : new byte[0]) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String stringArrayToString(final String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String s : array) {
            builder.append(s);
        }
        return builder.toString();
    }

    public static Map<Object, Object> mergeMaps(final Map<Object, Object> old, final Map<Object, Object> update) {
        Map<Object, Object> map = new HashMap<>(old);
        old.putAll(update);
        return map;
    }

    public static boolean isTextViewEmpty(final TextView textView) {
        return textView.getText().toString().trim().length() <= 0;
    }

    public static void showFragment(FragmentManager fm, int old, Fragment fg) {
        if (fg == null) return;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(old, fg);
        transaction.show(fg);
        transaction.commit();
    }

    public static void toggleFragment(FragmentManager fm, Fragment old, Fragment fg) {
        if (old == null | fg == null) return;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(old);
        transaction.show(fg);
        transaction.commit();
    }

    public static void replaceFragment(FragmentManager fm, int old, Fragment fg) {
        if (fg == null) return;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(old, fg);
        transaction.commit();
    }

    public static void replaceFragment(FragmentManager fm, int old, Fragment fg, String tag) {
        if (fg == null) return;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(old, fg, tag);
        transaction.commit();
    }

    public static void hideFragment(FragmentManager fm, Fragment fg) {
        if (fg == null) return;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fg);
        transaction.commit();
    }

    public static void removeFragment(FragmentManager fm, Fragment fg) {
        if (fg == null) return;
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(fg);
        transaction.commit();
    }
}
