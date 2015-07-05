package com.urw.qr.qr;

/**
 * Created by Jonny on 05.07.2015.
 */
public class QRCode {

    private String content = "";
    private int id = -1;

    public QRCode (String content)
    {
        this.content = content;
    }

    public QRCode (String content, int id)
    {
        this.content = content;
        this.id = id;
    }

    public String getContent ()
    {
        return content;
    }

    public int getID ()
    {
        return id;
    }

    public void setID (int id)
    {
        this.id = id;
    }
}
