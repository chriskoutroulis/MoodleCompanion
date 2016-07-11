package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 11/7/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("useridfrom")
    @Expose
    private Integer useridfrom;
    @SerializedName("useridto")
    @Expose
    private Integer useridto;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("fullmessage")
    @Expose
    private String fullmessage;
    @SerializedName("fullmessageformat")
    @Expose
    private Integer fullmessageformat;
    @SerializedName("fullmessagehtml")
    @Expose
    private String fullmessagehtml;
    @SerializedName("smallmessage")
    @Expose
    private String smallmessage;
    @SerializedName("notification")
    @Expose
    private Integer notification;
    @SerializedName("contexturl")
    @Expose
    private Object contexturl;
    @SerializedName("contexturlname")
    @Expose
    private Object contexturlname;
    @SerializedName("timecreated")
    @Expose
    private Integer timecreated;
    @SerializedName("timeread")
    @Expose
    private Integer timeread;
    @SerializedName("usertofullname")
    @Expose
    private String usertofullname;
    @SerializedName("userfromfullname")
    @Expose
    private String userfromfullname;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The useridfrom
     */
    public Integer getUseridfrom() {
        return useridfrom;
    }

    /**
     * @param useridfrom The useridfrom
     */
    public void setUseridfrom(Integer useridfrom) {
        this.useridfrom = useridfrom;
    }

    /**
     * @return The useridto
     */
    public Integer getUseridto() {
        return useridto;
    }

    /**
     * @param useridto The useridto
     */
    public void setUseridto(Integer useridto) {
        this.useridto = useridto;
    }

    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The fullmessage
     */
    public String getFullmessage() {
        return fullmessage;
    }

    /**
     * @param fullmessage The fullmessage
     */
    public void setFullmessage(String fullmessage) {
        this.fullmessage = fullmessage;
    }

    /**
     * @return The fullmessageformat
     */
    public Integer getFullmessageformat() {
        return fullmessageformat;
    }

    /**
     * @param fullmessageformat The fullmessageformat
     */
    public void setFullmessageformat(Integer fullmessageformat) {
        this.fullmessageformat = fullmessageformat;
    }

    /**
     * @return The fullmessagehtml
     */
    public String getFullmessagehtml() {
        return fullmessagehtml;
    }

    /**
     * @param fullmessagehtml The fullmessagehtml
     */
    public void setFullmessagehtml(String fullmessagehtml) {
        this.fullmessagehtml = fullmessagehtml;
    }

    /**
     * @return The smallmessage
     */
    public String getSmallmessage() {
        return smallmessage;
    }

    /**
     * @param smallmessage The smallmessage
     */
    public void setSmallmessage(String smallmessage) {
        this.smallmessage = smallmessage;
    }

    /**
     * @return The notification
     */
    public Integer getNotification() {
        return notification;
    }

    /**
     * @param notification The notification
     */
    public void setNotification(Integer notification) {
        this.notification = notification;
    }

    /**
     * @return The contexturl
     */
    public Object getContexturl() {
        return contexturl;
    }

    /**
     * @param contexturl The contexturl
     */
    public void setContexturl(Object contexturl) {
        this.contexturl = contexturl;
    }

    /**
     * @return The contexturlname
     */
    public Object getContexturlname() {
        return contexturlname;
    }

    /**
     * @param contexturlname The contexturlname
     */
    public void setContexturlname(Object contexturlname) {
        this.contexturlname = contexturlname;
    }

    /**
     * @return The timecreated
     */
    public Integer getTimecreated() {
        return timecreated;
    }

    /**
     * @param timecreated The timecreated
     */
    public void setTimecreated(Integer timecreated) {
        this.timecreated = timecreated;
    }

    /**
     * @return The timeread
     */
    public Integer getTimeread() {
        return timeread;
    }

    /**
     * @param timeread The timeread
     */
    public void setTimeread(Integer timeread) {
        this.timeread = timeread;
    }

    /**
     * @return The usertofullname
     */
    public String getUsertofullname() {
        return usertofullname;
    }

    /**
     * @param usertofullname The usertofullname
     */
    public void setUsertofullname(String usertofullname) {
        this.usertofullname = usertofullname;
    }

    /**
     * @return The userfromfullname
     */
    public String getUserfromfullname() {
        return userfromfullname;
    }

    /**
     * @param userfromfullname The userfromfullname
     */
    public void setUserfromfullname(String userfromfullname) {
        this.userfromfullname = userfromfullname;
    }

}


