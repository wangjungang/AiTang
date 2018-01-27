package com.example.administrator.aitang.zhibo.education.module.custom;


import com.example.administrator.aitang.zhibo.education.module.MeetingOptCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PermissionAttachment extends CustomAttachment {
    private final static String KEY_COMMAND = "command";
    private final static String KEY_ROOM_ID = "room_id";
    private final static String KEY_UIDS = "uids";

    private String roomId;
    private MeetingOptCommand meetingOptCommand;
    private List<String> accounts = new ArrayList<>();

    public PermissionAttachment() {
        super(CustomAttachmentType.Permission);
    }

    public PermissionAttachment(String roomId, MeetingOptCommand optCommand) {
        this();
        this.roomId = roomId;
        this.meetingOptCommand = optCommand;
    }

    public PermissionAttachment(String roomId, MeetingOptCommand optCommand, List<String> accounts) {
        this();
        this.roomId = roomId;
        this.meetingOptCommand = optCommand;
        this.accounts = accounts;
    }

    @Override
    protected void parseData(JSONObject data) {
        try {
            roomId = data.getString(KEY_ROOM_ID);
            meetingOptCommand = MeetingOptCommand.statusOfValue(data.getInt(KEY_COMMAND));
            JSONArray array = data.getJSONArray(KEY_UIDS);
            for (int i = 0; i < array.length(); i++) {
                accounts.add(array.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        try {
            data.put(KEY_COMMAND, meetingOptCommand.getValue());
            data.put(KEY_ROOM_ID, roomId);
            if (accounts != null && !accounts.isEmpty()) {
                data.put(KEY_UIDS, accounts);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    public String getRoomId() {
        return roomId;
    }

    public MeetingOptCommand getMeetingOptCommand() {
        return meetingOptCommand;
    }

    public List<String> getAccounts() {
        return accounts;
    }
}
