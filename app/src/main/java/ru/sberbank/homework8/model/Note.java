package ru.sberbank.homework8.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.sberbank.homework8.util.ColorGenerator;
import ru.sberbank.homework8.util.UUIDGenerator;

public class Note {

    private String mTitle;
    private String mText;
    private String mCreationDate;
    private int mColor;
    private int mId;


    public Note() {
        mId = UUIDGenerator.generateUniqueId();
        mColor = ColorGenerator.generateRandomColor();
        mCreationDate = new SimpleDateFormat("d MMM 'at' HH:mm").format(new Date());
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(String mCreationDate) {
        this.mCreationDate = mCreationDate;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
