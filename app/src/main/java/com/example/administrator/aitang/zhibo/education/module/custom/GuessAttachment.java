package com.example.administrator.aitang.zhibo.education.module.custom;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class GuessAttachment extends CustomAttachment {

    public enum Guess {
        Shitou(1, "石头"),
        Jiandao(2, "剪刀"),
        Bu(3, "布"),
        ;

        private int value;
        private String desc;

        Guess(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        static Guess enumOfValue(int value) {
            for (Guess direction : values()){
                if (direction.getValue() == value) {
                    return direction;
                }
            }
            return Shitou;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }

    private Guess value;

    public GuessAttachment() {
        super(CustomAttachmentType.Guess);
        random();
    }

    @Override
    protected void parseData(JSONObject data) {
        try {
            value = Guess.enumOfValue(data.getInt("value"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        try {
            data.put("value", value.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void random() {
        int value = new Random().nextInt(3) + 1;
        this.value = Guess.enumOfValue(value);
    }

    public Guess getValue() {
        return value;
    }
}
